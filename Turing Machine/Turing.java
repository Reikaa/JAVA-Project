package homework6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 95-771 Fall 2014 Homework 6
 * Turing Machine Simulation
 * 
 * @author ardyadipta
 *
 */
public class Turing {
	private static char[] tape; // tape is a global variable of this machine. all of the operations of this Turing machine are done on this tape
	private int haltState; // halt state of the machine
	
	// the actions of each state and each transition is stored using array of HashMap,
	// where the index of the array defines the current state of the machine. 
	// this is done since we need a data structure that can be called based on the character read on the tape, which is '0', '1', and 'B'
	private  HashMap<Character, Transition>[] actions; 
	
	/**
	 * Constructor for Turing Machine
	 * @param numberOfState - number of state on the machine
	 */
	public Turing(int numberOfState)
	{
		actions =  new HashMap[numberOfState-1];
		haltState = numberOfState-1;
		for (int fromState = 0 ; fromState< actions.length ; fromState++) actions[fromState] = new HashMap<Character, Transition>();
	}
	
	/**
	 * <dt><b>Pre-Condition</b><dd>
	 * fromState should be less than halt state
	 * <dt><b>Post-Condition</b><dd>
	 * new data on hash map will be inserted with key based on the character read on the tape and the value is the transition
	 * <dt><b>Time Complexity</b><dd>
	 * &Theta;(1)
	 * @param fromState - the origin state of this transition
	 * @param charRead - character read from the tape
	 * @param T - the transition of that state based on the character on the tape
	 */
	public void addTransition(int fromState, char charRead, Transition T)
	{		
		actions[fromState].put(charRead, T);
	}
	
	/**
	 * <dt><b>Pre-Condition</b><dd>
	 * -
	 * <dt><b>Post-Condition</b><dd>
	 * the tape will be filled with character 'B's
	 * <dt><b>Time Complexity</b><dd>
	 * &Theta;(n)
	 */
	public static void initializeTapeWithBlanks()
	{
		for(int i = 0; i<tape.length ; i++) tape[i] = 'B';
	}
	
	/**
	 * <dt><b>Pre-Condition</b><dd>
	 * tape is initialized
	 * <dt><b>Post-Condition</b><dd>
	 * -
	 * <dt><b>Time Complexity</b><dd>
	 * &Theta;(n)
	 * @return true if there is still '0' or 'B' on the tape, false if all is '1'
	 */
	public static boolean stillRoomOnTape()
	{
		for(int i = 0; i<tape.length ; i++)
		{
			if (tape[i] != '1') return true;
		}
		return false;
	}
	
	/**
	 * Method to execute the turing machine from state 0 until it finds its halt state
	 * 
	 * <dt><b>Pre-Condition</b><dd>
	 * tape is initialized. Turing machine has states and transition actions with halt state in it.
	 * <dt><b>Post-Condition</b><dd>
	 * tape will be rewritten on based on the actions made
	 * <dt><b>Time Complexity</b><dd>
	 * &Theta;(n)
	 * 
	 */
	public void execute()
	{
		// begin with state 0
		int state = 0;
		int currentBit = 0;
		
		while(state != haltState)
		{
			// get the transition based on the origin state and the character read
			Transition currentT = actions[state].get(tape[currentBit]); 
			
			// tape on that bit position will be written a new char based on the action in the transition
			tape[currentBit] = currentT.write();
			
			// go to the next state
			state = currentT.getNextState();
			
			// move to the next bit if transition is LEFT, or prior bit if transition is RIGHT
			currentBit = currentBit + currentT.moveTo();
		}
		
		
	}
	
