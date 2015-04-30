package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Bomb extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 8;
	private boolean alive = true;
	
	BufferedImage bomb;


	

	public Bomb(int x, int y){
		 super(x, y, 30, 40); 																		//5,10
		try{
				bomb = ImageIO.read(new File("f2/image/car.png"));
		}
		catch(IOException d){

		}
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}

		// if((int)(Math.random()*2) == 0)	
		// 	g.setColor(Color.orange);			
		// else
		// 	g.setColor(Color.white);														//red
		// g.fillRect(x, y, width, height);

		g.drawImage(bomb,x,y,width,height,null);
	}
		

	public void proceed(){
		int a = (int)(Math.random()*3);
		y -= step;
		
		if(y > Y_TO_DIE){
			alive = false;
		}

		else if(x > Y_TO_FADE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}