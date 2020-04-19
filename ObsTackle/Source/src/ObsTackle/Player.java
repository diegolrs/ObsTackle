package ObsTackle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
	private BufferedImage[] sprites;	
	public static int currentFrame = 0;
	private final int MAX_FRAMES = 2;
	private int contToChangeFrame = 0;
	private  final int framesForChangeAnimation = 20;
	
	public static double x;
	public static double y;
	public static int dx;
	public static int plataform = 2;
	
	public static double speed = 0.6;
	
	public static int WIDTH = 11;
	private final int HEIGHT = 13;
	
	private SpriteSheet sprite;
	
	
	Player(){
		sprite = new SpriteSheet("/spritesheet.png");
		sprites = new BufferedImage[Game.MAX_PLATAFORMS];
		sprites[0] = sprite.getSprite(0, 0, WIDTH, HEIGHT);
		sprites[1] = sprite.getSprite(11, 0, WIDTH, HEIGHT);
		
		this.x = 0;
		this.dx = 1;
		this.plataform = 2;
		this.y = 17;
	}
	
	public void goDown() {
		plataform++;
		
		if(plataform > Game.MAX_PLATAFORMS) 
			plataform--;	
		
	}
	
	public void goUp() {
		plataform--;
		
		if(plataform == 0)
			plataform++;
	}
	
	public void tick() {
		contToChangeFrame++;
		
		// Change Plataform
		
		switch(plataform) {
		case 1:
			y = 17;
			break;
		case 2:
			y = 57;
			break;
		case 3:
			y = 97;	
		}
		
		if(contToChangeFrame == framesForChangeAnimation) {
			currentFrame++;
			
			if(currentFrame >= MAX_FRAMES) {
				currentFrame = 0;
			}
			
			contToChangeFrame = 0;
		}
		
		if(WIDTH > 0) {
			if(x >= Game.WIDTH - WIDTH) {
				dx*= -1;
				WIDTH *= -1; // Invert Sprite
				Game.obstacle.destroy();
			}
		} else {
			if(x + WIDTH <= 0) {
				dx*= -1;
				WIDTH *= -1; // Invert Sprite
				Game.obstacle.destroy();
			}
		}
		
		x+= dx*speed;
					
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[currentFrame], (int)x, (int)y, WIDTH, HEIGHT, null);
	}
}
