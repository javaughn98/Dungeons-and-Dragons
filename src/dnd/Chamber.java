package dnd;
import dnd.models.Trap;
import dnd.models.Exit;
import dnd.models.Stairs;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import dnd.exceptions.UnusualShapeException;
import dnd.exceptions.NotProtectedException;
import dnd.die.D20;
import java.util.Random;

public class Chamber extends Space {
	/**
	 *  chamber content field.
	 */
	private ChamberContents myContents;
	/**
	 * Chamber shape field.
	 */
	private ChamberShape mySize;
	/**
	 * Array list of all the doors in the chamber.
	 */
	private ArrayList<Door> doorList;
	/**
	 * integer number to identify chamber.
	 */
	private int id1;
	/**
	 * integer value to identift chamber.
	 */
	private int id2;
	/**
	 * Array list of all the monsters in the chamber.
	 */
	private ArrayList<Monster> monsterList;
	/**
	 * Array list of all the treasures int the chamber.
	 */
	private ArrayList<Treasure> treasureList;
	/**
	 * If the chamber contents contain a trap memory is allocated.
	 */
	private Trap chamberTrap;
	/**
	 * If the chamber contents is a trap memory is allocated.
	 */
	private Stairs chamberStairs;
	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */
	/**
	 * constructor for the chamber that sets default fields.
	 */
	public Chamber() {
		ChamberShape newShape = ChamberShape.selectChamberShape(randomNumber(20, 1));
		ChamberContents newContents = new ChamberContents();
		newShape.setNumExits();
		newContents.chooseContents(randomNumber(20, 1));
		allocateArrayMemory();
		myContents = newContents;
		id1  = randomNumber(10000, 1);
		id2 = randomNumber(10000, 1);
		mySize = newShape;
		setupChamber();
	}
	/**
	 * creates a 20 sided dit class and retursn a random integer number.
	 * @return and integer value betweeen 1 and 20.
	 */
	private int rollTwentySidedDie() {
		D20 newDie = new D20();
		int roll =  0;
		roll = newDie.roll();
		return roll;
	}
	/**
	 * gets the first chamber id.
	 * @return first id.
	 */
	public int getFirstId() {
		return this.id1;
	}
	/**
	 * gets the second chamber id.
	 * @return second id.
	 */
	public int getSecondId() {
		return this.id2;
	}
	/**
	 * method that returns a random number between given max and min value.
	 * @param max maximum value.
	 * @param min minimum value.
	 * @return a number between max and min values.
	 */
	private int randomNumber(int max, int min) {
		Random number = new Random();
		int result = number.nextInt(max - min + 1) + min;
		return result;
	}
	/**
	 * sets the chamber shape and the chamber contents from the fields given.
	 * @param theShape contaons the specific shape that needs to be set for this chamber.
	 * @param theContents contains the specific contents that needs to be set for this chamber.
	 */
	public Chamber(ChamberShape theShape, ChamberContents theContents) {
		mySize = theShape;
		myContents = theContents;
		id1 = randomNumber(10000, 1);
		id2 = randomNumber(10000, 1);
		allocateArrayMemory();
		setupChamber();
	}
	/**
	 * Allocates memory for the chamber contents.
	 * sets the given chamber shape to this chamber.
	 * @param theShape the chamber field description that needs to be set.
	 */
	public void setShape(ChamberShape theShape) {
		allocateArrayMemory();
		chamberTrap = null;
		chamberStairs = null;
		mySize = theShape;
		setupChamber();
	}
	/**
	 * method that allocates memory for all the array list fields in the chamber class.
	 */
	private void allocateArrayMemory() {
		doorList = new ArrayList<Door>();
		treasureList = new ArrayList<Treasure>();
		monsterList = new ArrayList<Monster>();
	}
	/**
	 * returns a specific door for the door list based on the given index.
	 * @param index integer index of the door.
	 * @return a door from the array list of doors.
	 */
	private Door getSpecificDoor(int index) {
		if (doorList.size() == 0 || doorList == null) {
			return null;
		}
		 Door newDoor = new Door();
		 newDoor = doorList.get(index);
		 return newDoor;
	}
	/**
	 * allocates memory for treasure list.
	 * generates a random treasure.
	 * sets random treasure atributes based on the roll.
	 */
	private void randomTreasure() {
		if (treasureList == null) {
			treasureList = new ArrayList<Treasure>();
		}
        Treasure treasure = new Treasure();
        treasure.chooseTreasure(randomNumber(100, 1));
		treasure.setContainer(randomNumber(20, 1));
		addTreasure(treasure);
	}
	/**
	 * allocates memory for the monster list.
	 * generates a random monster for the chamber.
	 * sets random monster attributes based on the roll.
	 */
	private void randomMonster() {
		if (monsterList == null) {
			monsterList = new ArrayList<Monster>();
		}
        Monster monster = new Monster();
		monster.setType(randomNumber(100, 1));
		addMonster(monster);
    }
	/**
	 * allocates memory for a trap field.
	 * sets the given trap to the chamber.
	 * @param newTrap trap field that need to be set in the chamber.
	 */
	private void setTrap(Trap newTrap) {
		chamberTrap = new Trap();
		chamberTrap = newTrap;
	}
	/**
	 * allocates memory for the stair field.
	 * sets the given stairs to the stair field.
	 * @param newStairs stair field that needs to be set in the chamber.
	 */
	private void setStairs(Stairs newStairs) {
		chamberStairs = new Stairs();
		chamberStairs = newStairs;
	}
	/**
	 * allocates memory for the trap field.
	 * generates a random trap and set the trap field.
	 */
	private void randomTrap() {
		Trap trap = new Trap();
		trap.chooseTrap(randomNumber(20, 1));
		setTrap(trap);
	}
	/**
	 * allocate memory for the stair field.
	 * generates a random stair description based on roll.
	 */
	private void randomStairs() {
        Stairs stairs = new Stairs();
    	stairs.setType(randomNumber(20, 1));
		setStairs(stairs);
    }
	/**
	 * Sets up the chamber fields based on the content description.
	 * Sets up the chamber shape.
	 */
	private void setupChamber() {
		int numExits = 0;
		String description = new String("");
		numExits = mySize.getNumExits();
		description = myContents.getDescription();
		descriptionSetup(description);
		while (numExits != 0) {
			Door newDoor = new Door();
			Exit newExit = new Exit();
			newDoor.setExit(newExit);
			setDoor(newDoor);
			numExits = numExits - 1;
		}
	}
	/**
	 * method that creates the chamber contents based on the content description given.
	 * @param description chamber content description string.
	 */
	private void descriptionSetup(String description) {
		if (description.equals("monster only")) {
			randomMonster();
		} else if (description.equals("monster and treasure")) {
			randomMonster();
			randomTreasure();
		} else if (description.equals("trap")) {
			randomTrap();
		} else if (description.equals("treasure")) {
			randomTreasure();
		} else if (description.equals("stairs")) {
			randomStairs();
		}
	}
	/**
	 * set the content field to the given content description.
	 * @param theContents field with the content description that needs to be set.
	 */
	public void setContents(ChamberContents theContents) {
		allocateArrayMemory();
		chamberTrap = null;
		chamberStairs = null;
		myContents = theContents;
		setupChamber();
	}
	/**
	 * Returns the array list of doors.
	 * @return the array list of doors.
	 */
	public ArrayList<Door> getDoors() {
		ArrayList<Door> returnList = new ArrayList<Door>();
		returnList = doorList;
		return returnList;
	}
	/**
	 * Adds a given monster to the monster field.
	 * @param theMonster monster field that needs to be added to the monster list.
	 */
	public void addMonster(Monster theMonster) {
		if (monsterList == null) {
			monsterList = new ArrayList<Monster>();
		}
		monsterList.add(theMonster);
	}
	/**
	 * returns the list of monsters.
	 * @return the array list of monsters.
	 */
	public ArrayList<Monster> getMonsters() {
		return monsterList;
	}
	/**
	 * Adds a given treasure to the treasure list.
	 * @param theTreasure treasure field that needs to be added to the treasure list.
	 */
	public void addTreasure(Treasure theTreasure) {
		if (theTreasure == null) {
			return;
		}
		if (treasureList == null) {
			treasureList = new ArrayList<Treasure>();
		}
		treasureList.add(theTreasure);
	}
	/**
	 * Returns the treasure array list.
	 * @return the arraylist of treasures in the chamber.
	 */
	public ArrayList<Treasure> getTreasureList() {
	return treasureList;
	}
	/**
	 * Returns the description of the chamber class.
	 * @return the description of the chamber in a string format.
	 */
	@Override
	public String getDescription() {
		String returnString = new String("");
		StringBuilder stringBuild = new StringBuilder();
		shapeDescription(stringBuild);
		contentDescription(stringBuild);
		doorListDescription(stringBuild);
		monstrListDescription(stringBuild);
		treasureListDescription(stringBuild);
		returnString = stringBuild.toString();
		return returnString;
	}
	private void shapeDescription(StringBuilder sb) {
		sb.append("Shape - "+ mySize.getNumExits()+ " exits/"+ mySize.getShape()+"/Area: "+ mySize.getArea()+"/");
		try {
			sb.append("Length: " + mySize.getLength() +"/Width: " + mySize.getWidth()+ " ");
			sb.append(System.getProperty("line.separator"));
		} catch(UnusualShapeException e) {
			sb.append("Unusual Shape ");
			sb.append(System.getProperty("line.separator"));

		}
	}
	private void contentDescription(StringBuilder sb) {
		sb.append("Content  - " + myContents.getDescription());
		sb.append(System.getProperty("line.separator"));

	}
	private void doorListDescription(StringBuilder sb) {
		int i = 1;
		if(doorList == null) {
			sb.append("No Door(s) ");
			sb.append(System.getProperty("line.separator"));
		} else {
			sb.append("Door(s): " + doorList.size());
			sb.append(System.getProperty("line.separator"));
			for(Door d: doorList) {
				sb.append("Door"+ i+ " - "+ d.getDescription());
				sb.append(System.getProperty("line.separator"));
				i++;
			}
		}
	}
	private void monstrListDescription(StringBuilder sb) {
		int i = 1;
		if(monsterList == null) {
			sb.append("No Monster(s) ");
			sb.append(System.getProperty("line.separator"));
		} else {
			sb.append("Monster(s): " + monsterList.size());
			sb.append(System.getProperty("line.separator"));
			for(Monster m: monsterList) {
				sb.append("Monster"+ i+ " - "+ m.getDescription());
				sb.append(System.getProperty("line.separator"));
				i++;
			}
		}
	}
	private void treasureListDescription(StringBuilder sb) {
		int i = 1;
		if(treasureList == null) {
			sb.append("No Treasure(s) ");
			sb.append(System.getProperty("line.separator"));
		} else {
			sb.append("Treasure(s): " + treasureList.size());
			sb.append(System.getProperty("line.separator"));
			for(Treasure t: treasureList) {
				sb.append("Treasure"+ i+ " - "+ t.getWholeDescription());
				sb.append(System.getProperty("line.separator"));
				i++;
			}
		}
	}
	/**
	 * Adds a door to the list of doors.
	 * @param newDoor door field the needs to be added to the list.
	 */
	@Override
	public void setDoor(Door newDoor) {
		//should add a door connection to this room.
		if (doorList == null) {
			doorList = new ArrayList<Door>();
		}
		doorList.add(newDoor);
	}
	/**
	 * method that checks if two chamber objects are equal.
	 * @param obj object to be compared
	 * @return true or false value to state is the objects are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		Chamber newChamber = (Chamber) obj;
		if ((getFirstId() == newChamber.getFirstId()) && (getSecondId() == newChamber.getSecondId())) {
			return true;
		}
		return false;
	}
	/***********
	You can write your own methods too, you aren't limited to the required ones
	*************/

}
