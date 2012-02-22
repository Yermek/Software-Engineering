package ants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AntBrain {
	private static final Set<Character> NUMBERS = new HashSet<Character>(Arrays.asList(
		     new Character[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
		));
	private static final Set<Character> NUMBERSB6 = new HashSet<Character>(Arrays.asList(
		     new Character[] {'0', '1', '2', '3', '4', '5'}
		));
	
	private ArrayList<BrainState> antBrain;
	private FileReader file;
	private BrainState currentState;
	private int readAhead;
	
	private int highestReferencedState;
	private int lineNumber;
	private int charNumber;
	
	public AntBrain(String filename) {
		try {
			file = new FileReader(filename);
		} catch (FileNotFoundException e) {
			file = null;
		}
		if(file!=null) {
			highestReferencedState = 0;
			lineNumber = 0;
			charNumber = -1;
			readNext();
			getBrain();
		} else {
			System.out.println("Unable to read file: " + filename);
		}
	}
	
	private void readNext() {
		try {
			readAhead = file.read();
			charNumber++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void getBrain() {
		while(antBrain!=null && readAhead!=-1) {
			char read = (char) readAhead;
			readNext();
			switch(read) {
			case'D': case'd':
				getDropInstruction();
				break;
			case'F': case'f':
				getFlipInstruction();
				break;
			case'M': case'm':
				read = (char) readAhead;
				if(read=='a' || read=='A') {
					readNext();
					getMarkInstruction();
				} else if(read=='o' || read=='O') {
					readNext();
					getMoveInstruction();
				} else {
					error(read, "a or o");
				}
				break;	
			case'P': case'p':
				getPickupInstruction();
				break;
			case'S': case's':
				getSenseInstruction();
				break;
			case'T': case't':
				getTurnInstruction();
				break;
			case'U': case'u':
				getUnmarkInstruction();
				break;
			default:
				error("did not identify an instruction, found " + read);
				break;
			}
		}
	}
	
	private void getDropInstruction() {
		currentState = new BrainState(BrainState.INSTRUCTION_DROP);
		char read = (char) readAhead;
		readNext();
		if(checkCharacter(read, 'r')) {
			read = (char) readAhead;
			readNext();
			if(checkCharacter(read, 'o')) {
				read = (char) readAhead;
				readNext();
				if(checkCharacter(read, 'p')) {
					if(checkWhitespace()) {
						int nextState = getStateNumber();
						highestReferencedState = Math.max(nextState, highestReferencedState);
						currentState.setNextState(nextState);
						finishInstruction();
					}
				}
			}
		}
	}
	
	private void getFlipInstruction() {
		currentState = new BrainState(BrainState.INSTRUCTION_FLIP);
		char read = (char) readAhead;
		readNext();
		if(checkCharacter(read, 'l')) {
			read = (char) readAhead;
			readNext();
			if(checkCharacter(read, 'i')) {
				read = (char) readAhead;
				readNext();
				if(checkCharacter(read, 'p')) {
					if(checkWhitespace()) {
						currentState.setFlipNumber(getFlipNumber());
						if(checkWhitespace()) {
							int nextState = getStateNumber();
							highestReferencedState = Math.max(nextState, highestReferencedState);
							currentState.setNextState(nextState);
							if(checkWhitespace()) {
								int nextStateOnFail = getStateNumber();
								highestReferencedState = Math.max(nextStateOnFail, highestReferencedState);
								currentState.setNextStateOnFail(nextStateOnFail);
								finishInstruction();
							}
						}
					}
				}
			}
		}
	}
	
	private void getMarkInstruction() {
		currentState = new BrainState(BrainState.INSTRUCTION_MARK);
		char read = (char) readAhead;
		readNext();
		if(checkCharacter(read, 'r')) {
			read = (char) readAhead;
			readNext();
			if(checkCharacter(read, 'k')) {
				if(checkWhitespace()) {
					currentState.setMarkerNumber(getMarkerNumber());
					if(checkWhitespace()) {
						int nextState = getStateNumber();
						highestReferencedState = Math.max(nextState, highestReferencedState);
						currentState.setNextState(nextState);
						finishInstruction();
					}
				}
			}
		}
	}
	
	private void getMoveInstruction() {
		currentState = new BrainState(BrainState.INSTRUCTION_MOVE);
		char read = (char) readAhead;
		readNext();
		if(checkCharacter(read, 'v')) {
			read = (char) readAhead;
			readNext();
			if(checkCharacter(read, 'e')) {
				if(checkWhitespace()) {
					int nextState = getStateNumber();
					highestReferencedState = Math.max(nextState, highestReferencedState);
					currentState.setNextState(nextState);
					if(checkWhitespace()) {
						int nextStateOnFail = getStateNumber();
						highestReferencedState = Math.max(nextStateOnFail, highestReferencedState);
						currentState.setNextStateOnFail(nextStateOnFail);
						finishInstruction();
					}
				}
			}
		}
	}

	private void getPickupInstruction() {
		currentState = new BrainState(BrainState.INSTRUCTION_PICKUP);
		char read = (char) readAhead;
		readNext();
		if(checkCharacter(read, 'i')) {
			read = (char) readAhead;
			readNext();
			if(checkCharacter(read, 'c')) {
				read = (char) readAhead;
				readNext();
				if(checkCharacter(read, 'k')) {
					read = (char) readAhead;
					readNext();
					if(checkCharacter(read, 'u')) {
						read = (char) readAhead;
						readNext();
						if(checkCharacter(read, 'p')) {
							if(checkWhitespace()) {
								int nextState = getStateNumber();
								highestReferencedState = Math.max(nextState, highestReferencedState);
								currentState.setNextState(nextState);
								if(checkWhitespace()) {
									int nextStateOnFail = getStateNumber();
									highestReferencedState = Math.max(nextStateOnFail, highestReferencedState);
									currentState.setNextStateOnFail(nextStateOnFail);
									finishInstruction();
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void getSenseInstruction() {
		currentState = new BrainState(BrainState.INSTRUCTION_PICKUP);
		char read = (char) readAhead;
		readNext();
		if(checkCharacter(read, 'e')) {
			read = (char) readAhead;
			readNext();
			if(checkCharacter(read, 'n')) {
				read = (char) readAhead;
				readNext();
				if(checkCharacter(read, 's')) {
					read = (char) readAhead;
					readNext();
					if(checkCharacter(read, 'e')) {
						if(checkWhitespace()) {
							currentState.setSenseDirection(getSenseDirection());
							if(checkWhitespace()) {
								int nextState = getStateNumber();
								highestReferencedState = Math.max(nextState, highestReferencedState);
								currentState.setNextState(nextState);
								if(checkWhitespace()) {
									int nextStateOnFail = getStateNumber();
									highestReferencedState = Math.max(nextStateOnFail, highestReferencedState);
									currentState.setNextStateOnFail(nextStateOnFail);
							}
							
							
								finishInstruction();
							}
						}
					}
				}
			}
		}
	}
	
	private void getTurnInstruction() {
		
	}
	
	private void getUnmarkInstruction() {
		
	}
	
	private boolean checkCharacter(char read, char needed) { //check for capitals
		if(read==needed) {
			return true;
		} else {
			error(read, ""+needed);
			return false;
		}
	}
	
	private boolean checkWhitespace() {
		char read = (char) readAhead;
		if(read==' ' || read=='\t') {
			do {
				readNext();
				read = (char) readAhead;
			} while(read!=' ' && read!='\t');
			return true;
		} else {
			error("expected whitespace found: '" + read + "'");
			return false;
		}
	}
	
	private void ignoreWhitespace() {
		char read = (char) readAhead;
		do {
			readNext();
			read = (char) readAhead;
		} while(read!=' ' && read!='\t');
	}
	
	private void finishInstruction() {
		ignoreWhitespace();
		if(readAhead!=-1) {
			char read = (char) readAhead;
			if(read==';') {
				do {
					readNext();
					read = (char) readAhead;
				} while(read!='\n');
			} else if(read=='\n') {
				readNext();
			} else {
				error("expected newline found '" + read + "'");
			}
		}
	}
	
	private int getFlipNumber() {
		char read = (char) readAhead;
		String n = "";
		if(NUMBERS.contains(read)) {
			do {
				n+=read;
				readNext();
				read = (char) readAhead;
			} while(NUMBERS.contains(read));
			return new Integer(n);
		} else {
			return -1;
		}
	}
	
	private int getMarkerNumber() {
		char read = (char) readAhead;
		readNext();
		if(NUMBERSB6.contains(read)) {
			return new Integer(read);
		} else {
			return -1;
		}
	}
	
	private int getStateNumber() {
		char read = (char) readAhead;
		String n = "";
		if(NUMBERS.contains(read)) {
			do {
				n+=read;
				readNext();
				read = (char) readAhead;
			} while(NUMBERS.contains(read) && n.length()<5);
			readNext();
			return new Integer(n);
		} else {
			return -1;
		}
	}
	
	//TODO finish
	private int getSenseDirection() {
		char read = (char) readAhead;
		int s = -1;
		switch(read) {
		case'A': case'a':
			
			break;
		case'H': case'h':
			break;
		case'L': case'l':
			break;
		case'R': case'r':
			break;
		default:
			break;
		}
		return s;
	}
	
	private int getSenseCondition() {
		char read = (char) readAhead;
		int s = -1;
		switch(read) {
		case'F': case'f':	
			break;
		case'H': case'h':
			break;
		case'M': case'm':
			break;
		case'R': case'r':
			break;
		default:
			break;
		}
		return s;
	}
	
	private void error(char found, String expected) {
		error("found " + found + " but was expecting " + expected);
	}
	
	private void error(String message) {
		System.out.println("Error occourred on line number " + lineNumber + ", character " 
				+ charNumber + ": " + message + ".");
		antBrain = null;
	}
	
	public BrainState getBrainInstruction(int state) {
		return antBrain.get(state);
	}
}
