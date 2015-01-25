/**
 * New class transition for the Turing Machine
 * @author ardyadipta
 *
 */
public class Transition
{
	// define the global variables of this class
	// this LEFT and RIGHT are defined as constants which will set the location of the bit of the tape data. 
	// LEFT means go to the next bit, hence it increases the index of the array of character, which the tape is represented.
	// RIGHT means go to the prior bit, hence it decreases the index of the array of character, which the tape is represented.
	static final int LEFT = 1;
	static final int RIGHT = -1;
	
	private char toWrite; // the character that will be written on the tape
	private int move;	// the movement of the transition, whether it is to the left or to the right
	private int nextState; //  next state of this operation
	
	public Transition(char toWrite, int move, int nextState)
	{
		this.toWrite = toWrite;
		this.move = move;
		this.nextState = nextState;
	}
	
	/**
	 * method to write the tape
	 * @return char to be written to the tape
	 */
	public char write()
	{
		return toWrite;
	}
	
	/**
	 * method to move to other location on the tape
	 * @return integer +1 or -1 to increase or decrease the index of the array of the tape
	 */
	public int moveTo()
	{
		return move;
	}
	
	/**
	 * 
	 * @return next state of the tape
	 */
	public int getNextState()
	{
		return nextState;
	}
	
}
