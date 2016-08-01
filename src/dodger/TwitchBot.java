package dodger;

import org.jibble.pircbot.PircBot;

public class TwitchBot extends PircBot{
	private HealthBarManager healthBarManager;
	public TwitchBot(HealthBarManager healthBarManager) {
		setName(Main.botName);
		this.healthBarManager = healthBarManager;
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {	
		while(message.contains("cheer")){
			message = message.substring(message.indexOf("cheer"));
			String[] explodedString = message.split("\\s+", 2);
			int damage;
			try{
				damage = Integer.parseInt(explodedString[0].substring(5));
			}catch(NumberFormatException e){
				message = message.substring(5);
				continue;
			}
			healthBarManager.subtractHealth(damage);
			break;
		}
	}
}
