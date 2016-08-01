package dodger;

import java.io.IOException;
import org.jibble.pircbot.IrcException;

/**
 * This class initializes the application.
 * @author Kermy
 *
 */
public class Main {
	//default healthbar values
	public static int health = 5000;
	public static int maxHealth = 5000;
	
	//change these variables to the right values for your bot.
	public static String botName = "kermybot";
	public static String channelName = "dexteritybonus";
	public static String botAPIPassword = "oauth:a1vxs3ey0xm94ltbbppngxhlqqxn3l";

	public static void main(String[] args){
		HealthBarManager healthBarManager = new HealthBarManager();
		
		//twitch bot
		TwitchBot bot = new TwitchBot(healthBarManager);     
        //bot.setVerbose(true);    //debug mode
        try {
			bot.connect("irc.chat.twitch.tv", 6667, Main.botAPIPassword);
		} catch (IOException | IrcException e) {
			e.printStackTrace();
		}
        bot.joinChannel("#"+Main.channelName);    
	}

}
