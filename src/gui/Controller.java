package gui;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;
import dnd.Chamber;
import dnd.Passage;
import dnd.PassageSection;
import dnd.Main;
import dnd.Door;
import dnd.models.*;
import java.util.Random;

public class Controller {

    private DndApplication myGui;
    private Main mainLevel;
    private ArrayList<Door> doorList;
    
    public Controller(DndApplication theGui) {
        myGui = theGui;
        mainLevel = new Main();
        doorList = new ArrayList<Door>();
        mainLevel.run();
    }

    public ArrayList<String> getNameList() {
        int i = 1;
        ArrayList<String> returnList = new ArrayList<String>();
        ArrayList<Chamber> chamberList = mainLevel.getChamberList();
        for(Chamber chamber: chamberList) {
            String newString = new String("Chamber"+i);
            returnList.add(newString);
            i++;
        }
        i = 1;
        ArrayList<Passage> passageList = mainLevel.getPassageList();
        for(Passage passage: passageList) {
            String newString = new String("Passage"+i);
            returnList.add(newString);
            i++;
        }
        return returnList;
    }

    public ArrayList<Door> getChamberDoors(int index) {
        Chamber chamber = new Chamber();
        ArrayList<Door> returnList = new ArrayList<Door>();
        ArrayList<Chamber> chamberList = new ArrayList<Chamber>();
        chamberList = mainLevel.getChamberList();
        chamber = chamberList.get(index - 1);
        returnList = chamber.getDoors();
        doorList = returnList;
        return returnList;
    }

    public String getDoorDescription(int index) {
        String returnString = new String();
        returnString = doorList.get(index - 1).getDescription();
        return returnString;
    }
    public ArrayList<Door> getPassageDoors(int index) {
        Passage passage = new Passage();
        ArrayList<Door> returnList = new ArrayList<Door>();
        ArrayList<Passage> passageList = new ArrayList<Passage>();
        passageList = mainLevel.getPassageList();
        passage = passageList.get(index - 1);
        returnList = passage.getDoors();
        doorList = returnList;
        return returnList;
    }

    public String getChamberDescription(int index) {
        Chamber newChamber = new Chamber();
        String descriptionString = new String("");
        ArrayList<Chamber> chamberList = new ArrayList<Chamber>();
        chamberList = mainLevel.getChamberList();
        newChamber = chamberList.get(index - 1);
        descriptionString = newChamber.getDescription();
        return descriptionString;
    }

