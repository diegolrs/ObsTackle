package ObsTackle;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	private BufferStrategy bs;
	private BufferedImage image;
	private Graphics g;
	private SpriteSheet sprite;
	
	Thread thread;
	
	public static Player player;
	public static Obstacles obstacle;
	
	private BufferedImage[] sprites_obstacle;
	private int obstacleCurrentFrame = 0;
	private final int obstacleMaxFrames = 2;
	
	public static boolean isRunning = false;
	public static boolean gameOver = false;
	
	public static final int MAX_PLATAFORMS = 3;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = 120;
	public static final int SCALE = 5;
	
	private boolean alreadyShowedDeathFrame = false;
	private final int FPS = 60;
	private final long ns = 1000000000/FPS;
	private long lastTime = System.nanoTime();
	
	private int frames = 0; // Used on FPS status
	private long timer = System.currentTimeMillis(); // Used on FPS status
	
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		Thread thread = new Thread(game);		
		game.start();
		game.startFrame(frame);
		thread.start();
	}
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.addKeyListener(this);	
	}
	
	public void startFrame(JFrame frame) {
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void start() {
		player = new Player();
		obstacle = new Obstacles();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		sprite = new SpriteSheet("/spritesheet.png");
		
		sprites_obstacle = new BufferedImage[2];
		sprites_obstacle[0] = sprite.getSprite(22, 0, 10, 10);
		sprites_obstacle[1] = sprite.getSprite(32, 0, 10 ,10);
		
		isRunning = true;
		gameOver = false;
		alreadyShowedDeathFrame = false;
	}
	
	public void tick() {
		obstacle.tick();
		player.tick();	
					
	}
	
	public void render() {
		
		bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		g = image.getGraphics();
		
		// Background	
		g.setColor(new Color(147, 26, 98));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		obstacle.render(g);
		player.render(g);
		
		
		// Plataforms
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 30, WIDTH, 10);
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 70, WIDTH, 10);
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 110, WIDTH, 10);
		
		// Score
		if(!gameOver) {
			g.setFont(new Font("Arial", Font.PLAIN, 9));
			g.setColor(new Color(147, 26, 98));
			g.drawString("Score: " + Game.obstacle.score, 10, 118);
		}
		
		// GameOver
		if(gameOver) {
			g.setColor(new Color(0,0,0,0.8f));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Game Over", 45, 45);
			g.setFont(new Font("Arial", Font.PLAIN, 9));
			g.drawString("Type any key to continue", 27, 65);
			
			g.setFont(new Font("Arial", Font.PLAIN, 9));
			g.setColor(Color.yellow);
			g.drawString("Your score: " + Game.obstacle.score, WIDTH/2 - 30, 85);
			
			obstacle.highscore(obstacle.score);
			
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.setColor(Color.yellow);
			g.drawString("High score: " + Game.obstacle.highscore, WIDTH/2 - 33, 95);
		
			g.setFont(new Font("Arial", Font.PLAIN, 9));
			g.setColor(new Color(255, 255, 255));
			g.drawString("Diego Reis - 2020", WIDTH/2 - 35, 118);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image,0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);		
		
		bs.show();			
	}
	
	public void run() {	
		
		while(isRunning) {
			
			//System.out.println("Running"); 
			
			if(!gameOver) {
				alreadyShowedDeathFrame = false;
				
				this.requestFocus();
				
				long now = System.nanoTime();
				
				if(now - lastTime >= ns) {
					// Lógica do jogo
						frames++;
						tick();
						render();
						lastTime = now;
				}
			}
			else {
				if(!alreadyShowedDeathFrame)
				{
					player.render(g);
					render();
				}
				
				alreadyShowedDeathFrame = true;
			}
			
			/* // FPS control status  --------------------	
				if(System.currentTimeMillis() - timer >= 1000) {		
					System.out.println("Frames: " + frames);
					timer+=1000;
					frames = 0;
				}
				// ----------------------------------------
			 */	
		}
			
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
		if(gameOver) {
			player.plataform = 2;
			player.x = 20;
			player.dx = 1;
			player.speed = 0.6;
			player.WIDTH = 11;
			obstacle.destroy();
			obstacle.score = 0;
			gameOver = false;	
		}else {
		
			if(e.getKeyCode() == KeyEvent.VK_UP)
				player.goUp();
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
				player.goDown();	
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
	
	
}
