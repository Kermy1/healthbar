package dodger;


/**
 * This class acts as a pass through between the twitch bot and the healthbar view.
 * @author Kermy
 *
 */
public class HealthBarManager{
	private Frame view;
	public HealthBarManager(){
		view = new Frame();		
	}
	
	/*
	 * This method removes a specified amount of health from the current health.
	 * @param int amount of health to be subtracted.
	 */
	public synchronized void subtractHealth(int amount){
		if(amount>Main.health){
			view.animateHealthBar(0);
		}else{
			view.animateHealthBar(Main.health-amount);
		}
	}
	
	/*
	 * This method sets the current health to the specified health.
	 * @param health
	 */
	public synchronized void setHealth(int amount){
		view.animateHealthBar(amount);
	}
}
