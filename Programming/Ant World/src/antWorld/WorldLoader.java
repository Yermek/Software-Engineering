package antWorld;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WorldLoader {
	private AntWorld antWorld;
	
	public WorldLoader(String filename) {
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			file = null;
		}
		if(file!=null) {
			int x = 0;
			int y = 0;
			try {
				x = new Integer(file.readLine());
				y = new Integer(file.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Expected number");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			antWorld = new AntWorld(x, y);
			for(int j=0; j<y; j++) {
				char[] line = {};
				try {
					line = file.readLine().toCharArray();
				} catch (IOException e) {
					e.printStackTrace();
				}
				for(int i=0; i<line.length; i++) {
					if(i%2==1-j%2) {
						if(!(line[i]==' ')) {
							System.out.println("' ' expected found '" + line[i] + "' at line " + j + " character number " + i);
						}
					} else {
						int type = -1;
						int food = 0;
						switch(line[i]) {
						case '#':
							type = Hexagon.ROCK;
							break;
						case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
							food = line[i]-48;
						case '.':
							type = Hexagon.DIRT;
							break;
						case '+':
							type = Hexagon.REDANTHILL;
							break;
						case '-':
							type = Hexagon.BLACKANTHILL;
							break;
						default: 
							System.out.println("Found illegal character: '" + line[i] + "' at line " + j + " character number " + i);
							break;	
						}
						Hexagon h = new Hexagon((i-j%2)/2, j, type);
						h.addFood(food);
						antWorld.setHexagon(h);
						
					}
				}
			}
		} else {
			System.out.println("Unable to read file: " + filename);
		}
	}
	
	public AntWorld getAntWorld() {
		return antWorld;
	}
}
