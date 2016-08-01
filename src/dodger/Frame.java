package dodger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;



/**
 * This class is the view for this application.
 * @author Kermy
 *
 */
@SuppressWarnings("serial")
public class Frame extends JFrame{
	private HealthBarView healthBarView;
	private InputPanel inputPanel;
	private Timer swingTimer;
	public Frame() {
		setTitle("HealthBar");
		
		healthBarView = new HealthBarView();
		getContentPane().add(healthBarView, BorderLayout.CENTER);
		
		inputPanel = new InputPanel();
		getContentPane().add(inputPanel, BorderLayout.PAGE_END);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/*
	 * This method repaints the healthbar.
	 */
	public void updateView(){
		healthBarView.repaint();
	}
	
	/*
	 * This method will make the current health go to the desired health in a loop.
	 * @param int desiredHealth
	 */
	public synchronized void animateHealthBar(int futureHealth){		
		int difference = Math.abs(Main.health-futureHealth);
		double amount = (double)difference/(double)Main.maxHealth*((double)Main.maxHealth/500)*100;
		if(Main.health>futureHealth&&(int)amount>0){
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if(Main.health>futureHealth&&Main.health>=(int)amount){
						Main.health -= (int)amount;
						updateView();
					}else{
						Main.health=futureHealth;
						updateView();
						swingTimer.stop();
					}
			    }
			};
			swingTimer = new Timer(50, taskPerformer);
			swingTimer.start();
		}else if(Main.health<futureHealth&&(int)amount>0){
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if(Main.health<futureHealth&&Main.health<=futureHealth-(int)amount){
						Main.health += (int)amount;
						updateView();
					}else{
						Main.health=futureHealth;
						updateView();
						swingTimer.stop();
					}
			    }
			};
			swingTimer = new Timer(50, taskPerformer);
			swingTimer.start();
		}
	}
	
	/*
	 * This class is the healthbar view.
	 */
	private class HealthBarView extends JPanel{
		public HealthBarView(){
			setBackground(new Color(0,255,0));
			setPreferredSize(new Dimension(600, 100));
		}
		
		public void paint(Graphics g) {
			super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	     
			//white filling
			g2d.setColor(new Color(255,255,255));
			g2d.fillRect(10, 10, 500, 40);
			//red filling
			g2d.setColor(new Color(255,0,0));
			double currentHealth = Main.health;
			double maxHealth = Main.maxHealth;
			g2d.fillRect(11, 11, (int)(currentHealth/maxHealth*500)-1, 38);
			g2d.setColor(new Color(255,30,30));
			g2d.fillRect(11, 11, (int)(currentHealth/maxHealth*500)-1, 3);
			g2d.setColor(new Color(220,0,0));
			g2d.fillRect(11, 45, (int)(currentHealth/maxHealth*500)-1, 5);
			//outline
            g2d.setColor(new Color(0,0,0));
			g2d.drawRect(10, 10, 500, 40);
			g2d.drawString(Main.health+"/"+Main.maxHealth, 235, 65);
        }
	}
	
	/*
	 * This class is the input view and controller. 
	 */
	private class InputPanel extends JPanel implements ActionListener{
		private JButton resetButton;
		private JButton setMaxHealthButton;
		private JButton setHealthButton;
		private JTextField maxHealthInput;
		private JTextField healthInput;
		public InputPanel(){
			resetButton = new JButton("reset");
			resetButton.addActionListener(this);
			add(resetButton);
			
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
			maxHealthInput = new JTextField();
			maxHealthInput.setPreferredSize(new Dimension(100,27));
			maxHealthInput.setText(Main.maxHealth+"");
			healthInput = new JTextField();
			healthInput.setPreferredSize(new Dimension(100,27));
			healthInput.setText(Main.health+"");
			setHealthButton = new JButton("Set current health");
			setHealthButton.addActionListener(this);
			setMaxHealthButton = new JButton("Set max health");
			setMaxHealthButton.addActionListener(this);
			panel.add(healthInput);
			panel.add(setHealthButton);
			panel.add(maxHealthInput);
			panel.add(setMaxHealthButton);
			add(panel);					
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==resetButton){
				animateHealthBar(Main.maxHealth);
			}else if(e.getSource()==setHealthButton){
				try {
					animateHealthBar(Integer.parseInt(healthInput.getText()));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}
				updateView();
			}else if(e.getSource()==setMaxHealthButton){
				Main.health = Integer.parseInt(maxHealthInput.getText());
				Main.maxHealth = Integer.parseInt(maxHealthInput.getText());
				updateView();
			}
		}
		
	}
}
