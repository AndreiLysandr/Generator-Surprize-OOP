package ProiectDoi;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        ArrayList<ISurprise> surprises = new ArrayList<>();
        ArrayList<IBag> bags = new ArrayList<>();

        System.out.println("\nGood to see you!" + "\nThis application shares and generate surprises for you \u2661");
        ProiectDoiInfo.essentialInfo();

        System.out.println("The following set of commands is available for you to use them:");
        ProiectDoiInfo.commands();

        System.out.println("\nNow, let's proceed!");
        int command;

        GiveSurpriseAndApplause applause = null;
        GiveSurpriseAndSing sing = null;
        GiveSurpriseAndHug hug = null;
        boolean applauseCache = false;
        boolean singCache = false;
        boolean hugCache = false;
        boolean randomCache = false;
        boolean fifoCache = false;
        boolean lifoCache = false;
        int count = 0;

        boolean exit = true;

        while (exit) {
            System.out.println("\nPlease choose the next command:");
            command = sc.nextInt();

            System.out.println();

            switch (command) {
                case 1:
                    ISurprise newRandomSurprise = GatherSurprises.gather();

                    if(newRandomSurprise instanceof FortuneCookie) {
                        ProiectDoiInfo.newFortuneCookie();
                    }
                    else if (newRandomSurprise instanceof Candies) {
                        ProiectDoiInfo.newCandie();
                    }
                    else if (newRandomSurprise instanceof MinionToy) {
                        ProiectDoiInfo.newMinionToy();
                    }

                    if (surprises.size() < 5) {
                        count = 0;
                    } else if (surprises.size() < 10) {
                        count = 1;
                    } else {
                        count = 2;
                    }

                    surprises.add(newRandomSurprise);
                    break;

                case 2:
                    System.out.print("Choose how many surprises you want to generate -> ");

                    int noOfSurprises = sc.nextInt();
                    if (noOfSurprises > 15) {
                        count = 2;
                    } else {
                        count = 1;
                    }

                    ArrayList<ISurprise> desiredSurprises = new ArrayList<>(GatherSurprises.gather(noOfSurprises));

                    ProiectDoiInfo.randomlyGenerated(noOfSurprises);
                    printSurprises(desiredSurprises);

                    surprises.addAll(desiredSurprises);
                    break;

                case 3:
                    ArrayList<ISurprise> randomSurprises;
                    int rndNoOfSurprises = random.nextInt(10, 21);
                    randomSurprises = new ArrayList<>(GatherSurprises.gather(rndNoOfSurprises));

                    ProiectDoiInfo.randomlyGenerated(rndNoOfSurprises);
                    printSurprises(randomSurprises);

                    surprises.addAll(randomSurprises);
                    count = 2;
                    break;

                case 4:
                    if (surprises.isEmpty()) {
                        int autoSurprises = random.nextInt(10, 21);
                        ArrayList<ISurprise> autoGeneratedSurprises;
                        autoGeneratedSurprises = new ArrayList<>(GatherSurprises.gather(autoSurprises));

                        ProiectDoiInfo.errSurprises(autoSurprises);
                        surprises.addAll(autoGeneratedSurprises);
                        count = 2;
                    }
                    else {
                        System.out.println("\nCurrently you have " + surprises.size() + " surprises.");
                    }

                    printSurprises(surprises);
                    break;

                case 5:
                    if (randomCache && fifoCache && lifoCache) {
                        ProiectDoiInfo.alreadyInitBags();
                        break;
                    }

                    if (surprises.isEmpty()) {
                        int autoSurprises = random.nextInt(10, 21);
                        ArrayList<ISurprise> autoGeneratedSurprises;
                        autoGeneratedSurprises = new ArrayList<>(GatherSurprises.gather(autoSurprises));

                        ProiectDoiInfo.errSurprises(autoSurprises);
                        surprises.addAll(autoGeneratedSurprises);
                        count = 2;
                    }

                    ProiectDoiInfo.chooseBagType();
                    String str = sc.next();

                    initializeLoop:
                    while (true) {
                        str = checkTypeOfBag(sc, str);

                        IBag newBag;
                        switch (str) {
                            case "random":
                                if (!randomCache) {
                                    newBag = BagFactory.getInstance().makeBag(str);
                                    populateBags(newBag, surprises, count);
                                    bags.add(newBag);
                                    ProiectDoiInfo.newRandomOrderBag();

                                    randomCache = true;
                                    break initializeLoop;
                                }
                                else {
                                    ProiectDoiInfo.existingRandom();
                                    str = sc.next();

                                    break;
                                }

                            case "fifo":
                                if (!fifoCache) {
                                    newBag = BagFactory.getInstance().makeBag(str);
                                    populateBags(newBag, surprises, count);
                                    bags.add(newBag);
                                    ProiectDoiInfo.newFIFOBag();

                                    fifoCache = true;
                                    break initializeLoop;
                                }
                                else {
                                    ProiectDoiInfo.existingFIFO();
                                    str = sc.next();

                                    break;
                                }

                            case "lifo":
                                if (!lifoCache) {
                                    newBag = BagFactory.getInstance().makeBag(str);
                                    populateBags(newBag, surprises, count);
                                    bags.add(newBag);
                                    ProiectDoiInfo.newLIFOBag();

                                    lifoCache = true;
                                    break initializeLoop;
                                }
                                else {
                                    ProiectDoiInfo.existingLIFO();
                                    str = sc.next();

                                    break;
                                }
                            default:
                                System.out.println("Error: Unknown Bag");
                                break initializeLoop;
                        }
                    }
                    break;

                case 6:
                    if (randomCache && fifoCache && lifoCache) {
                        ProiectDoiInfo.alreadyInitBags();
                        break;
                    }

                    if (surprises.size() < 10) {
                        ProiectDoiInfo.moreSurprises();

                        int autoSurprises = random.nextInt(10, 21);
                        ArrayList<ISurprise> giftSurprises;
                        giftSurprises = new ArrayList<>(GatherSurprises.gather(autoSurprises));
                        surprises.addAll(giftSurprises);

                        count = 2;
                    }

                    if (!randomCache) {
                        IBag randomBag = BagFactory.getInstance().makeBag("random");
                        populateBags(randomBag, surprises, count);
                        bags.add(randomBag);

                        count = 1;
                        randomCache = true;
                    }

                    if (!fifoCache) {
                        IBag fifoBag = BagFactory.getInstance().makeBag("fifo");
                        populateBags(fifoBag, surprises, count);
                        bags.add(fifoBag);

                        count = 0;
                        fifoCache = true;
                    }

                    if (!lifoCache) {
                        IBag lifoBag = BagFactory.getInstance().makeBag("lifo");
                        populateBags(lifoBag, surprises, count);
                        bags.add(lifoBag);

                        lifoCache = true;
                    }
                    ProiectDoiInfo.initAll();
                    break;

                case 7:
                    if (!randomCache && !fifoCache && !lifoCache) {
                        ProiectDoiInfo.noBags();
                        break;
                    }

                    String all = "all";

                    ProiectDoiInfo.chooseTakeOut(all);
                    String takeOutAll = sc.next();
                    takeOutAll = checkTypeOfBag(sc, takeOutAll);

                    ProiectDoiInfo.choosePut(all);
                    String putAll = sc.next();
                    while (true) {
                        if (putAll.equalsIgnoreCase(takeOutAll)) {
                            ProiectDoiInfo.tryAnotherBag();
                            putAll = sc.next();
                        }
                        else {
                            break;
                        }
                    }
                    putAll = checkTypeOfBag(sc, putAll);

                    IBag putInBagAll = getNeededBag(bags, putAll);
                    IBag takeOutBagAll = getNeededBag(bags, takeOutAll);


                    if (takeOutBagAll != null && takeOutBagAll.size() != 0) {
                        if (putInBagAll != null) {
                            putInBagAll.put(takeOutBagAll);
                            ProiectDoiInfo.successPut();
                        }
                    }
                    else {
                        ProiectDoiInfo.unsuccessfulPut();
                        break;
                    }
                    break;

                case 8:
                    if (!randomCache && !fifoCache && !lifoCache) {
                        ProiectDoiInfo.noBags();
                        break;
                    }

                    String single = "single";

                    ProiectDoiInfo.chooseTakeOut(single);
                    String takeOutSingle = sc.next();
                    takeOutSingle = checkTypeOfBag(sc, takeOutSingle);

                    ProiectDoiInfo.choosePut(single);
                    String putSingle = sc.next();
                    while (true) {
                        if (putSingle.equalsIgnoreCase(takeOutSingle)) {
                            ProiectDoiInfo.tryAnotherBag();
                            putSingle = sc.next();
                        }
                        else {
                            break;
                        }
                    }
                    putSingle = checkTypeOfBag(sc, putSingle);

                    IBag putInBagSingle = getNeededBag(bags, putSingle);
                    IBag takeOutBagSingle = getNeededBag(bags, takeOutSingle);

                    if (takeOutBagSingle != null && takeOutBagSingle.size() != 0) {
                        if (putInBagSingle != null) {
                            putInBagSingle.put(takeOutBagSingle.takeOut());
                            ProiectDoiInfo.successPutSingle();
                        }
                    } else {
                        ProiectDoiInfo.unsuccessfulPutSingle();
                        break;
                    }
                    break;

                case 9:
                    if (!randomCache && !fifoCache && !lifoCache) {
                        ProiectDoiInfo.noBags();
                        break;
                    }

                    if (surprises.size() < 10) {
                        ProiectDoiInfo.moreSurprises();

                        int autoSurprises = random.nextInt(10, 21);
                        ArrayList<ISurprise> giftSurprises;
                        giftSurprises = new ArrayList<>(GatherSurprises.gather(autoSurprises));
                        surprises.addAll(giftSurprises);

                        count = 2;
                    }

                    for (IBag iBag : bags) {
                        populateBags(iBag, surprises, count);
                    }
                    ProiectDoiInfo.populatedBags();
                    break;

                case 10:
                    if (!randomCache && !fifoCache && !lifoCache) {
                        ProiectDoiInfo.noBags();
                        break;
                    }

                    ProiectDoiInfo.chooseShareMethod();
                    int n = sc.nextInt();

                    ProiectDoiInfo.howToShare();
                    int howShare = sc.nextInt();

                    int seconds = 0;
                    if (howShare == 2) {
                        ProiectDoiInfo.waitingTime();
                        seconds = sc.nextInt();
                    }

                    ProiectDoiInfo.bagToShare();
                    String bag = sc.next();
                    bag = checkTypeOfBag(sc, bag);

                    IBag sharedBag = getNeededBag(bags, bag);

                    shareWithEmotions:
                    switch (n) {
                        case 1:
                            if (!applauseCache) {
                                applause = new GiveSurpriseAndApplause(bag, seconds);
                                applause.put(sharedBag);
                                applauseCache = true;
                            }

                            switch (howShare) {
                                case 1:
                                    applause.give();
                                    break shareWithEmotions;
                                case 2:
                                    applause.giveAll();
                                    break shareWithEmotions;
                                default:
                                    break shareWithEmotions;
                            }
                        case 2:
                            if (!singCache) {
                                sing = new GiveSurpriseAndSing(bag, seconds);
                                sing.put(sharedBag);
                                singCache = true;
                            }

                            switch (howShare) {
                                case 1:
                                    sing.give();
                                    break shareWithEmotions;
                                case 2:
                                    sing.giveAll();
                                    break shareWithEmotions;
                                default:
                                    break shareWithEmotions;
                            }
                        case 3:
                            if (!hugCache) {
                                hug = new GiveSurpriseAndHug(bag, seconds);
                                hug.put(sharedBag);
                                hugCache = true;
                            }

                            switch (howShare) {
                                case 1:
                                    hug.give();
                                    break shareWithEmotions;
                                case 2:
                                    hug.giveAll();
                                    break shareWithEmotions;
                                default:
                                    break shareWithEmotions;
                            }
                        default:
                            break;
                    }
                    break;

                case 11:
                    System.out.println("The available set of commands is:");
                    ProiectDoiInfo.commands();
                    break;

                case 12:
                    ProiectDoiInfo.exit();
                    exit = false;
                    break;

                default:
                    System.out.println(command + " is not an available command. \nThe available set of commands is:");
                    ProiectDoiInfo.commands();
            }
        }
    }

    private static IBag getNeededBag(ArrayList<IBag> bags, String putOrTakeOut) {
        for (IBag bag : bags) {
            if (bag instanceof RandomOrderBag randomPut) {
                if (randomPut.bagType().equalsIgnoreCase(putOrTakeOut)) {
                    return randomPut;
                }
            }
            else if (bag instanceof FIFO_Bag fifoPut) {
                if (fifoPut.bagType().equalsIgnoreCase(putOrTakeOut)) {
                    return fifoPut;
                }
            }
            else if (bag instanceof LIFO_Bag lifoPut) {
                if (lifoPut.bagType().equalsIgnoreCase(putOrTakeOut)) {
                    return lifoPut;
                }
            }
        }
        return null;
    }

    private static String checkTypeOfBag(Scanner sc, String str) {
        while (true) {
            if (str.equalsIgnoreCase(BagFactory.getInstance().randomType()) ||
                    str.equalsIgnoreCase(BagFactory.getInstance().fifoType()) ||
                    str.equalsIgnoreCase(BagFactory.getInstance().lifoType())) {
                break;
            } else {
                ProiectDoiInfo.errBags(str);
                str = sc.next();
            }
        }
        return str;
    }

    public static void printSurprises(ArrayList<ISurprise> s) {
        System.out.println("The generated surprises are:");

        for (int i = 0; i < s.size(); i++) {
            System.out.println((i+1) + ". " + s.get(i));
        }
        System.out.println();
    }

    public static void populateBags(IBag bag, ArrayList<ISurprise> surprises, int count) {
        if (count == 2) {
            for (int i = 0; i < surprises.size() / 3; i++) {
                bag.put(surprises.get(i));
                surprises.remove(surprises.get(i));
            }
        }
        else if (count == 1) {
            for (int i = 0; i < surprises.size() / 2; i++) {
                bag.put(surprises.get(i));
                surprises.remove(surprises.get(i));
            }
        }
        else if (count == 0) {
            for (int i = 0; i < surprises.size(); i++) {
                bag.put(surprises.get(i));
                surprises.remove(surprises.get(i));
            }
        }
    }
}
