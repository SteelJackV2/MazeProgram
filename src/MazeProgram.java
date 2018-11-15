import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import jdk.nashorn.internal.runtime.arrays.ContinuousArrayData;
import sun.audio.*;
import javax.sound.sampled.*;


public class MazeProgram extends JPanel implements KeyListener,MouseListener {
	JFrame flat, dimensional;

	ArrayList<ArrayList<String>> maze = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<Boolean>> appear = new ArrayList<ArrayList<Boolean>>();
	int sX,sY;
	boolean forward, left, right;
	Image[] pic =  {new ImageIcon(this.getClass().getResource("up.png")).getImage(),
					new ImageIcon(this.getClass().getResource("arrow.png")).getImage(),
					new ImageIcon(this.getClass().getResource("aDown.png")).getImage(),
					new ImageIcon(this.getClass().getResource("left.png")).getImage()};
	public int direction = 1;
	public int moves = 0;
	public boolean win = false;
	public boolean play = false;
	public boolean setappear = !play;
	Clip clip;
	InputStream is;
	AudioStream as;
	AudioData ad;
	ContinuousAudioDataStream cas;
	int locX = 0;
	int locY = 0;

	public MazeProgram() {
		setBoard();
		flat=new JFrame();
		flat.add(this);
		flat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("D:\\Akash's Stuff\\Computer Science\\Data Structures\\MazeProgram\\src\\pacman.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		}
		catch(LineUnavailableException lue){}
		catch(UnsupportedAudioFileException uafe){}
		catch(IOException ioe){System.out.println("Hello?");}

		flat.setSize(1980,1080);
		flat.addKeyListener(this);
		flat.setVisible(true);

	}

	public void paintComponent(Graphics game) {
		super.paintComponent(game);
		{
			game.setColor(Color.BLACK);

			game.fillRect(0, 0, 1980, 1080);
			game.setColor(Color.WHITE);
			int size = 27;
			int start = 50;

			game.setFont(new Font("Arial", Font.BOLD, 25));

			if (!win && !play) {
				game.drawString("Draw The Game: Press Enter when done", 10, start - 20);
			} else if (!win && play) {
				game.drawString("Play The Game", 10, start - 20);
			} else {
				game.drawString("You Won the Game. Press R to reset", 10, start - 20);
			}
			game.drawString("Score: " + moves, 700, start - 20);

			for (int x = 0; x < maze.size(); x++) {
				for (int y = 0; y < maze.get(x).size(); y++) {

					if (maze.get(x).get(y).equals("-")) {
						locX = x;
						locY = y;
						game.drawImage(pic[direction], y * size + 1, x * size + 1 + start, null);
						try {
							if (!setappear) {
								appear.get(x - 1).set(y - 1, true);
								appear.get(x - 1).set(y, true);
								appear.get(x - 1).set(y + 1, true);
								appear.get(x).set(y - 1, true);
								appear.get(x).set(y + 1, true);
								appear.get(x + 1).set(y - 1, true);
								appear.get(x + 1).set(y, true);
								appear.get(x + 1).set(y + 1, true);
							}
						} catch (Exception e) {
							System.out.println("error");
						}
					}
					if (!setappear) {
						if (maze.get(x).get(y).equals("*") && appear.get(x).get(y)) {
							game.setColor(Color.RED);
							game.fillRect(y * size, x * size + start, size, size);
							game.setColor(Color.BLACK);
							game.drawRect(y * size, x * size + start, size, size);
						}
					} else {
						if (maze.get(x).get(y).equals("*")) {
							game.setColor(Color.RED);
							game.fillRect(y * size, x * size + start, size, size);
							game.setColor(Color.BLACK);
							game.drawRect(y * size, x * size + start, size, size);
						}
					}

					if (maze.get(x).get(y).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(y * size + 3, x * size + 3 + start, size - 6, size - 6);
					}
				}
			}
			game.setColor(Color.WHITE);
			game.fillRect(1000, 100,225, 25);
			game.setColor(new Color(199, 199, 199));
			game.fillRect(1000, 125,225, 25);
			game.setColor(new Color(155, 155, 155));
			game.fillRect(1000, 150,225, 25);
			game.setColor(new Color(0, 0, 0));
			game.fillRect(1000, 175,225, 25);
			game.fillRect(1000, 200,225, 25);
			game.fillRect(1000, 225,225, 25);
			game.setColor(new Color(155, 155, 155));
			game.fillRect(1000, 250,225, 25);
			game.setColor(new Color(199, 199, 199));
			game.fillRect(1000, 275,225, 25);
			game.setColor(Color.WHITE);
			game.fillRect(1000, 300,225, 25);

				//000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
			if(direction ==0) {
				int l = locY - 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(locX + y -3).get(l).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1075 - w, 1075 - w - 25, 1075 - w - 25, 1075 - w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};

					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}

				l = locY + 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(locX + y - 3).get(l).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1150 + w, 1150 + w + 25, 1150 + w + 25, 1150 + w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};

					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}
				try{
				if(maze.get(locX-1).get(locY).equals("*")){
					game.setColor(Color.RED);
					game.fillRect(1000, 100,225, 225);
					game.setColor(Color.BLACK);
					game.drawRect(1000, 100,225, 225);
				}else if(maze.get(locX-2).get(locY).equals("*")){
					game.setColor(Color.RED);
					game.fillRect(1025, 125,175, 175);
					game.setColor(Color.BLACK);
					game.drawRect(1025, 125,175, 175);
				}else if(maze.get(locX-3).get(locY).equals("*")){
					game.setColor(Color.RED);
					game.fillRect(1050, 150,125, 125);
					game.setColor(Color.BLACK);
					game.drawRect(1050, 150,125, 125);
				}else if(maze.get(locX-4).get(locY).equals("*")){
					game.setColor(Color.RED);
					game.fillRect(1075, 175,75, 75);
					game.setColor(Color.BLACK);
					game.drawRect(1075, 175,75, 75);
				}

					if(maze.get(locX-1).get(locY).equals("o")){
						game.setColor(Color.BLUE);
						game.fillRect(1000, 100,225, 225);
						game.setColor(Color.BLACK);
						game.drawRect(1000, 100,225, 225);
					}else if(maze.get(locX-2).get(locY).equals("o")){
						game.setColor(Color.BLUE);
						game.fillRect(1025, 125,175, 175);
						game.setColor(Color.BLACK);
						game.drawRect(1025, 125,175, 175);
					}else if(maze.get(locX-3).get(locY).equals("o")){
						game.setColor(Color.BLUE);
						game.fillRect(1050, 150,125, 125);
						game.setColor(Color.BLACK);
						game.drawRect(1050, 150,125, 125);
					}else if(maze.get(locX-4).get(locY).equals("o")){
						game.setColor(Color.BLUE);
						game.fillRect(1075, 175,75, 75);
						game.setColor(Color.BLACK);
						game.drawRect(1075, 175,75, 75);
					}
				}catch (Exception e){

				}
				//11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			}if(direction ==1) {
				int l = locX - 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(l).get(locY - y + 3).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1075 - w, 1075 - w - 25, 1075 - w - 25, 1075 - w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};

					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}

				l = locX + 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(l).get(locY - y + 3).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1150 + w, 1150 + w + 25, 1150 + w + 25, 1150 + w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};

					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}
				try {
					if (maze.get(locX).get(locY + 1).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1000, 100, 225, 225);
						game.setColor(Color.BLACK);
						game.drawRect(1000, 100, 225, 225);
					} else if (maze.get(locX).get(locY + 2).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1025, 125, 175, 175);
						game.setColor(Color.BLACK);
						game.drawRect(1025, 125, 175, 175);
					} else if (maze.get(locX).get(locY + 3).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1050, 150, 125, 125);
						game.setColor(Color.BLACK);
						game.drawRect(1050, 150, 125, 125);
					} else if (maze.get(locX).get(locY + 4).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1075, 175, 75, 75);
						game.setColor(Color.BLACK);
						game.drawRect(1075, 175, 75, 75);
					}

					if (maze.get(locX).get(locY + 1).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1000, 100, 225, 225);
						game.setColor(Color.BLACK);
						game.drawRect(1000, 100, 225, 225);
					} else if (maze.get(locX).get(locY + 2).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1025, 125, 175, 175);
						game.setColor(Color.BLACK);
						game.drawRect(1025, 125, 175, 175);
					} else if (maze.get(locX).get(locY + 3).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1050, 150, 125, 125);
						game.setColor(Color.BLACK);
						game.drawRect(1050, 150, 125, 125);
					} else if (maze.get(locX).get(locY + 4).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1075, 175, 75, 75);
						game.setColor(Color.BLACK);
						game.drawRect(1075, 175, 75, 75);
					}
				}catch (Exception e){

				}



				//222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
			}else if (direction == 2){
				int l = locY + 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(locX - y + 3).get(l).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1075 - w, 1075 - w - 25, 1075 - w - 25, 1075 - w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};

					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}

				l = locY - 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(locX - y + 3).get(l).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1150 + w, 1150 + w + 25, 1150 + w + 25, 1150 + w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};

					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}
				try {
					if (maze.get(locX + 1).get(locY).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1000, 100, 225, 225);
						game.setColor(Color.BLACK);
						game.drawRect(1000, 100, 225, 225);
					} else if (maze.get(locX + 2).get(locY).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1025, 125, 175, 175);
						game.setColor(Color.BLACK);
						game.drawRect(1025, 125, 175, 175);
					} else if (maze.get(locX + 3).get(locY).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1050, 150, 125, 125);
						game.setColor(Color.BLACK);
						game.drawRect(1050, 150, 125, 125);
					} else if (maze.get(locX + 4).get(locY).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1075, 175, 75, 75);
						game.setColor(Color.BLACK);
						game.drawRect(1075, 175, 75, 75);
					}

					if (maze.get(locX + 1).get(locY).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1000, 100, 225, 225);
						game.setColor(Color.BLACK);
						game.drawRect(1000, 100, 225, 225);
					} else if (maze.get(locX + 2).get(locY).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1025, 125, 175, 175);
						game.setColor(Color.BLACK);
						game.drawRect(1025, 125, 175, 175);
					} else if (maze.get(locX + 3).get(locY).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1050, 150, 125, 125);
						game.setColor(Color.BLACK);
						game.drawRect(1050, 150, 125, 125);
					} else if (maze.get(locX + 4).get(locY).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1075, 175, 75, 75);
						game.setColor(Color.BLACK);
						game.drawRect(1075, 175, 75, 75);
					}
				}catch (Exception e){

				}

				//33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
			}else if(direction == 3) {
				int l = locX + 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(l).get(locY + y - 3).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1075 - w, 1075 - w - 25, 1075 - w - 25, 1075 - w};
					//int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};



					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}

				l = locX - 1;
				for (int y = 0; y < 3; y++) {
					int w = 25 * y;
					int i = 25;
					try {
						if (maze.get(l).get(locY + y - 3).equals(" ")) {
							i = 0;
						}
					} catch (Exception e) {
					}
					int[] s = {1150 + w, 1150 + w + 25, 1150 + w + 25, 1150 + w};
					int[] h = {250 + w, 250 + w + i, 175 - w - i, 175 - w};

					game.setColor(Color.RED);
					game.fillPolygon(s, h, 4);
					game.setColor(Color.BLACK);
					game.drawPolygon(s, h, 4);
				}
				try {
					if (maze.get(locX).get(locY - 1).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1000, 100, 225, 225);
						game.setColor(Color.BLACK);
						game.drawRect(1000, 100, 225, 225);
					} else if (maze.get(locX).get(locY - 2).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1025, 125, 175, 175);
						game.setColor(Color.BLACK);
						game.drawRect(1025, 125, 175, 175);
					} else if (maze.get(locX).get(locY - 3).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1050, 150, 125, 125);
						game.setColor(Color.BLACK);
						game.drawRect(1050, 150, 125, 125);
					} else if (maze.get(locX).get(locY - 4).equals("*")) {
						game.setColor(Color.RED);
						game.fillRect(1075, 175, 75, 75);
						game.setColor(Color.BLACK);
						game.drawRect(1075, 175, 75, 75);
					}

					if (maze.get(locX).get(locY - 1).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1000, 100, 225, 225);
						game.setColor(Color.BLACK);
						game.drawRect(1000, 100, 225, 225);
					} else if (maze.get(locX).get(locY - 2).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1025, 125, 175, 175);
						game.setColor(Color.BLACK);
						game.drawRect(1025, 125, 175, 175);
					} else if (maze.get(locX).get(locY - 3).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1050, 150, 125, 125);
						game.setColor(Color.BLACK);
						game.drawRect(1050, 150, 125, 125);
					} else if (maze.get(locX).get(locY - 4).equals("o")) {
						game.setColor(Color.BLUE);
						game.fillRect(1075, 175, 75, 75);
						game.setColor(Color.BLACK);
						game.drawRect(1075, 175, 75, 75);
					}
				}catch (Exception e){ }
			//44444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444
			}
		}
	}

	public void setBoard(){
		File name = new File("D:\\Akash's Stuff\\Computer Science\\Data Structures\\MazeProgram\\src\\drawmaze");

		int r=0;
		try {
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			while( (text= input.readLine())!= null) {
				String row[] = text.split("");
				maze.add(new ArrayList<String>());
				appear.add(new ArrayList<Boolean>());
				int y = 0;
				for(String x: row){
					if(x.equals("-")){
						sX = r;
						sY = y;
					}
					y++;
					maze.get(r).add(x);
					appear.get(r).add(false);
				}
				r++;
			}

		}
		catch (IOException io){
			System.err.println("File error");
		}

	}

	public void setWalls(){
	}

	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == 82)) {
			maze.get(sX).set(sY,"-");
			maze.get(locX).set(locY," ");
			win = false;
			moves = 0;
			direction = 1;
		}

		if ((e.getKeyCode() == 10)) {
			maze.get(sX).set(sY,"-");
			maze.get(locX).set(locY,"o");
			win = false;
			moves = 0;
			play = true;
			setappear = false;
			direction = 1;
		}

		if(!win && play) {
			if ((e.getKeyCode() == 39)) {

				direction++;
				if (direction == 4)
					direction = 0;
				if (!win)
					moves++;
			}
			if ((e.getKeyCode() == 38)) {
				try{
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("D:\\Akash's Stuff\\Computer Science\\Data Structures\\MazeProgram\\src\\pacman.wav"));
					clip = AudioSystem.getClip();
					clip.open(audioStream);
				}
				catch(LineUnavailableException lue){}
				catch(UnsupportedAudioFileException uafe){}
				catch(IOException ioe){System.out.println("Hello?");}
				clip.start();
				try {
					switch (direction) {
						case 0:
							if (maze.get(locX - 1).get(locY).equals(" ")) {
								maze.get(locX).set(locY, (" "));
								maze.get(locX - 1).set(locY, "-");

								if (!win)
									moves++;
							}
							if (maze.get(locX - 1).get(locY).equals("o")) {
								win = true;
								maze.get(locX).set(locY, (" "));
							}

							break;
						case 1:
							if (maze.get(locX).get(locY + 1).equals(" ")) {
								maze.get(locX).set(locY, (" "));
								maze.get(locX).set(locY + 1, "-");

								if (!win)
									moves++;
							}
							if (maze.get(locX).get(locY + 1).equals("o")) {
								win = true;
								maze.get(locX).set(locY, (" "));
							}
							break;
						case 2:
							if (maze.get(locX + 1).get(locY).equals(" ")) {
								maze.get(locX).set(locY, (" "));
								maze.get(locX + 1).set(locY, "-");
								if (!win)
									moves++;
							}
							if (maze.get(locX + 1).get(locY).equals("o")) {
								win = true;
								maze.get(locX).set(locY, (" "));
							}
							break;
						case 3:
							if (maze.get(locX).get(locY - 1).equals(" ")) {
								maze.get(locX).set(locY, (" "));
								maze.get(locX).set(locY - 1, "-");
								if (!win)
									moves++;
							}
							if (maze.get(locX).get(locY - 1).equals("o")) {
								win = true;
								maze.get(locX).set(locY, (" "));
							}
							break;
					}
				} catch (Exception ex) {
				}
			}

			if ((e.getKeyCode() == 37)) {
				direction--;
				if (!win)
					moves++;
				if (direction == -1)
					direction = 3;

			}
			repaint();
		}

		if(!win && !play) {
			if ((e.getKeyCode() == 39)) {
				direction++;
				if (direction == 4)
					direction = 0;
				if (!win)
					moves++;
			}
			if ((e.getKeyCode() == 38)) {
				try{
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("D:\\Akash's Stuff\\Computer Science\\Data Structures\\MazeProgram\\src\\pacman.wav"));
					clip = AudioSystem.getClip();
					clip.open(audioStream);
				}
				catch(LineUnavailableException lue){}
				catch(UnsupportedAudioFileException uafe){}
				catch(IOException ioe){System.out.println("Hello?");}
				clip.start();
				try {
					switch (direction) {
						case 0:
								maze.get(locX).set(locY, (" "));
								maze.get(locX - 1).set(locY, "-");
								if (!win)
									moves++;

							if (maze.get(locX - 1).get(locY).equals("o")) {
								win = true;
							}

							break;
						case 1:
								maze.get(locX).set(locY, (" "));
								maze.get(locX).set(locY + 1, "-");
								if (!win)
									moves++;

							if (maze.get(locX).get(locY + 1).equals("o")) {
								win = true;
							}
							break;
						case 2:
								maze.get(locX).set(locY, (" "));
								maze.get(locX + 1).set(locY, "-");
								if (!win)
									moves++;

							if (maze.get(locX + 1).get(locY).equals("o")) {
								win = true;
							}
							break;
						case 3:
								maze.get(locX).set(locY, (" "));
								maze.get(locX).set(locY - 1, "-");
								if (!win)
									moves++;

							if (maze.get(locX).get(locY - 1).equals("o")) {
								win = true;
							}
							break;
					}
				} catch (Exception ex) {}

			}

			if ((e.getKeyCode() == 37)) {
				direction--;
				if (!win)
					moves++;
				if (direction == -1)
					direction = 3;

			}
			repaint();
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public static void main(String args[])
	{
		MazeProgram app=new MazeProgram();
	}
}