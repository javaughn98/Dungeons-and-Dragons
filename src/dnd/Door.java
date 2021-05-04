package dnd;
import dnd.die.D6;
import dnd.die.D10;
import dnd.die.D20;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;

public class Door {
	/**
	 * door exit field.
	 */
	private Exit exit;
	/**
	 * boolean field that checks if the door is an archway.
	 */
	private boolean isArchway;
	/**
	 * boolean field that checks if the door is trapped.
	 */
	private boolean isTrapped;
	/**
	 * boolean field that checks if the door is locked.
	 */
	private boolean isLocked;
	/**
	 * boolean field that checks if the door is open.
	 */
	private boolean isOpen;
	/**
	 * Trap field for the case when the door is trapped.
	 */
	private Trap trap;
	/**
	 * arrayList of spaces that the door is connected to.
	 */
	private ArrayList<Space> spaceList;
	/**
	 * constructor for the door class that sets the door as being locked, trapped or archway.
	 */
	public Door() {
		//needs to set defaults.
		if (chanceTrapped()) {
			D20 die20 = new D20();
			int roll = 0;
			roll = die20.roll();
			setTrapped(true, roll);
		} else if (chanceLocked()) {
			setLocked(true);
		} else if (chanceArchway()) {
			setArchway(true);
		} else {
			trap = null;
			exit = null;
			setArchway(false);
			setTrapped(false);
			setLocked(false);
			setOpen(false);
		}
	}
	/**
	 * door constructor that sets the given exit to the exit field.
	 * @param theExit Exit that sould be attached to the exit field for the door class.
	 */
	public Door(Exit theExit) {
		//sets up the door based on the Exit from the tables.
		trap = null;
		setExit(theExit);
		setLocked(false);
		setArchway(false);
		setTrapped(false);
		setOpen(false);
	}
	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */
	/**
	 * Sets the a random trap for the door based on the roll given or does nothing if the door isnt trapped.
	 * @param flag true or false flag that determines if a trap should be set.
	 * @param roll give the dice roll from 1 -20 that determines the description of the trap.
	 */
	public void setTrapped(boolean flag, int...roll) {
		// true == trapped.  Trap must be rolled if no integer is given.
		if (!flag) {
			isTrapped = false;
			trap = null;
			return;
		}
		isTrapped = flag;
		setFalseFlags();
		if (roll.length == 0) {
			trap = new Trap();
			trap.chooseTrap(rollTwentSidedDie());
		} else {
			trap = new Trap();
			for (int i: roll) {
				trap.chooseTrap(i);
			}
		}
	}
	/**
	 * rolls a die with twenty side.
	 * @return random integer between 1 and 20.
	 */
	private int rollTwentSidedDie() {
		D20 newDie = new D20();
		int roll =  0;
		roll = newDie.roll();
		return roll;
	}
	/** 
	 * helper method that helps the setTrapped method.
	 * sets the other properties to false if the door trap is set.
	 * sets the archway flags to false.
	 * sets the locked flag to false
	 * sets the open flag to false.
	*/
	private void setFalseFlags() {
		isArchway = false;
		isLocked = false;
		isOpen = false;
	}
	/**
	 * methord that sets the state of the door to open or closed based on the boolean flag passed.
	 * @param flag contains true or false to tell if the door is open or closed.
	 */
	public void setOpen(boolean flag) {
		//true == open.
		if (isArchway()) {
			isOpen = true;
		} else {
			isOpen = flag;
		}
		if (flag) {
			setTrapped(false);
			setLocked(false);
		}
	}
	/**
	 * sets the door to an archaway based on the given flag.
	 * @param flag true or false value give to determine if the door is an archway or not.
	 */
	public void setArchway(boolean flag) {
		//true == is archway.
		isArchway = flag;
		if (flag) {
			setOpen(true);
			setLocked(false);
			setTrapped(false);
		}
	}
	/**
	 * sets the given exit to the exit field for the door.
	 * @param theExit the given exit that should be set to this field.
	 */
	public void setExit(Exit theExit) {
		if (theExit == null) {
			return;
		}
		exit = theExit;
	}
	/**
	 * sets the door to locked or unlocked based on the given flag.
	 * @param flag boolean flag that determines if the door is locked or unlocked.
	 */
	private void setLocked(boolean flag) {
		isLocked = flag;
		if (flag) {
			setOpen(false);
			setArchway(false);
		}
	}
	/**
	 * method that determines if the door is trapped or not.
	 * @return boolean value that states if the door is trapped or not.
	 */
	private boolean chanceTrapped() {
		boolean returnBool;
		D20 die20 = new D20();
		int roll = 0;
		roll = die20.roll();
		returnBool = setBool(roll);
		return returnBool;
	}
	/**
	 * determines if the door has a certain attribute based on the roll given.
	 * @param roll take in the die roll.
	 * @return true or false value determined by the roll given.
	 */
	private boolean setBool(int roll) {
		boolean returnBool;
		if (roll == 1) {
			returnBool = true;
		} else {
			returnBool = false;
		}
		return returnBool;
	}
	/**
	 * creates a six sided die to determing if the door is locked based on the odds given.
	 * @return true or false value to determine the state of the door.
	 */
	private boolean chanceLocked() {
		boolean returnBool;
		D6 die6 = new D6();
		int roll = 0;
		roll = die6.roll();
		returnBool = setBool(roll);
		return returnBool;
	}
	/**
	 * creates a ten sided die to determing if the door is an archway based on the odds given.
	 * @return true or false value to determine the state of the door.
	 */
	private boolean chanceArchway() {
		boolean returnBool;
		D10 die10 = new D10();
		int roll = 0;
		roll = die10.roll();
		returnBool = setBool(roll);
		return returnBool;
	}
	/**
	 * method that states if the door is trapped or not.
	 * @return true or false value.
	 */
	public boolean isTrapped() {
		return isTrapped;
	}
	/**
	 * method that sates if the door is open or closed.
	 * @return true or false boolean value.
	 */
	public boolean isOpen() {
		return isOpen;
	}
	/**
	 * method that states if the door is an archway or not.
	 * @return true or false boolean value.
	 */
	public boolean isArchway() {
		return isArchway;
	}
	/**
	 * method that states idf the door is locked or not.
	 * @return true or false boolean value.
	 */
	public boolean isLocked() {
		return isLocked;
	}
	/**
	 * gets the description of the door trap in string format.
	 * @return teh description  of the trap.
	 */
	public String getTrapDescription() {
		String returnString;
		if (trap == null || !isTrapped()) {
			returnString = "The Door is not trapped";
		} else {
			returnString  = trap.getDescription();
		}
		return returnString;
	}
	/**
	 * gets the spaces that the door connects.
	 * @return a space array list that contains that contains the space(s).
	 */
	public ArrayList<Space> getSpaces() {
		//returns the two spaces that are connected by the door.
 		return spaceList;
	}
	/**
	 * adds a given space to the array list of spaces.
	 * @param toAdd space that should be added to the list.
	 */
	public void setSpaces(Space toAdd) {
		//identifies the two spaces with the door.
		// this method should also call the setDoor method from Space.
		if (toAdd == null) {
			return;
		}
		if (spaceList == null) {
			spaceList = new ArrayList<Space>();
		}
		toAdd.setDoor(this);
		spaceList.add(toAdd);
	}
	/**
	 * method that creates the description string for the door.
	 * @return the string with the door description.s
	 */
	public String getDescription() {
		String returnString = new String();
		StringBuilder stringBuild = new StringBuilder();
		archwayDescription(stringBuild);
		trapDescription(stringBuild);
		spaceDescription(stringBuild);
		returnString = stringBuild.toString();
		return returnString;
	}

	private void archwayDescription(StringBuilder sb) {
		if(isArchway()) {
			sb.append("Archway, ");
		} else {
			sb.append("Door, ");
		}
		if(isOpen()) {
			sb.append("open, ");
		} else {
			sb.append("closed, ");
		}
		if(isLocked()) {
			sb.append("locked, ");
		} else {
			sb.append("unlocked, ");
		}
	}

	private void trapDescription(StringBuilder sb) {
		if(isTrapped()) {
			sb.append("traped - "+ trap.getDescription()+", ");
		} else {
			sb.append("not trapped, ");
		}
	}

	private void spaceDescription(StringBuilder sb) {
		if(spaceList == null) {
			sb.append("not connected to any spaces, ");
		} else {
			sb.append("connects " + spaceList.size() + " spaces, ");
		}
	}

/***********
You can write your own methods too, you aren't limited to the required ones
*************/
}
