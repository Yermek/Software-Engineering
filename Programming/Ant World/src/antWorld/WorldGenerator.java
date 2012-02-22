package antWorld;

public class WorldGenerator {
	private AntWorld antWorld;

	public WorldGenerator() {
		Generate(150, 150, 7, 5, 11, 5, 14);
	}

	public WorldGenerator(int x, int y, int nestSize, int foodSize, int foodAmmount, int foodDensity, int rockAmmount) {
		x = Math.max(x, 10);
		y = Math.max(y, 10);
		Generate(x, y, nestSize, foodSize, foodAmmount, foodDensity, rockAmmount);
	}

	public void Generate(int x, int y, int nestSize, int foodSize, int foodAmmount, int foodDensity, int rockAmmount) {
		antWorld = new AntWorld(x, y);
		for(int i=0; i<x; i++) {
			antWorld.setHexagon(new Hexagon(i, 0, Hexagon.ROCK));
			antWorld.setHexagon(new Hexagon(i, y-1, Hexagon.ROCK));
		}
		for(int j=1; j<y-1; j++) {
			antWorld.setHexagon(new Hexagon(0, j, Hexagon.ROCK));
			antWorld.setHexagon(new Hexagon(x-1, j, Hexagon.ROCK));
		}
		for(int i=1; i<x-1; i++) {
			for(int j=1; j<y-1; j++) {
				antWorld.setHexagon(new Hexagon(i, j, Hexagon.DIRT));
			}
		}
		setNests(nestSize);
		if(foodDensity>0 && foodAmmount>0 && foodSize>0) {
			placeFood(foodSize, foodAmmount, foodDensity);
		}
		if(rockAmmount>0) {
			placeRocks(rockAmmount);
		}
	}

	private void setNests(int size) {
		size = Math.max(size, 1);
		int gridSize[] = antWorld.getGridSize();
		if(size*2+3>=Math.min(gridSize[0], gridSize[1])) {
			size = Math.min(gridSize[0]-3, gridSize[1]-3)/2;
		}
		int counter = 0;
		boolean done = false;

		int redXPos;
		int redYPos;
		int blackXPos;
		int blackYPos;
		do {
			redXPos = AntWorld.getRandom(gridSize[0]-(size+1)*2)+size+1;
			redYPos = AntWorld.getRandom(gridSize[1]-(size+1)*2)+size+1;
			blackXPos = AntWorld.getRandom(gridSize[0]-(size+1)*2)+size+1;
			blackYPos = AntWorld.getRandom(gridSize[1]-(size+1)*2)+size+1;
			if(checkNestPositions(size, new int[] {redXPos, redYPos},  new int[] {blackXPos, blackYPos})) {
				done = true;
			} else {
				counter++;
				if(counter>1000) {
					counter=0;
					size--;
				}
			}
		} while(!done);
		placeNests(size, new int[] {redXPos, redYPos},  new int[] {blackXPos, blackYPos});
	}
	
	private void placeNests(int size, int[] redPos, int[] blackPos) {
		antWorld.setHexagon(new Hexagon(blackPos[0], blackPos[1], Hexagon.BLACKANTHILL));
		antWorld.setHexagon(new Hexagon(redPos[0], redPos[1], Hexagon.REDANTHILL));
		int leftRed = redPos[0]-size+1;
		int leftBlack = blackPos[0]-size+1;
		for(int i=1; i<size; i++) {
			antWorld.setHexagon(new Hexagon(blackPos[0]+i, blackPos[1], Hexagon.BLACKANTHILL));
			antWorld.setHexagon(new Hexagon(redPos[0]+i, redPos[1], Hexagon.REDANTHILL));
			antWorld.setHexagon(new Hexagon(blackPos[0]-i, blackPos[1], Hexagon.BLACKANTHILL));
			antWorld.setHexagon(new Hexagon(redPos[0]-i, redPos[1], Hexagon.REDANTHILL));
			if((redPos[1]+i)%2==0) {
					leftRed++;
			}
			if((blackPos[1]+i)%2==0) {
					leftBlack++;
			}
			for(int j=0; j<size*2-1-i; j++) {
				antWorld.setHexagon(new Hexagon(leftRed+j, redPos[1]+i, Hexagon.REDANTHILL));
				antWorld.setHexagon(new Hexagon(leftRed+j, redPos[1]-i, Hexagon.REDANTHILL));
				antWorld.setHexagon(new Hexagon(leftBlack+j, blackPos[1]+i, Hexagon.BLACKANTHILL));
				antWorld.setHexagon(new Hexagon(leftBlack+j, blackPos[1]-i, Hexagon.BLACKANTHILL));
			}
		}
		
	}
	
