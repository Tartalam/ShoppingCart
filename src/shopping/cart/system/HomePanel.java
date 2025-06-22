package shopping.cart.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class HomePanel extends JPanel implements ActionListener {
    
    private NavigationControl navController;
    
    public HomePanel(NavigationControl navController) {
        this.navController = navController;
        initializeUI();
    }
    
    public void initializeUI() {
        // Main layout
        setLayout(new BorderLayout());

        // Header container (subPanel1)
        JPanel subPanel1 = new JPanel(new GridBagLayout());
        subPanel1.setBackground(Color.red);
        subPanel1.setPreferredSize(new Dimension(0, 120)); // Header height

        // Body container (subPanel2)
        JPanel subPanel2 = new JPanel();
        subPanel2.setBackground(Color.blue);
        
        // Add to main layout
        add(subPanel1, BorderLayout.NORTH);
        add(subPanel2, BorderLayout.CENTER);


        // Three horizontal panels inside the header
        JPanel headPanel1 = new JPanel();
        JPanel headPanel2 = new JPanel();
        JPanel headPanel3 = new JPanel();

        // Set colors for visibility
        headPanel1.setBackground(Color.GREEN);
        headPanel2.setBackground(Color.MAGENTA);
        headPanel3.setBackground(Color.ORANGE);

        // Configure GridBagConstraints for horizontal layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill available space
        gbc.weightx = 1.0; // Equal horizontal distribution
        gbc.weighty = 1.0; // Take full height
        gbc.insets = new Insets(0, 0, 0, 0); // No padding

        // Panel 1 (Left)
        gbc.gridx = 0;
        gbc.weightx = 2.2;
        subPanel1.add(headPanel1, gbc);

        // Panel 2 (Middle)
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        subPanel1.add(headPanel2, gbc);

        // Panel 3 (Right)
        gbc.gridx = 2;
        gbc.weightx = 1.2;
        subPanel1.add(headPanel3, gbc);
        
        // Search box setting and design
        JTextField searchBox = new JTextField();
        
        //change layout of the first header partition 
        headPanel1.setLayout(new GridBagLayout());
        gbc.weightx = 1.0;
        gbc.insets = new Insets(40,30,40,0);
        gbc.anchor = GridBagConstraints.CENTER;
        
        headPanel1.add(searchBox, gbc);
        
        //Search button design and setting
        JButton searchButton = new JButton();
        headPanel2.setLayout(new BorderLayout());
        searchButton.setPreferredSize(new Dimension(40,30));
        // Add to LINE_START to prevent stretching
        headPanel2.setBorder(new EmptyBorder(40,0,40,0));
        headPanel2.add(searchButton, BorderLayout.LINE_START); 
        
        
        


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Action handling (if needed)
    }
}