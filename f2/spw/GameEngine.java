package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Line> lines = new ArrayList<Line>();
	private SpaceShip v;	
	
	private Timer timer;
	public int move_line = 0;				
	private long score = 1;
	private double difficulty = 0.3;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(20, new ActionListener() {  								//50
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	
	private void generateEnemy(){
																						
				Enemy e = new Enemy((int)(Math.random()*390),10); 						//(Math.random()*390), 30)
				gp.sprites.add(e);
				enemies.add(e);
		  
	}	
	private void generateLine(){
			     // for(int i=0;i<2;i++){		
					
					Line w = new Line(moveline(),1); 
					gp.sprites.add(w);
					lines.add(w);			//(Math.random()*390), 30) 
					
				
					Line p = new Line(moveline()+300,20); 
					gp.sprites.add(p);
					lines.add(p);			//(Math.random()*390), 30) 
				
			    //}
		  
	}	

	private int moveline(){
		int n = (int)(Math.random()*2);
		if(n == 0 && move_line < 400)
			move_line += 2;
		if(n == 1 && move_line > 0)
			move_line -= 2;
		if(move_line > 400)
			move_line -= 2;
		if(move_line < 0)
			move_line += 2;
		return move_line;

	}

	private void process(){
		generateLine();
		
		if(Math.random() < difficulty){                       
			generateEnemy();
		}            
		
		
		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Line> p_iter = lines.iterator();

		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				
			}
			
			
		}
		while(p_iter.hasNext()){
			Line p = p_iter.next();
				p.proceed();
				if(!p.isAlive()){
					p_iter.remove();
					gp.sprites.remove(p);
					
				}
		}
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				
				gp.sprites.remove(e);
				score +=10;
																
			}
		}
		
		



		Rectangle2D.Double pr;
		for(Line p : lines){
			pr = p.getRectangle();
			if(pr.intersects(vr)){
				score -=1;											
			}
		}
	}
	
	public void die(){
		timer.stop();
	}
	

	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.moveLR(-1);						
			break;
		case KeyEvent.VK_RIGHT:
			v.moveLR(1);												
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		 case KeyEvent.VK_UP:
		 	v.moveUD(-1);
		 	break;						
		 case KeyEvent.VK_DOWN:
		 	 v.moveUD(1);						
		 	break;
		case KeyEvent.VK_Z:
		 	die();	
		 	break;					
		 case KeyEvent.VK_X:
		 	start();	
		 	break;						//time play
		 case KeyEvent.VK_R:
		 	score = 0;						//resetscore
		 	break;
		 
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
