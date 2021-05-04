package dnd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Level {

    private ArrayList<Chamber> chamberList;
    private ArrayList<Passage> passageList;
    private HashMap<Door, ArrayList<Chamber>> doorMap;
    private HashMap<Door, Boolean> connection;
    private int chamberCount;

    public static void main(String[] args) {
        Level newLevel = new Level();
        int index = 0;
        boolean connectionMade = false;
        ArrayList<Chamber> listOfChambers = new ArrayList<Chamber>();
        listOfChambers = newLevel.getChambers();
        int count = 0;
        
        for (int i = 0; i < 5; i++) {
            newLevel.createChamber();
        }
        for (Chamber newChamber: listOfChambers) {
            ArrayList<Door> doorList = newLevel.getDoorsInChamber(newChamber);
            for (Door newDoor: doorList) {
                do {
                    index = newLevel.randomNumber(5, 0);
                } while(index != count);
                newLevel.addToHash(newDoor, listOfChambers.get(index));
            }
            count++;
        }

        for (Chamber chamber: listOfChambers) {
            ArrayList<Door> doors = newLevel.getDoorsInChamber(chamber);
            for (Door door: doors) {
                if (newLevel.connection.get(door) != null) {

                } else {
                    ArrayList<Chamber> chambers = newLevel.doorMap.get(door);
                    Chamber cham = chambers.get(0);
                    ArrayList<Door> arrayDoors = cham.getDoors();
                    for (Door checkDoor: arrayDoors) {
                        ArrayList<Chamber> chamb = newLevel.doorMap.get(checkDoor);
                        if (chamb != null) {
                            Chamber newC = chamb.get(0);
                            if (newC.equals(chamber)) {
                                Passage newPassage = new Passage();
                                PassageSection passageSection1 = new PassageSection();
                                passageSection1.addDoor(door);
                                PassageSection passageSection2 = new PassageSection();
                                passageSection2.addDoor(checkDoor);
                                newPassage.addPassageSection(passageSection1);
                                newPassage.addPassageSection(passageSection2);
                                newLevel.addPassage(newPassage);
                                connectionMade = true;
                                newLevel.connection.put(door, true);
                                newLevel.connection.put(checkDoor, true);
                                break;
                            }
                        }
                    }
                    if (connectionMade) {
                        connectionMade = false;
                    }
                    else {
                        int j = arrayDoors.size();
                        Door d = arrayDoors.get(newLevel.randomNumber(j, 0));
                        Passage pass = new Passage();
                        PassageSection ps1 = new PassageSection();
                        ps1.addDoor(door);
                        PassageSection ps2 = new PassageSection();
                        ps2.addDoor(d);
                        pass.addPassageSection(ps1);
                        pass.addPassageSection(ps2);
                        newLevel.addPassage(pass);
                        newLevel.connection.put(door, true);

                    }
                }
            }
                
        }
        System.out.println("completed assignment");
        return;
    }

    public Level() {
        chamberList = new ArrayList<Chamber>();
        passageList = new ArrayList<Passage>();
        connection = new HashMap<Door, Boolean>();
        doorMap = new HashMap<Door, ArrayList<Chamber>>();
        chamberCount = 0;
    }

    public int randomNumber(int max, int min) {
        Random num = new Random();
        int result = num.nextInt(max - min + 1) + min;
        return result;
    }

    public ArrayList<Door> getDoorsInChamber(Chamber givenChamber) {
        ArrayList<Door> doorList= new ArrayList<Door>();
        doorList = givenChamber.getDoors();
        return doorList;
    }

    public ArrayList<Chamber> getChambers() {
        return chamberList;
    }
    public void addChamber(Chamber toAdd) {
        if (toAdd == null) {
            return;
        }
        if (chamberList == null) {
            chamberList = new ArrayList<Chamber>();
        }
        chamberList.add(toAdd);
        chamberCount++;
    }

    public void createChamber() {
        Chamber newChamber = new Chamber();
        addChamber(newChamber);
    }

    public void createPassage() {
        Passage newPassage = new Passage();
        addPassage(newPassage);
    }

    public void addPassage(Passage toAdd) {
        if (toAdd == null) {
            return;
        }
        if (passageList == null) {
            passageList = new ArrayList<Passage>();
        }
        passageList.add(toAdd);
    }
   

    public void addToHash(Door doorToAdd, Chamber toAdd) {
        if (doorMap == null) {
            doorMap = new HashMap<Door, ArrayList<Chamber>>();
        }
        ArrayList<Chamber> chamberList = new ArrayList<Chamber>();
        chamberList.add(toAdd);
        doorMap.put(doorToAdd, chamberList);
    }
}