	/**
	 * 
	 * Method to display the chars on the tape, but only the bits are displayed
	 * Since the tape is written 'B' when it is blank, I have to check when the tape is still blank, and then break the loop
	 * 
	 * <dt><b>Pre-Condition</b><dd>
	 * tape is initialized
	 * <dt><b>Post-Condition</b><dd>
	 * -
	 * <dt><b>Time Complexity</b><dd>
	 * &Theta;(n)
	 * where n is the length of the tape. in the best case scenario, it only takes 1 operations to run (when all of the data on the tape is blank)
	 */
	public static void displayTape()
	{
		StringBuilder tapeData = new StringBuilder();
		for(int i = 0; i<tape.length; i++)
		{
			if(tape[i] == 'B') break; // if the tape is blank, break the loop
			tapeData.append(tape[i]);
		}
		System.out.println(tapeData.reverse()); // display the tape
	}
	
	
	/**
	 * Main function to run the simulation of the Turing Machine 
	 * 
	 * <dt><b>Pre-Condition</b><dd>
	 * enough memory is preserved
	 * <dt><b>Post-Condition</b><dd>
	 * tape will be filled with '1'
	 * <dt><b>Time Complexity</b><dd>
	 * &Theta;(2^n)<br>
	 * where n is the number of bits on the tape. This happens since we are about to make the machine to do the operations on the whole tape.<br>
	 * As seen on the table below, let's take a look at the time to complete with output.<br>
	 * when n = 15 to n = 20, it took around 40 times longer (2^5 = 32 times, became 40 due to multiplication of some constant factor)<br>
	 * and when from n = 20 to n= 22 and then n=24, it takes about 4 times longer each, exactly 2^2 times.<br>
	 * So for 65 bits, it will take nearly 300 million years. <br>
	 * <br>
	 * time to complete (in seconds)<br>
	 * <img src="table.png" style="width:800px;"><br>
	 * ==============================================================<br>
	 *   n	| with output				| without output			|<br>
	 * --------------------------------------------------------------<br>
	 * 10	| 0.027						| 0.002						|<br>
	 * 15	| 0.273						| 0.014						|<br>
	 * 20	| 11.46						| 0.044						|<br>
	 * 22	| 47.662					| 0.128						|<br>
	 * 24	| 242.878					| 0.47						|<br>
	 * 26	| 895.992					| 1.858						|<br>
	 * 28	| 4375.761					| 7.289						|<br>
	 * 30	| 17503.044 (estimated)		| 28.917					|<br>
	 * 35	| 560097.408 (estimated)	| 928.06					|<br>
	 * 40	| 17923117.06 (estimated)	| 29697.92 (estimated)		|<br>
	 * 45	| 573539745.8 (estimated)	| 950333.44 (estimated)		|<br>
	 * 50	| 18353271865 (estimated)	| 30410670.08 (estimated)	|<br>
	 * 55	| 5.87305E+11 (estimated)	| 973141442.6 (estimated)	|<br>
	 * 60	| 1.87938E+13 (estimated)	| 31140526162 (estimated)	|<br>
	 * 65	| 6.014E+14 (estimated)		| 9.96497E+11 (estimated)	|<br>
	 * ==============================================================<br>
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("How many bits position?");
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		String line = is.readLine();
		int n = Integer.parseInt(line);
		Stopwatch sw = new Stopwatch();
		
		tape = new char[n];
		
		// this is a three state machine . it has states 0, and 1, and a halt state of 2
		Turing machine = new Turing(3);
		
		System.out.println("Adding transition state to 0");
		
		machine.addTransition(0,'B', new Transition('0', Transition.LEFT,2));
		machine.addTransition(0,'0', new Transition('1', Transition.LEFT,2));
		machine.addTransition(0,'1', new Transition('0', Transition.LEFT,1));
		
		//if i see a blank, I would write zero to tape, and transition to the left, then to state 2
		
		//System.out.println("Adding these transitions to state 1");
		
		machine.addTransition(1,'1', new Transition('0', Transition.LEFT,1));
		machine.addTransition(1,'B', new Transition('1', Transition.LEFT,2));
		machine.addTransition(1,'0', new Transition('1', Transition.LEFT,2));
		
		
		//place n B's on tape 
		initializeTapeWithBlanks();
		
		System.out.println("Running machine on an initially empty tape");
		while(stillRoomOnTape())
		{
			// machine is in state 0
			// tape head points to least significant bit
			machine.execute(); // execute until halt state is entered
			displayTape(); // comment this to run the program without output
			
		}
		
		double time = sw.elapsedTime();
		System.out.println("for " + n + " input, Time: " + time);
		

	}

}
