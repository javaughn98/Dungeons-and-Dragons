package dnd;

import dnd.models.Monster;
import java.util.Random;

/* Represents a 10 ft section of passageway */
public class PassageSection {
	/******************************
	 Required Methods for that we will test during grading
	*******************************/
	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */
	/**
	 * monster field if the passage section contains a monster.
	 */
	private Monster theMonster;
	/**
	 * door field for the passage section if it contains a door.
	 */
	private Door theDoor;
	/**
	 * Field that contains the description string for the passage section.
	 */
	private String description;
	/**
	 * passage section constructor  that sets the default description.
	 */
	public PassageSection() {
		//sets up the 10 foot section with default settings.
		description = new String("passage goes straight for 10 ft");
	}
	/**
	 * constructor that sets the given tescription to the passage section.
	 * @param newDescription passage section description tjat should be set to this passage section.
	 */
	public PassageSection(String newDescription) {
		//sets up a specific passage based on the values sent in from.
		//modified table 1.
		this.description = new String("");
		this.description = newDescription;
		if (newDescription.contains("Monster")) {
			Monster newMonster = new Monster();
			newMonster.setType(randomNumber());
			setMonster(newMonster);
		} else if (newDescription.contains("Door")) {
			Door newDoor = new Door();
			addDoor(newDoor);
		} else if (newDescription.contains("archway")) {
			Door newDoor = new Door();
			newDoor.setArchway(true);
			addDoor(newDoor);
		}
	}
	/**
	 * method theat gives a random number between 1 and 100.
	 * @return integer number between 1 and 100;
	 */
	private int randomNumber() {
		Random number = new Random();
		int result = number.nextInt(100 - 1 + 1) + 1;
		return result;
	}
	/**
	 * gets the door from the passage section.
	 * @return the door field in the passage section.
	 */
	public Door getDoor() {
		//returns the door that is in the passage section, if there is one.
		return theDoor;
	}
	/**
	 * gets the monster that is in the passage section.
	 * @return the monster field in the passage section.
	 */
	public Monster getMonster() {
		//returns the monster that is in the passage section, if there is one.
		return this.theMonster;
	}
	/**
	 * gets the description string field.
	 * @return the description string.
	 */
	public String getDescription() {
		//returns the Description of the passage section.
		return description;
	}
	/**
	 * sets the given monster to the monster field in this class.
	 * @param newMonster monster field that should be set in the passage section.
	 */
	public void setMonster(Monster newMonster) {
		//sets the monster that was passed in the passage.
		theMonster = new Monster();
		theMonster = newMonster;
	}
	/**
	 * sets the given door to the door field in this class.
	 * @param newDoor door that should be set.
	 */
	public void addDoor(Door newDoor) {
		//adds a door to the passage section.
		theDoor = new Door();
		theDoor = newDoor;
	}
	public void removeMonster() {
		theMonster = null;
	}
}
