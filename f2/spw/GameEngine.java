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
	private ArrayList<Shark> sharks = new ArrayList<Shark>();
	private SpaceShip v;	
	
	private Timer timer;
	public int move_line = 0;				
	private long score = 0;
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
																						
				Enemy e = new Enemy((int)(Math.random()*390),5); 						//(Math.random()*390), 30)
				gp.sprites.add(e);
				enemies.add(e);
		  
	}	
	private void generateLine(){
			     // for(int i=0;i<2;i++){		
					
					Line w = new Line(moveline()+(int)(Math.random()*20),0); 
					gp.sprites.add(w);
					lines.add(w);			//(Math.random()*390), 30) 
					
				
					Line p = new Line(moveline()+300+(int)(Math.random()*20),20); 
					gp.sprites.add(p);
					lines.add(p);			//(Math.random()*390), 30) 
				
			    //}
		  
	}	

	private void generateShark(){
																							
					Shark s = new Shark(0,(int)(Math.random()*390)); 						//(Math.random()*390), 30)
					gp.sprites.add(s);
					sharks.add(s);
			  
	}	

	private int moveline(){
		int n = (int)(Math.random()*2);
		if(n == 0 && move_line < 400)
			move_line += 1;
		if(n == 1 && move_line > 0)
			move_line -= 1;
		if(move_line > 300)
			move_line -= 4;
		if(move_line < 0)
			move_line += 4;
		return move_line;

	}

	private void process(){
		
		long sh = getScore();
		sh = sh%100;

		if( sh%10 > 5 || sh%10 == 7)
			generateLine();
		
		if(Math.random() < difficulty){                       
			generateEnemy();
		}  
		if(sh < 10){                       
			generateShark();
		}           
		
		
		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Line> p_iter = lines.iterator();
		Iterator<Shark> s_iter = sharks.iterator();

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

		while(s_iter.hasNext()){
			Shark s = s_iter.next();
				s.proceed();
				if(!s.isAlive()){
					s_iter.remove();
					gp.sprites.remove(s);
					
				}
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				
				gp.sprites.remove(e);
				score +=1;
																
			}
		}
		
		



		Rectangle2D.Double pr;
		for(Line p : lines){
			pr = p.getRectangle();
			if(pr.intersects(vr)){
				v.moveLR(1);	
				score -= 10;										
			}
		}

		Rectangle2D.Double sr;
				for(Shark s : sharks){
					sr = s.getRectangle();
					if(sr.intersects(vr)){
						die();											
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
