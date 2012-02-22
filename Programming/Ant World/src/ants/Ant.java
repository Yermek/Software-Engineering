package ants;

public class Ant {
	private int x;
	private int y;
	private boolean red;
	private int direction;
	
	private int brainCounter;
	
	public Ant(int x, int y, boolean red) {
		this.x = x;
		this.y = y;
		this.red = red;
		direction = 0;
		brainCounter = 0;
	}
	
	public void act() {
		//look at brain counter
		//act on brain
		//goto the location in the brain
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isRed() {
		return red;
	}
	
	public boolean isBlack() {
		return !red;
	}
}
