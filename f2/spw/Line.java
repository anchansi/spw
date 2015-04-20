package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Line extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	BufferedImage line;

	private int step = 4;
	private boolean alive = true;
	
	public Line(int x, int y) {
		super(x, y, 50, 40); 	
		try{
				line = ImageIO.read(new File("f2/image/red.jpg"));
		}
		catch(IOException d){

		}																	//5,10
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}

		// g.setColor(Color.green);													//red
		// g.fillRect(x, y, width, height);

			g.drawImage(line,x,y,width,height,null);

		
	}
		

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}