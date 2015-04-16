package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SpaceShip extends Sprite{

	int step = 20;
	BufferedImage boat;

	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);


			try{
				boat = ImageIO.read(new File("f2/image/boat.png"));
			}
			catch(IOException d){

			}
		
		
		

	}

	@Override
	public void draw(Graphics2D g) {
		// g.setColor(Color.red);							//blue
		// g.fillRect(x, y, width, height);

		g.drawImage(boat,x,y,width,height,null);
		
	}

	public void moveLR(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
		 	x = 400 - width;

	}
	public void moveUD(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 600 - width)
			y = 600 - width;
	}

	
}