	private boolean checkNestPositions(int size, int[] redPos, int[] blackPos) {
		if(!(redPos[1]+(size*2)>blackPos[1] && redPos[1]-(size*2)<blackPos[1])) {
			return true;
		} else {
			if(redPos[0]==blackPos[0]) {
				return false;
			} else {	
				int[] leftNest;
				int[] rightNest;
				if(redPos[0]>blackPos[0]) {
					leftNest = blackPos;
					rightNest = redPos;
				} else {
					leftNest = redPos;
					rightNest = blackPos;
				}
				if(leftNest[0]+(size*2)-1<rightNest[0]) {
					return true;
				} else {
					if(leftNest[0]+(size*2)-1-(Math.abs(leftNest[1]-rightNest[1])-leftNest[1]%2+1)/2<rightNest[0]) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}

	private void placeFood(int size, int ammount, int density) {
		int done = 0;
		int attempts = 0;
		int gridSize[] = antWorld.getGridSize();
		while(size+4>Math.min(gridSize[0], gridSize[1])) {
			size = Math.min(gridSize[0], gridSize[1])-4;
		}
		do {
			attempts++;
			int foodXPos = AntWorld.getRandom(gridSize[0]-size-4)+2;
			int foodYPos = AntWorld.getRandom(gridSize[1]-size-4)+2;
			boolean clear = true;
			int x = 0-(foodYPos-1)%2;
			while(clear && x<size+1-(foodYPos-1)%2) {
				Hexagon hex = antWorld.getHexagon(foodXPos+x, foodYPos-1);			
				if(hex.getType()!=Hexagon.DIRT || hex.hasFood()) {
					clear = false;
				}
				x++;
			}
			int y =  0;
			while(y<size && clear) {
				x = -1;
				while(x<size+1 && clear) {
					Hexagon hex = antWorld.getHexagon(foodXPos+x, foodYPos+y);
					if(hex.getType()!=Hexagon.DIRT || hex.hasFood()) {
						clear = false;
					}
					x++;
				}
				y++;
			}
			x = 0-(foodYPos+size)%2;
			while(clear && x<size+1-(foodYPos+size)%2) {
				Hexagon hex = antWorld.getHexagon(foodXPos+x, foodYPos+size);
				if(hex.getType()!=Hexagon.DIRT || hex.hasFood()) {
					clear = false;
				}
				x++;
			}
			if(clear) {
				for(int i=0; i<size; i++) {
					for(int j=0; j<size; j++) {
						antWorld.getHexagon(foodXPos+i, foodYPos+j).addFood(density);
					}
				}
				attempts = 0;
				done++;
			}
		} while(done<ammount-(attempts/10000));
	}

	private void placeRocks(int ammount) { // allow branching
		int done = 0;
		int attempts = 0;
		int size = 1;
		int gridSize[] = antWorld.getGridSize();
		do {
			attempts++;
			int rockXPos = AntWorld.getRandom(gridSize[0]-5)+2;
			int rockYPos = AntWorld.getRandom(gridSize[1]-5)+2;
			boolean clear = true;
			int x = 0-(rockYPos-1)%2;
			while(clear && x<size+1-(rockYPos-1)%2) {
				Hexagon hex = antWorld.getHexagon(rockXPos+x, rockYPos-1);			
				if(hex.getType()!=Hexagon.DIRT || hex.hasFood()) {
					clear = false;
				}
				x++;
			}
			int y =  0;
			while(y<size && clear) {
				x = -1;
				while(x<size+1 && clear) {
					Hexagon hex = antWorld.getHexagon(rockXPos+x, rockYPos+y);
					if(hex.getType()!=Hexagon.DIRT || hex.hasFood()) {
						clear = false;
					}
					x++;
				}
				y++;
			}
			x = 0-(rockYPos+size)%2;
			while(clear && x<size+1-(rockYPos+size)%2) {
				Hexagon hex = antWorld.getHexagon(rockXPos+x, rockYPos+size);
				if(hex.getType()!=Hexagon.DIRT || hex.hasFood()) {
					clear = false;
				}
				x++;
			}
			if(clear) {
				antWorld.setHexagon(new Hexagon(rockXPos, rockYPos, Hexagon.ROCK));
				attempts = 0;
				done++;
			}
		} while(done<ammount-(attempts/10000));
	}

	public AntWorld getAntWorld() {
		return antWorld;
	}
}
