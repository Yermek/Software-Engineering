package ants;

public class BrainState {
	public static final int INSTRUCTION_SENSE = 0;
	public static final int INSTRUCTION_MARK = 1;
	public static final int INSTRUCTION_UNMARK = 2;
	public static final int INSTRUCTION_PICKUP = 3;
	public static final int INSTRUCTION_DROP = 4;
	public static final int INSTRUCTION_TURN = 5;
	public static final int INSTRUCTION_MOVE = 6;
	public static final int INSTRUCTION_FLIP = 7;
	
	public static final int SENSE_HERE = 0;
	public static final int SENSE_AHEAD = 1;
	public static final int SENSE_LEFT = 2;
	public static final int SENSE_RIGHT = 3;
	
	public static final int COND_FRIEND = 0;
	public static final int COND_FOE = 1;
	public static final int COND_FRIENDWITHFOOD = 2;
	public static final int COND_FOEWITHFOOD = 3;
	public static final int COND_FOOD = 4;
	public static final int COND_ROCK = 5;
	public static final int COND_MARKER = 6;
	public static final int COND_FOEMARKER = 7;
	public static final int COND_HOME = 8;
	public static final int COND_FOEHOME = 9;

	public static final int TURN_LEFT = -1;
	public static final int TURN_RIGHT = 1;

	private int instruction;
	private int senseDirection;
	private int senseCondition;
	private int turnDirection;
	private int nextState;
	private int nextStateOnFail;
	private int markerNumber;
	private int flipNumber;
	
	public BrainState(int instruction) {
		this.instruction = instruction;
	}

	public void setSenseDirection(int senseDirection) {
		this.senseDirection = senseDirection;
	}

	public void setSenseCondition(int senseCondition) {
		this.senseCondition = senseCondition;
	}

	public void setTurnDirection(int turnDirection) {
		this.turnDirection = turnDirection;
	}

	public void setNextState(int nextState) {
		this.nextState = nextState;
	}

	public void setNextStateOnFail(int nextStateOnFail) {
		this.nextStateOnFail = nextStateOnFail;
	}

	public void setMarkerNumber(int markerNumber) {
		this.markerNumber = markerNumber;
	}

	public void setFlipNumber(int flipNumber) {
		this.flipNumber = flipNumber;
	}
	
	public int getInstruction() {
		return instruction;
	}

	public int getSenseDirection() {
		return senseDirection;
	}

	public int getSenseCondition() {
		return senseCondition;
	}

	public int getTurnDirection() {
		return turnDirection;
	}

	public int getNextState() {
		return nextState;
	}

	public int getNextStateOnFail() {
		return nextStateOnFail;
	}

	public int getMarkerNumber() {
		return markerNumber;
	}

	public int getFlipNumber() {
		return flipNumber;
	}
}
