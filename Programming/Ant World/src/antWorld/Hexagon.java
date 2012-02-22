package antWorld;

import ants.Ant;

public class Hexagon {
	public final static int DIRT = 0;
	public final static int ROCK = 1;
	public final static int REDANTHILL = 2;
	public final static int BLACKANTHILL = 3;
	
	public final static int redAnt = 0;
	public final static int blackAnt = 1;
	
	private int x;
	private int y;
	private int type;
	private int food;
	
	private Ant ant;
	private boolean[][] markers;
	
	public Hexagon(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		food = 0;
		ant = null;
		markers = new boolean[2][6];
		for(int i=0; i<6; i++) {
			markers[0][i] = false;
			markers[1][i] = false;
		}
	}
	
	public void addFood(int numOfFood) {
		food+=numOfFood;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean hasFood() {
		return food>0;
	}
	
	public int getFood() {
		return food;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean hasAnt() {
		return ant!=null;
	}
	
	public Ant getAnt() {
		return ant;
	}
	
	public boolean isEnemyMarkerPresent(int antColour) {
		boolean present = markers[antColour][0];
		int i = 1;
		while(i<6 && !present) {
			present = markers[antColour][i];
			i++;
		}
		return present;
	}
	
	public boolean[] getMarkers(int antColour) {
		return markers[antColour];
	}
	
	public boolean getMarkers(int antColour, int markerNumber) {
		return markers[antColour][markerNumber];
	}
	
	public static int[] getLocationOfAdjacentCell(int x, int y, int direction) {
		int[] pos = {x, y};
		switch(direction) {
		case 0: 
			pos[0]++;
			break; //(x+1, y)
	    case 1: 
	    	pos[0]+=y%2;
	    	pos[1]++;
			break; //if even(y) then (x, y+1) else (x+1, y+1)
	    case 2: 
	    	pos[0]+=y%2-1;
	    	pos[1]++;
			break; //if even(y) then (x-1, y+1) else (x, y+1)
	    case 3: 
	    	pos[0]--;
			break; //(x-1, y)
	    case 4: 
	    	pos[0]+=y%2-1;
	    	pos[1]--;
			break; //if even(y) then (x-1, y-1) else (x, y-1)
	    case 5: 
	    	pos[0]+=y%2;
	    	pos[1]--;
			break; //if even(y) then (x, y-1) else (x+1, y-1)
		}
		return pos;
	}
	/*function adjacent_cell(p:pos, d:dir):pos =
		  let (x,y) = p in
		  switch d of
		    case 0: (x+1, y)
		    case 1: if even(y) then (x, y+1) else (x+1, y+1)
		    case 2: if even(y) then (x-1, y+1) else (x, y+1)
		    case 3: (x-1, y)
		    case 4: if even(y) then (x-1, y-1) else (x, y-1)
		    case 5: if even(y) then (x, y-1) else (x+1, y-1)*/
}
