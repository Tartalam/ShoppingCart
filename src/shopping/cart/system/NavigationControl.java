package shopping.cart.system;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class NavigationControl {
	
	private CardLayout cardLayout;
	private JPanel cards;
	
	// primary constructor
	NavigationControl(CardLayout cardLayout, JPanel cards){
		
		this.cardLayout = cardLayout;
		this.cards = cards;
	}
	
	// method that displays the selected panel
	public void showPanel(String panelName) {
		
		cardLayout.show(cards, panelName);
	}
	
	// method that moves user back to the home page.
	public void goHome() {
		
		showPanel("Home");
	}
	
	// used to implement back navigation logic
	public void goBack() {
		
	}

}