    public boolean save(String fileName) {
        boolean returnBool = false;
        try {
            returnBool = true;
            ObjectOutputStream outputDes = new ObjectOutputStream(new FileOutputStream(fileName));
            outputDes.writeObject(mainLevel);
            outputDes.close();
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
        return returnBool;
    }
    public boolean load(String fileName) {
        boolean returnBool = false;
        try {
            returnBool = true;
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            mainLevel = (Main) in.readObject();
        } catch(IOException e) {
            System.out.println("IOException is caught " + e);
        }catch(ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught " + ex);
        }
        return returnBool;
    }
    
    public Chamber getChamber(int index) {
        Chamber returnChamber = new Chamber();
        ArrayList<Chamber> chamberList = new ArrayList<Chamber>();
        chamberList = mainLevel.getChamberList();
        returnChamber = chamberList.get(index - 1);
        return returnChamber;
    }

    public Passage getPassage(int index) {
        Passage returnPassage = new Passage();
        ArrayList<Passage> passageList = new ArrayList<Passage>();
        passageList = mainLevel.getPassageList();
        returnPassage = passageList.get(index - 1);
        return returnPassage;
    }
    public String getPassageDescription(int index) {
        Passage newPassage = new Passage();
        String descriptionString = new String("");
        ArrayList<Passage> passageList = new ArrayList<Passage>();
        passageList = mainLevel.getPassageList();
        newPassage = passageList.get(index - 1);
        descriptionString = newPassage.getDescription();
        return descriptionString;
    }
    public void addMonsterToChamber(int roll, int chamberIndex) {
        Chamber newChamber = new Chamber();
        newChamber = getChamber(chamberIndex);
        Monster newMonster = new Monster();
        newMonster.setType(roll);
        newChamber.addMonster(newMonster);
    }

    public void addMonsterToPassage(int roll, int passageIndex) {
        Passage newPassage = new Passage();
        newPassage = getPassage(passageIndex);
        Monster newMonster = new Monster();
        newMonster.setType(roll);
        newPassage.addMonster(newMonster, randomNumber(newPassage.getPassageSection().size(), 0));
    }
    public void removeMonsterFromChamber(int chamberIndex, int monsterIndex) {
        Chamber newChamber = new Chamber();
        newChamber = getChamber(chamberIndex);
        ArrayList<Monster> monsterList = newChamber.getMonsters();
        monsterList.remove(monsterIndex - 1);

    }
    public void removeTreasureFromChamber(int chamberIndex, int treasureIndex) {
        Chamber newChamber = new Chamber();
        newChamber = getChamber(chamberIndex);
        ArrayList<Treasure> treasureList = newChamber.getTreasureList();
        treasureList.remove(treasureIndex - 1);
    }

    public void removeMonsterFromPassage(int passageIndex, String str) {
        Passage newPassage = new Passage();
        newPassage = getPassage(passageIndex);
        Monster newMonster = new Monster();
        ArrayList<PassageSection> list = new ArrayList<PassageSection>();
        list = newPassage.getPassageSection();
        for(PassageSection ps: list) {
            newMonster = ps.getMonster();
            if(str.contains(newMonster.getDescription())) {
                ps.removeMonster();
            }
        }
    }

    private int randomNumber(int max, int min) {
		Random number = new Random();
		int result = number.nextInt(max - min + 1) + min;
        return result;
    }

    public void addTreasureToChamber(int treasureRoll, int containerRoll, int chamberIndex) {
        Chamber newChamber = new Chamber();
        newChamber = getChamber(chamberIndex);
        Treasure newTreasure = new Treasure();
        newTreasure.chooseTreasure(treasureRoll);
        newTreasure.setContainer(containerRoll);
        newChamber.addTreasure(newTreasure);
    }

    public ArrayList<String> chamberRemoveMonsterList(int chamberIndex) {
        ArrayList<String> returnList = new ArrayList<String>();
        int i = 1;
        int j = 0;
        Chamber newChamber = new Chamber();
        newChamber = getChamber(chamberIndex);
        ArrayList<Monster> monsterList = new ArrayList<Monster>();
        monsterList = newChamber.getMonsters();
        for(Monster m: monsterList) {
            String s = new String();
            s = "Monster"+ i+ " - " + m.getDescription();
            returnList.add(j, s);
            i++;
            j++;
        }
        return returnList;
    }
    public ArrayList<String> chamberRemoveTreasureList(int chamberIndex) {
        ArrayList<String> returnList = new ArrayList<String>();
        int i = 1;
        int j = 0;
        Chamber newChamber = new Chamber();
        newChamber = getChamber(chamberIndex);
        ArrayList<Treasure> treasureList = new ArrayList<Treasure>();
        treasureList = newChamber.getTreasureList();
        for(Treasure t: treasureList) {
            String s = new String();
            s = "Treasure"+ i+ " - " + t.getWholeDescription();
            returnList.add(j, s);
            i++;
            j++;
        }
        return returnList;
    }

    public ArrayList<String> passageRemoveMonsterList(int passageIndex) {
        int i = 1;
        int j = 0;
        ArrayList<String> returnList = new ArrayList<String>();
        Passage newPassage = new Passage();
        newPassage = getPassage(passageIndex);
        ArrayList<Monster> monsterList = new ArrayList<Monster>();
        monsterList = newPassage.getAllMonsters();
        for(Monster m: monsterList) {
            String s = new String();
            s = "Monster" + i + " - " + m.getDescription();
            returnList.add(j, s);
            i++;
            j++;
        }
        return returnList;
    }
    public ArrayList<String> getDoorString(ArrayList<Door> doorList) {
        int i = 1;
        int j = 0;
        ArrayList<String> returnList = new ArrayList<String>();
        for(Door d: doorList) {
            String newString = new String("Door" + i);
            returnList.add(j, newString);
            i++;
            j++;
        }
        return returnList;
    }

}
