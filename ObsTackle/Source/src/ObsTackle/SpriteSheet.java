package ObsTackle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private BufferedImage image;
	
	SpriteSheet(String address){
		try {
			image = ImageIO.read(getClass().getResource(address));
		}
		catch (IOException error) {
			error.getStackTrace();
			System.out.println("Error opening file");
		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return image.getSubimage(x, y, width, height);
	}
	
	
}
