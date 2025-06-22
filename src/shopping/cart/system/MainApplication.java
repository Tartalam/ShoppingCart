package shopping.cart.system;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApplication extends JFrame{
	
	private JPanel cards;
	private CardLayout cardLayout;
	private NavigationControl navController;
	
	public MainApplication() {
		
		// Setting for window
		setTitle("Shoping Cart App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1600,900);
		
		// Initialize card layout and container panel
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		
		//create navigation controller
		navController = new NavigationControl(cardLayout, cards);
		
		// Create and add panels
		HomePanel homePanel = new HomePanel(navController);
		
		cards.add(homePanel, "HOME");
		
		//add cards to the  frame
		getContentPane().add(cards);
		
		navController.showPanel("HOME");
		setVisible(true);
		
		
	}
	

}
