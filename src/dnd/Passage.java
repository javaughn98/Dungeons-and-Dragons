package dnd;

import dnd.models.Monster;
import java.lang.IndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.HashMap;
/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way
You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/
public class Passage extends Space {
	//these instance variables are suggestions only
	//you can change them if you wish.
	/**
	 * array list of passage section field.
	 */
	private ArrayList<PassageSection> thePassage;
	/**
	 * hashmap that contains the doors for each passage section.
	 */
	private HashMap<Door, PassageSection> doorMap;
	/**
	 * passage section field.
	 */
	private PassageSection passageSec;

	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */
	/**
	 * gets the array list of doors for the passage.
	 * @return array list of doors
	 */
	public ArrayList<Door> getDoors() {
	//gets all of the doors in the entire passage.
		ArrayList<Door> returnArray = new ArrayList<Door>();
		Door door;
		if (thePassage.isEmpty()) {
			return null;
		}
		for (PassageSection p: thePassage) {
			door = p.getDoor();
			if (door != null) {
				returnArray.add(door);
			}
		}
	return returnArray;
	}
	public ArrayList<PassageSection> getPassageSection() {
		return thePassage;
	}
	/**
	 * gest that door from a specific passage section in the array list.
	 * @param i index variable that represents the section index in the array list.
	 * @return the door fromthat section.
	 */
	public Door getDoor(int i) {
		//returns the door in section 'i'. If there is no door, returns null
		PassageSection passage;
		Door passageDoor;
		if (thePassage.isEmpty()) {
			return null;
		}
		if (i < 0) {
			return null;
		}
		try {
			passage = thePassage.get(i);
			passageDoor = passage.getDoor();
			if (passageDoor == null) {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Index out of bounds");
			return null;
		}
		return passageDoor;
	}
	/**
	 * adds a monster to a specific passage section from the passage section array list.
	 * @param theMonster the conster that shouldbe added to the passage section.
	 * @param i the index of the passage section that the monster should be added.
	 */
	public void addMonster(Monster theMonster, int i) {
		// adds a monster to section 'i' of the passage.
		PassageSection passage = new PassageSection();
		if (thePassage.isEmpty()) {
			return;
		}
		if (theMonster == null) {
			return;
		}
		try {
			passage = thePassage.get(i);
			passage.setMonster(theMonster);
			thePassage.set(i, passage);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Index out of bounds");
			return;
		}
	}
	/**
	 * gets the monster that is in a specific passage section from the passage sectipn array list.
	 * @param i the index variable for the passage section in the array list.
	 * @return the monster from that specific passage section.
	 */
	public Monster getMonster(int i) {
		//returns Monster door in section 'i'. If there is no Monster, returns null
		PassageSection passage;
		Monster passageMonster;
		if (thePassage.isEmpty()) {
			return null;
		}
		try {
			passage = thePassage.get(i);
			passageMonster = passage.getMonster();
			if (passageMonster == null) {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Index out of bounds");
			return null;
		}
		return passageMonster;
	}
	public ArrayList<Monster> getAllMonsters() {
		int i = 0;
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		Monster newMonster = new Monster();
		for(PassageSection ps: thePassage) {
			newMonster = ps.getMonster();
			if(newMonster != null) {
				monsterList.add(i, newMonster);
			}
			i++;
		}
		return monsterList;
	}
	/**
	 * sets the given passage section to the passage section field from this class.
	 * @param passage given passage section that should be set.
	 */
	private void setPassageSec(PassageSection passage) {
		passageSec = new PassageSection();
		passageSec = passage;
	}
	/**
	 * adds a given passage section to the array list of passage sections.
	 * @param passage passage that should be added.
	 */
	private void addToList(PassageSection passage) {
		thePassage.add(passage);
	}
	/**
	 * adds a passage section to the passageway.
	 * @param toAdd passage section to add.
	 */
	public void addPassageSection(PassageSection toAdd) {
		//adds the passage section to the passageway.
		if (toAdd == null) {
			return;
		}
		if (thePassage == null) {
			thePassage = new ArrayList<PassageSection>();
		}
		addToList(toAdd);
	}
	/**
	 * sets a given door.
	 * @param newDoor door to be set.
	 */
	@Override
	public void setDoor(Door newDoor) {
		//should add a door connection to the current Passage Section.
		if (newDoor == null) {
			return;
		}
		if (doorMap == null) {
			doorMap = new HashMap<>();
		}
		int index = thePassage.size() - 1;
		PassageSection newSection = thePassage.get(index);
		newSection.addDoor(newDoor);
		doorMap.put(newDoor, newSection);
		thePassage.set(index, newSection);
	}
	@Override
	public String getDescription() {
		String returnString = new String("");
		StringBuilder stringBuild = new StringBuilder();
		doorDescription(stringBuild);
		sectionDescription(stringBuild);
		monsterDescription(stringBuild);
		returnString = stringBuild.toString();
		return returnString;
	}

	public void doorDescription(StringBuilder sb) {
		int i = 1;
		sb.append("This passage has: ");
		sb.append(System.getProperty("line.separator"));
		sb.append("Door(s): " + getDoors().size());
		sb.append(System.getProperty("line.separator"));
		ArrayList<Door> doorList = new ArrayList<Door>();
		doorList = getDoors();
		for(Door d: doorList) {
			sb.append("Door"+i + " - "+ d.getDescription());
			sb.append(System.getProperty("line.separator"));
			i++;
		}
	}
	public void sectionDescription(StringBuilder sb) {
		int i = 1;
		sb.append("Passage Section(s): " + thePassage.size());
		sb.append(System.getProperty("line.separator"));
		for(PassageSection ps: thePassage) {
			sb.append("PassageSection" + i + " - "+ ps.getDescription());
			sb.append(System.getProperty("line.separator"));
			i++;
		}
	}
	public void monsterDescription(StringBuilder sb) {
		int i = 1;
		sb.append("Monster(s): " + getAllMonsters().size());
		sb.append(System.getProperty("line.separator"));
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		monsterList = getAllMonsters();
		for(Monster m: monsterList) {
			sb.append("Monster" + i + " - "+ m.getDescription());
			sb.append(System.getProperty("line.separator"));
			i++;
		}
	}

	/***********
	You can write your own methods too, you aren't limited to the required ones
	*************/
}
