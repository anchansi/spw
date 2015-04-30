package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Goal extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	public int a = 1;
	private int step = 5;
	private boolean alive = true;
	
	 BufferedImage goal;


	

	public Goal(int x, int y){
		 super(x, y, 90, 80); 																		//5,10
		 try{
		 		goal = ImageIO.read(new File("f2/image/start.png"));
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

		
		// g.setColor(Color.orange);																	//red
		// g.fillRect(x, y, width, height);

		 g.drawImage(goal,x,y,width,height,null);
	}
		

	public void proceed(){
	
		
	
		  if (x < 0 ){
		 	a = 1;
		 	
		 }
		  else if (a ==0 ){
			x += step;
		}

		else if (x > 0 && a == 1 && x < 400){
			x += step;
		}

		else if(x > 0 && x > 400){
			
			a = 0; 
		}


		else if(a == 0){
			
			x -= step; 
		}



	}
	
	public boolean isAlive(){
		return alive;
	}
}