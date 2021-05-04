package dnd;
public abstract class Space {
	/**
	 * Gets the description string of the given object.
	 * @return the description string.
	 */
	public abstract String getDescription();
	/**
	 * sets the door based on the given object.
	 * @param theDoor door that should be set.
	 */
	public abstract void setDoor(Door theDoor);
}
