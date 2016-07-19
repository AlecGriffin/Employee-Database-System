import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JFrame{

	// Declare Panels
	JPanel mainLoginPanel;
	private JPanel loginScreen;
	private JPanel optionsPanel;

	//Declare Components 
	JLabel usernameLabel;
	JLabel passwordLabel;
	JButton submitButton;
	JTextField usernameField;
	JPasswordField passwordField;
	CardLayout cardLayout;

	public GUI(){
		initGUI();
	}

	//post: Sets Up mainPanel
	public void initGUI(){
		cardLayout = new CardLayout();
		
		this.getContentPane().setLayout(cardLayout);
		this.setSize(1000,800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		displayLoginPanel();
		cardLayout.show(GUI.this.getContentPane(), "MainOptionsPanel"); // FOR TESTING!!!
		
		this.setVisible(true);
		

	}

	public void displayLoginPanel(){
		
		//Panels
		mainLoginPanel = new JPanel();
		mainLoginPanel.setLayout(new BoxLayout(mainLoginPanel, BoxLayout.PAGE_AXIS));
		optionsPanel = new JPanel();
		optionsPanel.setBackground(Color.red); // FOR TESTING
		
		loginScreen = new JPanel(new GridLayout(3, 2));
		loginScreen.setBackground(Color.LIGHT_GRAY);
		loginScreen.setMaximumSize(new Dimension(400, 100));

		// Instantiate Components
		usernameField = new JTextField(15);
		passwordField = new JPasswordField(15);
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		submitButton = new JButton("Log In");

		// Adding components to the loginScreen panel
		this.getContentPane().add(mainLoginPanel, "mainContentPanel");
		this.getContentPane().add(optionsPanel, "optionsPanel");
		this.getContentPane().add(new MainOptionsPanel(), "MainOptionsPanel");
		loginScreen.add(usernameLabel);
		loginScreen.add(usernameField);		
		loginScreen.add(passwordLabel);
		loginScreen.add(passwordField);
		loginScreen.add(submitButton);

		// Dimensions for Filler Objects (Used to create Filler BOxes)
		Dimension minSize = new Dimension(600, 250);
		Dimension prefSize = new Dimension(600, 250);
		Dimension maxSize = new Dimension(600, 250);

		//Add components to the main JFrame
		mainLoginPanel.add(new Box.Filler(minSize, prefSize, maxSize));
		mainLoginPanel.add(loginScreen);
		mainLoginPanel.add(new Box.Filler(minSize, prefSize, maxSize));
		
		// Adding Listener Object to Component with an anonymous class
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());

				// 1.) Check if username and password pair is in the database.
				if(Login_Verification.verifyLoginCredentials(username, password)){
					//2a.) If so, login to system
					cardLayout.show(GUI.this.getContentPane(), "MainOptionsPanel");
					
				}else{
					//2b.) If not, delete passwordField and display text stating that the username/password commbination
					// was wrong and ask for the client to try again.


				}
			}
		});
	}

	public static void main(String[] args){
		GUI gui = new GUI();
	}
}
