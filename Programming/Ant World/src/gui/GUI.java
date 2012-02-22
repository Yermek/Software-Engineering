package gui;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder; 

import antWorld.AntWorld;

public class GUI extends JFrame {
	static final long serialVersionUID = 1L;
	private AntWorldPanel antWorldPane;
	private JPanel menuPanel;
	
	public GUI(AntWorld antWorld) {
		super("Ant World");
		
		this.setLayout(new BorderLayout());
		antWorldPane = new AntWorldPanel(antWorld);
		menuPanel = new JPanel();
		
		JButton button1 = new JButton("Button 1");
		menuPanel.add(button1);
		
		add(antWorldPane, BorderLayout.CENTER);
		add(menuPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(this.getX()+50, this.getY()+50);
		this.pack();
        this.setVisible(true);
	}

	public class AntWorldPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {
		private static final long serialVersionUID = 1L;
		private BufferedImage[] images;
		private AntWorld antWorld;
		private double scale;
		private int startX;
		private int startY;
		
		public AntWorldPanel() {
			initialise();	
		}
		
		public AntWorldPanel(AntWorld antWorld) {
			this.antWorld = antWorld;
			initialise();
		}

		public void initialise() {
			int[] size = antWorld.getGridSize();
			scale = 10.0/Math.max(size[0], size[1]);
			startX = 0;
			startY = 0;
			images = new BufferedImage[8];
			try {
				images[0] = ImageIO.read(new File("src/gui/images/dirt.png"));
				images[1] = ImageIO.read(new File("src/gui/images/rock.png"));
				images[2] = ImageIO.read(new File("src/gui/images/red-nest.png"));
				images[3] = ImageIO.read(new File("src/gui/images/black-nest.png"));
				images[4] = ImageIO.read(new File("src/gui/images/food0.png"));
				images[5] = ImageIO.read(new File("src/gui/images/food1.png"));
				images[6] = ImageIO.read(new File("src/gui/images/food2.png"));
				images[7] = ImageIO.read(new File("src/gui/images/food3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			this.setPreferredSize(new Dimension((int) ((size[0]*64+32)*scale+20), (int) ((size[1]*55+20)*scale+20)));
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.scale(scale, scale);
			int[] size = antWorld.getGridSize();
			for(int i=0; i<size[0]; i++) {
				for(int j=0; j<size[1]; j++) {
					int x = (int) (i*64+(10*(1/scale)));
					if(j%2!=0) { x += 32; }
					int y = (int) (j*55+(10*(1/scale)));
					g2d.drawImage(images[antWorld.getHexagon(i, j).getType()], startX+x, startY+y, null);
					if(antWorld.getHexagon(i, j).hasFood()) {
						int food = antWorld.getHexagon(i, j).getFood();
						if(food<5) {
							g2d.drawImage(images[4], startX+x+24, startY+y+30, null);
						} else if(food<10) {
							g2d.drawImage(images[5], startX+x+24, startY+y+30, null);
						} else if(food<20) {
							g2d.drawImage(images[6], startX+x+24, startY+y+30, null);
						} else {
							g2d.drawImage(images[7], startX+x+24, startY+y+30, null);
						}
					}
				}
			}
		}

		@Override
		public void componentHidden(ComponentEvent arg0) { }
		@Override
		public void componentMoved(ComponentEvent arg0) { }
		@Override
		public void componentResized(ComponentEvent arg0) { }
		@Override
		public void componentShown(ComponentEvent arg0) { }
		
		@Override
		public void mouseDragged(MouseEvent arg0) { }
		@Override
		public void mouseMoved(MouseEvent arg0) { }
		
		@Override
		public void mouseClicked(MouseEvent arg0) { }
		@Override
		public void mouseEntered(MouseEvent arg0) { }
		@Override
		public void mouseExited(MouseEvent arg0) { }
		@Override
		public void mousePressed(MouseEvent arg0) { }
		@Override
		public void mouseReleased(MouseEvent arg0) { }
	}

}
