package ObsTackle;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sun.tools.javac.Main;

public class Obstacles {
	
	private BufferedImage[] sprites;
	private int currentFrame = 0;
	private final int MAX_FRAMES = 2;
	private int contToChangeFrame = 0;
	private  final int framesForChangeAnimation = 20;
	
	private int WIDTH = 15;
	private final int HEIGHT = 15;
	private int plataform;
	public static int score = -1;
	public static int highscore = 0;
	
	private int cont = 0;
	
	private int x;
	private int y;
	
	private SpriteSheet sprite;
	
	Obstacles(){
		sprite = new SpriteSheet("/spritesheet.png");
		sprites = new BufferedImage[MAX_FRAMES];
		sprites[0] = sprite.getSprite(22, 0, 10, 10);
		sprites[1] = sprite.getSprite(32, 0, 10 ,10);
		destroy();
	}
	
	public void setLocation() {
		
		int number = new Random().nextInt(Game.MAX_PLATAFORMS);
		
		switch(number) {
		case 0: 
			y = 15;
			plataform = 1;
			break;
		case 1:
			y = 55;
			plataform = 2;
			break;
		case 2:
			y = 95;
			plataform = 3;
			break;
		}
		
		number = new Random().nextInt(70) + 50;
		x = number;
	}
	
	public void highscore(int currentScore) {
		if(currentScore > highscore)
			highscore = currentScore;	
	}
	
	public void tick() {	
		
		if(Game.gameOver)
			score = -1;
		
		if(Game.player.dx > 0) {
			if(x >= Game.player.x - WIDTH && x <= Game.player.x + Game.player.WIDTH  && plataform == Game.player.plataform) 
				gameOver();
		}else if (Game.player.dx < 0) {
			if(x >= Game.player.x - WIDTH - (-1*Game.player.WIDTH)  && x <= Game.player.x && plataform == Game.player.plataform)
				gameOver();
		}
	}
	
	
	public void gameOver() {
		Game.gameOver = true;	
	}
	
	public void destroy() {
		score++;
		Game.player.speed += 0.05;
		currentFrame = new Random().nextInt(MAX_FRAMES);
		setLocation();
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[currentFrame], x, y, WIDTH, HEIGHT, null);
	}
	
	
}
