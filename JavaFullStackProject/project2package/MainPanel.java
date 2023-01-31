package project2package;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.io.File;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * 
 * @author Tim Webb
 * 10/15/2021
 * CSCI 3381
 * 
 */

public class MainPanel extends JPanel {
	private final int WIDTH = 600, HEIGHT = 500;
	final JFileChooser fc = new JFileChooser();

	//Panel objects
	private JPanel layeredPane;
	private JPanel homePanel;
	private JPanel predictTweetsPanel;
	private JPanel addRemoveModifyPanel;
	
	private JTextArea usernameTextArea_1;
	private JTextArea idsTextArea_1;
	private JTextArea textAreaTweets;
	private JTextArea idsTextArea;
	private JScrollPane tweetIdScrollPane;
	private JTextArea usernameTextArea;
	private JScrollPane usernamesScrollPane;

	private JLabel lblFile; 
	private JButton btnToPredictTweets;
	private JButton btnHome;
	private TweetCollection tc;
	private JTextArea idTextField;
	private JRadioButton rdbtnPredictSingle;
	private JLabel lblEnterMessage;
	private JComboBox<String> comboBox;

	private JLabel lblUsernames;
	private HashSet<String> ids;
	private JButton btnPredict;
	private JLabel lblPrediction;
	private JTextArea textAreaPOutput;
	private JButton btnAddremovemodify;
	private JLabel lbl_ids;
	private JLabel lblPolarity;
	private JLabel lblId;
	private JLabel lblUsername;
	private JLabel lblBodyText;
	private JTextField textFieldId;
	private JTextField textFieldUsername;
	private JTextArea textAreaBodyText;
	private JLabel lblErrorLabel;
	private JButton btnARM;
	private JTextField textFieldEnterID;
	private JButton btnGET;
	private JLabel lblARM;
	private Tweet tweetModify;
	private Tweet holderTweet;
	private JComboBox<String> comboBoxPolarity;
	private String filename;
	private JButton btnChooseTweetFile;
	private ImageIcon logoBird;
	private ImageIcon speechBubble1;
	private ImageIcon speechBubble2;
	private ImageIcon mainLogo;
	private ImageIcon staticBird;
	private ImageIcon bbTweeter;
	private JLabel lblNewLabel;
	private JLabel lblHome;
	private JLabel lblHelp;
	private JLabel lblTweetingLeft;
	private JButton btnHelp;
	private JScrollPane tweetIdScrollPaneARMP;
	private JScrollPane usernamesScrollPaneARMP;
	private JScrollPane scrollPane;
	private JSlider slider;
	private JRadioButton rdbtnPredictAll;
	private JComboBox<String> comboBoxARM;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField sliderTextField;
	private JLabel lblNumberOfTweets;
	private JButton btnUndoModification;
	//-----------------------------------------------------------------
	// Sets up the panel, including the timer for the animation.
	//-----------------------------------------------------------------
	public MainPanel()
	{
		super(new BorderLayout());
		
		/**
		 * New Tweet Collection for use in MainPanel
		 * filename holds name of textFile with tweets.
		 */
		tc = new TweetCollection("testTweets.txt");
		filename = "testTweets.txt";
		
		/**
		 * Images for panels
		 */
		logoBird = new ImageIcon(MainPanel.class.getResource("/project2package/AnimatedBB.gif"));
		speechBubble1 = new ImageIcon(MainPanel.class.getResource("/project2package/speechBubble.png"));
		speechBubble2 = new ImageIcon(MainPanel.class.getResource("/project2package/speechBubble2.png"));
		mainLogo = new ImageIcon(MainPanel.class.getResource("/project2package/logo.png"));
		staticBird = new ImageIcon(MainPanel.class.getResource("/project2package/StaticBB.png"));
		bbTweeter = new ImageIcon(MainPanel.class.getResource("/project2package/tweeter.gif"));
		
		/**
		 * Layered Panel for panel switching (switchPanel(Panel));
		 */
		layeredPane = new JPanel();
		layeredPane.setPreferredSize (new Dimension(WIDTH, HEIGHT));
		layeredPane.setBackground (Color.black);
		add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		/**
		 * Home Panel for Home Screen
		 */
		homePanel = new JPanel();
		homePanel.setBackground(SystemColor.activeCaption);
		homePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		layeredPane.add(homePanel, "name_8618380643300");
		homePanel.setLayout(null);
		
		/**
		 * Predict Panel for Predicting Tweets
		 */
		predictTweetsPanel = new JPanel();
		predictTweetsPanel.setBackground(SystemColor.activeCaption);
		predictTweetsPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		layeredPane.add(predictTweetsPanel, "name_8619732913000");
		predictTweetsPanel.setLayout(null);
		
		/**
		 * ARM Panel for Add/Remove/Modify tweets
		 */
		addRemoveModifyPanel = new JPanel();
		addRemoveModifyPanel.setBackground(SystemColor.activeCaption);
		addRemoveModifyPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		layeredPane.add(addRemoveModifyPanel, "name_8621114938900");
		addRemoveModifyPanel.setLayout(null);
		
		
		/**
		 *
		 */
		btnToPredictTweets = new JButton("Predict Tweets");
		buttonDesigner(btnToPredictTweets);
		btnToPredictTweets.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		btnToPredictTweets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(predictTweetsPanel);
			}
		});
		btnToPredictTweets.setBounds(22, 466, 160, 25);
		homePanel.add(btnToPredictTweets);
		
		//Sets logo at top of page
		lblHome = new JLabel("Home");
		lblHome.setIcon(mainLogo);
		lblHome.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblHome.setBounds(203, 11, 200, 40);
		homePanel.add(lblHome);
		
		/**
		 * IDs Text Area
		 */
		tweetIdScrollPane = new JScrollPane();
		tweetIdScrollPane.setBounds(22, 74, 130, 350);
		homePanel.add(tweetIdScrollPane);
		idsTextArea = new JTextArea();
		idsTextArea.setEditable(false);
		tweetIdScrollPane.setViewportView(idsTextArea);

		/**
		 * Username Text Area
		 */
		usernamesScrollPane = new JScrollPane();
		usernamesScrollPane.setBounds(182, 75, 130, 350);
		homePanel.add(usernamesScrollPane);
		usernameTextArea = new JTextArea();
		usernameTextArea.setEditable(false);
		usernamesScrollPane.setViewportView(usernameTextArea);
		
		/**
		 * Shows all tweets or 200 if greater than 200.
		 */
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 207, 399);
		predictTweetsPanel.add(scrollPane);
		textAreaTweets = new JTextArea();
		textAreaTweets.setEditable(false);
		scrollPane.setViewportView(textAreaTweets);
		
		//lbl above Usernames textArea
		lblUsernames = new JLabel("Usernames");
		lblUsernames.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblUsernames.setBounds(206, 55, 109, 14);
		homePanel.add(lblUsernames);
		
		//ARM button
		btnAddremovemodify = new JButton("Add/Remove/Modify");
		buttonDesigner(btnAddremovemodify);
		btnAddremovemodify.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		btnAddremovemodify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(addRemoveModifyPanel);
			}
		});
		btnAddremovemodify.setBounds(208, 466, 160, 25);
		homePanel.add(btnAddremovemodify);
		
		//label above idTextArea
		lbl_ids = new JLabel("IDs");
		lbl_ids.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lbl_ids.setBounds(68, 55, 75, 14);
		homePanel.add(lbl_ids);
		
		/**
		 * Used to choose and upload a new tweet file. This listener will also set slider intervals and update the text areas to ensure information is consistent.
		 */
		btnChooseTweetFile = new JButton("Select Tweets File");
		buttonDesigner(btnChooseTweetFile);
		btnChooseTweetFile.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		btnChooseTweetFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Handle open button action.
			    if (e.getSource() == btnChooseTweetFile) {
			        int returnVal = fc.showOpenDialog(MainPanel.this);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            tc.empty();
			            
			            //This is where a real application would open the file.
			            lblFile.setText("File: " + file.getName() + ".");
			            TweetCollection tc2 = new TweetCollection(file.getName());
			            tc = tc2;
			            filename = file.getName();
			            updateTextAreas();
			            if(tc.getSize() > 400)
			            	slider.setMajorTickSpacing(tc.getSize());
			            else
			            	slider.setMajorTickSpacing(tc.getSize());
			        } else {
			            lblFile.setText("Open command cancelled by user.");
			        }
			   }
			    slider.setMaximum(tc.getSize());
			    updateTextAreas();
			}
		});
		btnChooseTweetFile.setBounds(350, 76, 220, 23);
		homePanel.add(btnChooseTweetFile);
		
	
		/**
		 * displays error or file gotten
		 */
		lblFile = new JLabel("File:");
		lblFile.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblFile.setBounds(350, 110, 220, 14);
		homePanel.add(lblFile);
		
		//Shows animated gif left of logo
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(logoBird);
		lblNewLabel.setBounds(160, 11, 63, 40);
		homePanel.add(lblNewLabel);
		
		//Bird Button to show help bubbles. Cycles through two bubbles and the tweeter.
		btnHelp = new JButton("");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lblTweetingLeft.isVisible()) {
					lblHelp.setVisible(true);
					lblTweetingLeft.setVisible(false);
				}
				else if(lblHelp.isVisible()){
					if(lblHelp.getIcon().equals(speechBubble1))
						lblHelp.setIcon(speechBubble2);
					else {
					lblHelp.setVisible(false);
					lblTweetingLeft.setVisible(true);
					}
				}
			}
		});
		btnHelp.setOpaque(false);
		btnHelp.setContentAreaFilled(false);
		btnHelp.setBorderPainted(false);
		btnHelp.setIcon(staticBird);
		btnHelp.setBounds(323, 291, 130, 133);
		homePanel.add(btnHelp);
		
		// Speech Bubble
		lblHelp = new JLabel("");
		lblHelp.setIcon(speechBubble1);
		lblHelp.setVisible(false);
		lblHelp.setBounds(442, 170, 158, 169);
		homePanel.add(lblHelp);
		
		// Tweet gif
		lblTweetingLeft = new JLabel("");
		lblTweetingLeft.setIcon(bbTweeter);
		lblTweetingLeft.setVisible(true);
		lblTweetingLeft.setBounds(322, 224, 81, 74);
		homePanel.add(lblTweetingLeft);

		// Home Button
		btnHome = new JButton("Home");
		btnHome.setBounds(501, 466, 89, 25);
		buttonDesigner(btnHome);
		btnHome.setEnabled(false);
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(homePanel);
			}
		});
		homePanel.add(btnHome);
		

		/**
		 * Sets up panel for predicting all tweets. Will show predict button and slider w/ slider text field.
		 */
		rdbtnPredictAll = new JRadioButton("Predict All");
		rdbtnPredictAll.setSelected(true);
		buttonGroup.add(rdbtnPredictAll);
		rdbtnPredictAll.setBackground(SystemColor.activeCaption);
		rdbtnPredictAll.setBounds(235, 56, 109, 23);
		rdbtnPredictAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNumberOfTweets.setVisible(true);
				btnPredict.setEnabled(true);
				slider.setVisible(true);
				idTextField.setEditable(false);
				comboBox.setVisible(false);
				lblEnterMessage.setVisible(false);
				idTextField.setVisible(false);
				idTextField.setEditable(false);
				sliderTextField.setVisible(true);
			}
		});
		predictTweetsPanel.add(rdbtnPredictAll);
		
		/**
		 * Sets up panel for predicting single tweet. There can be either predict with ID, or predict with your own entered body text. 
		 */
		rdbtnPredictSingle = new JRadioButton("Predict Single");
		buttonGroup.add(rdbtnPredictSingle);
		rdbtnPredictSingle.setBackground(SystemColor.activeCaption);
		rdbtnPredictSingle.setBounds(235, 85, 109, 23);
		rdbtnPredictSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBox.getSelectedItem();
				idTextField.setEditable(true);
				comboBox.setVisible(true);
				slider.setVisible(false);
				sliderTextField.setVisible(false);
				lblNumberOfTweets.setVisible(false);
				if(selected.equals("Predict With ID")) {
					lblEnterMessage.setVisible(true);
					idTextField.setVisible(true);
					idTextField.setEditable(true);
				}
				else if(selected.equals("Predict With Body Text")) {
					lblEnterMessage.setVisible(true);
					idTextField.setVisible(true);
					idTextField.setEditable(true);
					
				}
				else {
					lblEnterMessage.setVisible(false);
					idTextField.setVisible(false);
					idTextField.setEditable(false);
				}
			}
		});
		predictTweetsPanel.add(rdbtnPredictSingle);
		
		/**
		 * textField that disables/enables the predict button based on whether or not text is in the box. 
		 * How do I turn this into a method to be resued...
		 */
		idTextField = new JTextArea();
		idTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    enableButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    enableButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    enableButton();
			}

			public void enableButton() {
			     if (idTextField.getText().equals(""))
			     {
			        btnPredict.setEnabled(false);
			     }
			     else
			     {
			        btnPredict.setEnabled(true);
			     }
			  }
			});
		idTextField.setVisible(false);
		idTextField.setLineWrap(true);
		idTextField.setBounds(340, 148, 100, 20);
		idTextField.setEditable(false);
		predictTweetsPanel.add(idTextField);
		
		/**
		 * used to tell user to enter body text or ID
		 */
		lblEnterMessage = new JLabel("Enter Body Text:");
		lblEnterMessage.setBounds(244, 151, 97, 14);
		lblEnterMessage.setVisible(false);
		predictTweetsPanel.add(lblEnterMessage);
		
		/**
		 * comboBox that brings to visibility certain components when different options are selected.
		 */
		comboBox = new JComboBox<String>();
		comboBox.setBounds(245, 115, 132, 22);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBox.getSelectedItem();
				if(selected.equals("Predict With ID")) {
					lblEnterMessage.setText("Enter Tweet ID:");
					lblEnterMessage.setVisible(true);
					idTextField.setSize(new Dimension(50,20));
					idTextField.setVisible(true);
					idTextField.setEditable(true);
					idTextField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
				}
				else if(selected.equals("Predict With Body Text")) {
					lblEnterMessage.setText("Enter Body Text:");
					lblEnterMessage.setVisible(true);
					idTextField.setSize(new Dimension(200,100));
					idTextField.setText("");
					idTextField.getDocument().putProperty("filterNewlines", Boolean.FALSE);
					idTextField.setVisible(true);
					idTextField.setEditable(true);
				}
				else {
					lblEnterMessage.setVisible(false);
					idTextField.setVisible(false);
					idTextField.setEditable(false);
					btnPredict.setEnabled(false);
				}
			}
		});
		comboBox.setVisible(false);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {" ", "Predict With ID", "Predict With Body Text"}));
		comboBox.setMaximumRowCount(3);
		predictTweetsPanel.add(comboBox);
		
		/**
		 * JButton to predict either a single Tweet or a TweetCollection. 
		 * Calls tc.Predict on a single tweet or collection to return information back to panel.
		 * Has some error protection as well. 
		 * 
		 */
		btnPredict = new JButton("Predict");
		btnPredict.setEnabled(true);
		buttonDesigner(btnPredict);
		btnPredict.setForeground(Color.BLACK);
		btnPredict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBox.getSelectedItem();
				if(rdbtnPredictSingle.isSelected() && selected.equals("Predict With ID")) {
					try {
						textAreaPOutput.setSize(new Dimension(50,20));
						Tweet t = new Tweet();
						
						t = tc.get(Long.valueOf(idTextField.getText().replace(" ", "")));
						int output = tc.predict(t);						
						textAreaPOutput.setText(String.valueOf(output));
					} catch (Exception e2) {
						
						textAreaPOutput.setText("Error!");
					}
				}
				else if(rdbtnPredictSingle.isSelected() && selected.equals("Predict With Body Text")) {
					if(idTextField.equals("")) {
						textAreaPOutput.setText("Error! No Text Entered.");
					}
					Tweet t = new Tweet("",0, "", idTextField.getText());
					textAreaPOutput.setText(String.valueOf(tc.predict(t)));
				}
				else if(rdbtnPredictAll.isSelected()) {
					
					textAreaPOutput.setSize(new Dimension(270,170));
					textAreaPOutput.setText(tc.predict(slider.getValue()));
				}
				tc.resetPdr();
			}
		});
		btnPredict.setBounds(235, 254, 350, 25);
		predictTweetsPanel.add(btnPredict);
		
		/**
		 * Prediction label area.
		 */
		lblPrediction = new JLabel("Prediction:");
		lblPrediction.setBounds(235, 290, 70, 14);
		predictTweetsPanel.add(lblPrediction);
		
		/**
		 * Prediction output area.
		 */
		textAreaPOutput = new JTextArea();
		textAreaPOutput.setEditable(false);
		textAreaPOutput.setBounds(306, 288, 30, 20);
		textAreaPOutput.setBorder(new LineBorder(Color.BLACK));
		predictTweetsPanel.add(textAreaPOutput);
		
		/**
		 * Slider that displays selected index, as well as allowing user to type amount desired in w/ character and other testing.
		 * 
		 * The first KeyListener is looking to see if the entered data is a numerical value. 
		 * If anything other than numerical is entered, disables the predict button
		 * 
		 * The second is a document listener that will check each time any change is made to ensure something is
		 * in the box before enabling the predict button. I added this because I couldn't quite get the first listener
		 * to keep working even if I entered a ltter in the box, but later changed the slider. Just ran out of time.
		 */
		sliderTextField = new JTextField("");
		sliderTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String s = sliderTextField.getText().replace(" ", "");
				if(s.matches("[0-9]+") && s.length() > 0)
				{
					slider.setValue(Integer.valueOf(sliderTextField.getText().replace(" ", "")));
					btnPredict.setEnabled(true);
				}
				else {
					
					btnPredict.setEnabled(false);
				}
			}
		});
		sliderTextField.getDocument().addDocumentListener(new DocumentListener() {
			
			  public void changedUpdate(DocumentEvent e) {
			    enableButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    enableButton(); 
			  }
			  public void insertUpdate(DocumentEvent e) {
			    enableButton();
			}

			public void enableButton() {
			     if (sliderTextField.getText().equals(""))
			     {
			        btnPredict.setEnabled(false);
			     }
			     else
			     {
			        btnPredict.setEnabled(true);
			     }
			  }
			});
		sliderTextField.setBounds(508, 191, 70, 26);
		predictTweetsPanel.add(sliderTextField);
		
		/**
		 * Slider that selects an index to predict. Updates Label on right to show selected index.
		 */
		slider = new JSlider();
		slider.setMinimum(2);
		slider.setMaximum(tc.getSize());
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
					String value = String.valueOf(slider.getValue());
					sliderTextField.setText(value);
			}
		});
		slider.setMajorTickSpacing(tc.getSize()/4);
		slider.setPaintLabels(true);
		slider.setOpaque(true);
		slider.setBackground(SystemColor.activeCaption);
		slider.setPaintTicks(true);
		slider.setBounds(235, 188, 263, 38);
		predictTweetsPanel.add(slider);
		
		/**
		 * Label above slider to tell user what it's for.
		 */
		lblNumberOfTweets = new JLabel("Number of Tweets to Predict");
		lblNumberOfTweets.setBounds(235, 171, 165, 14);
		predictTweetsPanel.add(lblNumberOfTweets);
		
	

		/**
		 * Used for idsTextArea_1
		 */
		tweetIdScrollPaneARMP = new JScrollPane();
		tweetIdScrollPaneARMP.setBounds(22, 74, 130, 350);
		addRemoveModifyPanel.add(tweetIdScrollPaneARMP);
		
		/**
		 * Displays ALL IDs in the TweetCollection.
		 */
		idsTextArea_1 = new JTextArea();
		tweetIdScrollPaneARMP.setViewportView(idsTextArea_1);
		idsTextArea_1.setText("");
		idsTextArea_1.setEditable(false);
		
		/**
		 * Used for usernameTextArea
		 */
		usernamesScrollPaneARMP = new JScrollPane();
		usernamesScrollPaneARMP.setBounds(182, 75, 130, 350);
		addRemoveModifyPanel.add(usernamesScrollPaneARMP);
		
		/**
		 * Displays All Usernames in the TweetCollection. Need to figure out how to display side by side corresponding ID
		 */
		usernameTextArea_1 = new JTextArea();
		usernamesScrollPaneARMP.setViewportView(usernameTextArea_1);
		usernameTextArea_1.setText("");
		usernameTextArea_1.setEditable(false);
		
		/**
		 * ComboBox to select add, remove, or modify.
		 */
		comboBoxARM = new JComboBox<String>();
		comboBoxARM.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lblErrorLabel.setVisible(false);
			}
		});
		comboBoxARM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ARMSetup();			
			}
		});
		comboBoxARM.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Add", "Remove", "Modify"}));
		comboBoxARM.setBounds(350, 76, 144, 23);
		addRemoveModifyPanel.add(comboBoxARM);
		
		/**
		 * Label for 
		 */
		lblARM = new JLabel("Add:");
		lblARM.setBounds(322, 134, 68, 23);
		lblARM.setVisible(false);
		addRemoveModifyPanel.add(lblARM);
		
		/**
		 * Label for textFieldPolarity
		 */
		lblPolarity = new JLabel("Polarity");
		lblPolarity.setBounds(322, 179, 46, 14);
		lblPolarity.setVisible(false);
		addRemoveModifyPanel.add(lblPolarity);
		
		/**
		 * Label for textFieldId
		 */
		lblId = new JLabel("ID");
		lblId.setBounds(322, 204, 46, 14);
		lblId.setVisible(false);
		addRemoveModifyPanel.add(lblId);
		
		/**
		 * Label for textFieldUsername
		 */
		lblUsername = new JLabel("Username");
		lblUsername.setBounds(322, 229, 68, 14);
		lblUsername.setVisible(false);
		addRemoveModifyPanel.add(lblUsername);
		
		/**
		 * Label for textAreaBodyText
		 */
		lblBodyText = new JLabel("Body Text");
		lblBodyText.setBounds(322, 254, 68, 14);
		lblBodyText.setVisible(false);
		addRemoveModifyPanel.add(lblBodyText);
		
		/**
		 * Will display ID
		 */
		textFieldId = new JTextField();
		textFieldId.setEditable(false);
		textFieldId.setBounds(388, 201, 86, 20);
		textFieldId.setVisible(false);
		addRemoveModifyPanel.add(textFieldId);
		textFieldId.setColumns(10);
		
		/**
		 * Displays Username
		 */
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(388, 226, 86, 20);
		textFieldUsername.setVisible(false);
		addRemoveModifyPanel.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		/**
		 * Used to input tweet or display.
		 */
		textAreaBodyText = new JTextArea();
		textAreaBodyText.setLineWrap(true);
		textAreaBodyText.setVisible(false);
		textAreaBodyText.setBounds(388, 254, 202, 63);
		addRemoveModifyPanel.add(textAreaBodyText);
		textAreaBodyText.setColumns(10);
		
		/**
		 * Error Label that is really used for general output as well as errors. 
		 */
		lblErrorLabel = new JLabel("New label");
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setBounds(322, 410, 268, 14);
		lblErrorLabel.setVisible(false);
		addRemoveModifyPanel.add(lblErrorLabel);
		
		/**
		 * Select Polarity (0,2,4)
		 */
		comboBoxPolarity = new JComboBox<String>();
		comboBoxPolarity.setModel(new DefaultComboBoxModel<String>(new String[] {"", "0", "2", "4"}));
		comboBoxPolarity.setVisible(false);
		comboBoxPolarity.setBounds(388, 175, 86, 22);
		addRemoveModifyPanel.add(comboBoxPolarity);
		
		/**
		 * JButton to Add/Remove/Modify a tweet
		 * 
		 * Add: Will add a tweet based off the input from all textFields/comboBoxes
		 * Remove: Removes a tweet
		 * 
		 */
		btnARM = new JButton("Add");
		buttonDesigner(btnARM);
		btnARM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Long id = Long.valueOf(textFieldId.getText().replace(" ", ""));
				String selected = (String) comboBoxARM.getSelectedItem();
				
				if(selected.equals("")) {
					
				}
				else if(selected.equals("Add")) {
					try {
						if(!tc.contains(tc.get(id))) {
						tc.add(new Tweet(String.valueOf(comboBoxPolarity.getSelectedItem()), id
								, textFieldUsername.getText(), textAreaBodyText.getText()));
						lblErrorLabel.setText("Tweet with ID " + id + " added.");
						lblErrorLabel.setVisible(true);
						}
						else {
							lblErrorLabel.setText("Tweet with ID " + id + " already exists.");
							lblErrorLabel.setVisible(true);
						}
					} catch (Exception e2) {
						lblErrorLabel.setText("Error, Invalid Input");
						lblErrorLabel.setVisible(true);
					}
				}
				else if(selected.equals("Remove")) {
					try {
						Long t = Long.valueOf(textFieldEnterID.getText().replace(" ", ""));
						if(tc.getIds().contains(t)) {
							lblErrorLabel.setText("Removed Tweet " + tc.get(t));
							lblErrorLabel.setVisible(true);
						tc.removeTweet(new Tweet(t));
						}
						else {
							lblErrorLabel.setText("Error, Invalid ID");
							lblErrorLabel.setVisible(true);
						}
					}catch(Exception error) {
						lblErrorLabel.setVisible(true);
						lblErrorLabel.setText("Error, Invalid ID Type String");
					}
				}
				else if(selected.equals("Modify")) {
					try {
						if(tc.contains(tweetModify)) {
							tweetModify.setPolarity(String.valueOf(comboBoxPolarity.getSelectedItem()));
							tweetModify.setUser(textFieldUsername.getText());
							tweetModify.setBodyText(textAreaBodyText.getText());
							lblErrorLabel.setText("Modification complete!");
							lblErrorLabel.setVisible(true);
							updateTextAreas();
						}
						else {
							lblErrorLabel.setText("Tweet with ID " + id + " doesn't exists.");
							lblErrorLabel.setVisible(true);
						}
					} catch (Exception e2) {
						lblErrorLabel.setText("Invalid input");
					}
					
				}
				ids = new HashSet<String>();
				for (long l : tc.getIds()) {
					ids.add(String.valueOf(l) + "\n");
				}
				updateTextAreas();
			}
		});
		btnARM.setBounds(334, 346, 89, 25);
		btnARM.setVisible(false);
		addRemoveModifyPanel.add(btnARM);
		
		/**
		 * Used below to get Tweet.
		 */
		textFieldEnterID = new JTextField();
		textFieldEnterID.setBounds(388, 135, 86, 20);
		textFieldEnterID.setVisible(false);
		addRemoveModifyPanel.add(textFieldEnterID);
		textFieldEnterID.setColumns(10);
		
		/**
		 * get Tweet Based off Input ID from textFieldEnterId
		 */
		btnGET = new JButton("GET");
		btnGET.setVisible(false);
		buttonDesigner(btnGET);
		btnGET.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String t = textFieldEnterID.getText().replace(" ", "");
					if(!t.equals("")) {
						
						tweetModify = tc.get(Long.valueOf(t));
						holderTweet = new Tweet(tweetModify.getPolarity(),tweetModify.getId(),tweetModify.getUser(),tweetModify.getBodyText());
						
						comboBoxPolarity.setSelectedItem(tweetModify.getPolarity());
						textFieldId.setText(String.valueOf(tweetModify.getId()));
						textFieldUsername.setText(tweetModify.getUser());
						textAreaBodyText.setText(tweetModify.getBodyText());
					}
				
			}
		});
		btnGET.setBounds(501, 134, 89, 25);
		addRemoveModifyPanel.add(btnGET);
		
		btnUndoModification = new JButton("Undo Last");
		buttonDesigner(btnUndoModification);
		btnUndoModification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tc.removeTweet(tweetModify);
				tc.add(holderTweet);
				btnGET.doClick();
				updateTextAreas();
				lblErrorLabel.setText("Undo complete.");
				lblErrorLabel.setVisible(true);
			}
		});
		btnUndoModification.setBounds(468, 347, 108, 23);
		btnUndoModification.setVisible(false);
		addRemoveModifyPanel.add(btnUndoModification);
		

		
		updateTextAreas();
	}
	
	/**
	 * Called to update the textAreas in the application each time a change is made to the tweetcollection. Makes it more dynamic feeling.
	 */
	private void updateTextAreas() {
		TweetCollection tc1 = new TweetCollection();
		TweetCollection tc2 = new TweetCollection();
		if(tc.getSize()>200) {
			for(int i = 0; i < 100; i++) {
				tc1.add(tc.getAtIndex(i));
			}
			for(int i = tc.getSize()-100; i < tc.getSize(); i++) {
				tc1.add(tc.getAtIndex(i));
			}
			tc2 = tc1;
		}
		else
			tc2 = tc;
		
		usernameTextArea.setText(tc2.getAllUsernames().toString().replace("[", "").replace(",", "").replace("]", ""));
		idsTextArea.setText(tc2.getIds().toString().replace("[", " ").replace("]", "").replace(",", "\n"));
		textAreaTweets.setText(" | " + tc2.toString().replace("[", "").replace("]", "").replace(",", " |"));
		idsTextArea_1.setText(tc2.getIds().toString().replace("[", " ").replace("]", "").replace(",", "\n"));
		usernameTextArea_1.setText(tc2.getAllUsernames().toString().replace("[", "").replace(",", "").replace("]", ""));
	}

	/**
	 * probably didn't NEED this, but I like it better down here to clean up the code up top.
	 * I need a better way to do this....
	 * 
	 * Sets visibility for multiple components when different user options are selected. Called from listeners above.
	 */
	private void ARMSetup() {
		String selected = (String) comboBoxARM.getSelectedItem();
		if(selected.equals("")) {
			lblPolarity.setVisible(false);
			lblId.setVisible(false);
			lblUsername.setVisible(false);
			lblBodyText.setVisible(false);
			comboBoxPolarity.setVisible(false);
			textFieldId.setVisible(false);
			textFieldUsername.setVisible(false);
			textAreaBodyText.setVisible(false);
			btnARM.setVisible(false);
			btnGET.setVisible(false);
			lblARM.setVisible(false);
			textFieldEnterID.setVisible(false);
		}
		else if(selected.equals("Add")) {
			lblPolarity.setVisible(true);
			lblId.setVisible(true);
			lblUsername.setVisible(true);
			lblBodyText.setVisible(true);
			comboBoxPolarity.setVisible(true);
			textFieldId.setVisible(true);
			textFieldUsername.setVisible(true);
			textAreaBodyText.setVisible(true);
			btnGET.setVisible(false);
			textFieldId.setEditable(true);
			btnARM.setText("Add");
			btnARM.setVisible(true);
			textFieldEnterID.setVisible(false);
			lblARM.setVisible(false);
			btnUndoModification.setVisible(false);
		}
		else if(selected.equals("Modify")) {
			lblPolarity.setVisible(true);
			lblId.setVisible(true);
			lblUsername.setVisible(true);
			lblBodyText.setVisible(true);
			comboBoxPolarity.setVisible(true);
			textFieldId.setVisible(true);
			textFieldUsername.setVisible(true);
			textAreaBodyText.setVisible(true);
			btnARM.setVisible(true);
			btnUndoModification.setVisible(true);
			btnARM.setText("Modify");
			btnGET.setVisible(true);
			textFieldEnterID.setVisible(true);
			lblARM.setVisible(true);
			lblARM.setText("Enter ID: ");
			textFieldId.setEditable(false);
		}
		else if(selected.equals("Remove")) {
			lblPolarity.setVisible(false);
			lblId.setVisible(false);
			lblUsername.setVisible(false);
			lblBodyText.setVisible(false);
			comboBoxPolarity.setVisible(false);
			textFieldId.setVisible(false);
			textFieldUsername.setVisible(false);
			textAreaBodyText.setVisible(false);
			btnARM.setVisible(true);
			btnARM.setText("Remove");
			lblARM.setVisible(true);
			lblARM.setText("Enter ID:");
			btnGET.setVisible(false);
			textFieldEnterID.setVisible(true);
			textFieldId.setEditable(false);
			btnUndoModification.setVisible(false);
		}
	}
	
	/**
	 * Write changes to file
	 */
	public void doWrite() {
		tc.writeFile(filename);
		System.out.println("Saved to " + filename);
	}
	
	/**
	 * Call for each button to make the buttons similar style without having to add tons of lines of code.
	 * @param b button
	 */
	private void buttonDesigner(JButton b) {
		b.setBackground(SystemColor.activeCaption);
		b.setOpaque(true);
		b.setBorder(new LineBorder(Color.BLACK));
	}
	
	/**
	 * switch panel to change panel and set it up. Reuses buttons to keep from having multiples of each button.
	 * 
	 * @param panel  Panel to be switched to
	 * 
	 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JFrame/Remove_JPanel_from_JFrame_JPanels_and_add_a_new_JPanel.htm
	 */
	private void switchPanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		panel.add(lblNewLabel);
		panel.add(lblHome);
		panel.add(btnHome);
		panel.add(btnToPredictTweets);
		panel.add(btnAddremovemodify);
		panel.add(lbl_ids);
		panel.add(lblUsernames);
		btnHome.setEnabled(true);
		btnToPredictTweets.setEnabled(true);
		btnAddremovemodify.setEnabled(true);
		
		if(panel == homePanel) {
			btnHome.setEnabled(false);
			lblUsernames.setVisible(true);
			lbl_ids.setVisible(true);
		}
		if(panel == predictTweetsPanel) {
			btnToPredictTweets.setEnabled(false);
			lblUsernames.setVisible(false);
			lbl_ids.setVisible(false);
		}
		if(panel == addRemoveModifyPanel) {
			btnAddremovemodify.setEnabled(false);
			lblUsernames.setVisible(true);
			lbl_ids.setVisible(true);
		}
		
		
		
		layeredPane.repaint();
		layeredPane.revalidate();
	}
}