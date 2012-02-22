package antWorld;

import gui.GUI;

import java.util.Random;

public class AntWorld {
	private static final int tournamentSize = 150;
	
	private static Random random;
	
	private Hexagon[][] grid;
	private int[] gridSize;
	
	public AntWorld() {
		initialise(tournamentSize, tournamentSize);	
	}
	
	public AntWorld(int[] size) {
		initialise(size[0], size[1]);
	}
	
	public AntWorld(int x, int y) {
		initialise(x, y);
	}

	private void initialise(int x, int y) {
		grid = new Hexagon[x][y];
		gridSize = new int[2];
		gridSize[0] = x;
		gridSize[1] = y;
	}
	
	public void setHexagon(Hexagon hex) {
		grid[hex.getX()][hex.getY()] = hex;
	}
	
	public Hexagon getHexagon(int x, int y) {
		return grid[x][y];
	}
	
	public int[] getGridSize() {
		return gridSize;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int j=0; j<gridSize[1]; j++) {
			for(int i=0; i<gridSize[0]*2; i++) {
				if(i%2==1-j%2) {
					s+=" ";
				} else {
					Hexagon h = grid[(i-j%2)/2][j];
					int food = h.getFood();
					if(food>0) {
						if(food>9) {
							s+="f";
						} else {
							s+=food;
						}
					} else {
						int type = h.getType();
						switch(type) {
						case Hexagon.DIRT:
							s+=".";
							break;
						case Hexagon.ROCK:
							s+="#";
							break;
						case Hexagon.REDANTHILL:
							s+="+";
							break;
						case Hexagon.BLACKANTHILL:
							s+="-";
							break;
						default:
							s+="?";
							break;
						}
					}
				}
			}
			s+="\n";
		}
		return s;
	}
	
	public static void setRandomSeed() {
		random = new Random();//12345);
	}
	
	public static void setRandomSeed(int seed) {
		random = new Random(seed);
	}

	public static int getRandom(int n) {
		if(random==null) {
			setRandomSeed();
		}
		return random.nextInt(n);
	}
	
	public static void main(String[] args) {
		AntWorld a = new WorldGenerator(20,20,5,0,0,0,0).getAntWorld();
		System.out.println(a);
		new GUI(a);
	}
}
