package part_3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;


public class DisplayThree extends JFrame implements ActionListener {
	
	private static DisplayThree display = null;
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int 
	WINDOW_WIDTH = screenSize.width / 2,			                
	//WINDOW_HEIGHT = screenSize.height / 2,	
	WINDOW_HEIGHT = (int) (WINDOW_WIDTH / 1.8),	
	OFFSET = WINDOW_WIDTH / 20,
	SELECTOR_WIDTH = OFFSET * 3,			               
	SELECTOR_HEIGHT = OFFSET * 5,	
	SELECTOR_Y = OFFSET,
	ROOM_SELECTOR_X = SELECTOR_Y,			                
	C_SELECTOR_X = OFFSET * 5,			                	                
	I_SELECTOR_X = OFFSET * 9,			                
	ADDING_BOX_WIDTH = OFFSET * 6,			                
	ADDING_BOX_HEIGHT = OFFSET * 7,			                
	ADDING_BOX_X = OFFSET * 13,			                
	ADDING_BOX_Y = OFFSET,		
	INSTRUCTIONS_WIDTH = OFFSET * 11,
	INSTRUCTIONS_HEIGHT = OFFSET * 2,
	INSTRUCTIONS_X = OFFSET,
	INSTRUCTIONS_Y = OFFSET * 7,
	LABEL_WIDTH = OFFSET * 3,			               
	LABEL_HEIGHT = OFFSET,			                
	LABEL_Y = OFFSET,			                
	R_LABEL_X = ROOM_SELECTOR_X,			                
	C_LABEL_X = C_SELECTOR_X,	
	I_LABEL_X = I_SELECTOR_X,
	ADD_LABEL_X = ADDING_BOX_X,
	DELETE_BUTTON_Y = (OFFSET * 6) + (OFFSET / 4),
	DELETE_BUTTON_WIDTH = OFFSET * 2,
	DELETE_BUTTON_HEIGHT = OFFSET / 2,
	C_DELETE_BUTTON_X = C_SELECTOR_X + (OFFSET / 2),
	I_DELETE_BUTTON_X = I_SELECTOR_X + (OFFSET / 2),
	ADD_BUTTON_DIAMETER = ADDING_BOX_X,
	ADD_BUTTON_X = ADDING_BOX_X,
	ADD_BUTTON_Y = OFFSET * 7;		                			                
			                			              			              
	JTextPane usageInstructions;
//	JButton textButton, imageButton;
//	JLabel textLabel, imageLabel;
	
	JButton deleteCreatureButton;
	JButton deleteItemButton;
	
	Selector roomSelector;
	CreatureSelector creatureSelector;
	ItemSelector itemSelector;
	
	public int selectedRoomID;
	Vector<String> rooms = new Vector<String>();
	Vector<String> creaturesInRoom = new Vector<String>();
	Vector<String> itemsInRoom = new Vector<String>();
	
	Vector<String> testV1 = new Vector<String>();
	Vector<String> testV2 = new Vector<String>();



	public static DisplayThree getInstance() { 
        if (display == null) 
            display = new DisplayThree(); 
  
        return display; 
    } 
	
	private DisplayThree() {//FORMAT EVERYTHING CORRECTLY
		
		setLayout(null); 
		
		getContentPane().setBackground(new Color(230, 230, 230));;
		
		usageInstructions = new JTextPane();
		usageInstructions.setText(getInstructionString());
		usageInstructions.setBounds(INSTRUCTIONS_X, INSTRUCTIONS_Y, INSTRUCTIONS_WIDTH, INSTRUCTIONS_HEIGHT);
		usageInstructions.setBorder(new LineBorder(Color.BLACK));
		add(usageInstructions);
		
		
		rooms.add("one");
		rooms.add("two");
		rooms.add("three");
		rooms.add("four");
		rooms.add("five");
		rooms.add("six");
		
		
		testV1.add("creature 1");
		testV1.add("creature 2");
		testV1.add("creature 3");
		testV1.add("creature 4");
		testV1.add("creature 5");
		testV1.add("creature 6");
		
		
		testV2.add("item 1");
		testV2.add("item 2");
		testV2.add("item 3");
		testV2.add("item 4");
		testV2.add("item 5");
		testV2.add("item 6");














		//put rooms in vector
		
		
		roomSelector = new Selector(this, rooms);
		roomSelector.setBounds(ROOM_SELECTOR_X, SELECTOR_Y, SELECTOR_WIDTH, SELECTOR_HEIGHT);
		add(roomSelector);

		
		creatureSelector = new CreatureSelector(this, testV1);
		creatureSelector.setBounds(C_SELECTOR_X, SELECTOR_Y, SELECTOR_WIDTH, SELECTOR_HEIGHT);
		add(creatureSelector);
		
		
		itemSelector = new ItemSelector(this, rooms);
		itemSelector.setBounds(I_SELECTOR_X, SELECTOR_Y, SELECTOR_WIDTH, SELECTOR_HEIGHT);
		add(itemSelector);
		
		
		deleteCreatureButton = new JButton("Del"); //TODO: make it an image instead
		deleteCreatureButton.setBounds(C_DELETE_BUTTON_X, DELETE_BUTTON_Y, DELETE_BUTTON_WIDTH, DELETE_BUTTON_HEIGHT);
		deleteCreatureButton.addActionListener(this);
		add(deleteCreatureButton);
		

		deleteItemButton = new JButton("Del"); //TODO: make it an image instead
		deleteItemButton.setBounds(I_DELETE_BUTTON_X, DELETE_BUTTON_Y, DELETE_BUTTON_WIDTH, DELETE_BUTTON_HEIGHT);
		deleteItemButton.addActionListener(this);
		add(deleteItemButton);
		
		
		setSize((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
		//pack();
		setResizable(false);
		setVisible(true);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == deleteCreatureButton) {

			creatureSelector.deleteSelectedAndRefresh();
									
		} else if (event.getSource() == deleteItemButton) {

			itemSelector.deleteSelectedAndRefresh();
									
		}
		//if delete button is pressed, 
//		if (event.getSource()) {
//			textButton.setText("Pushed");    
//			
//		} else if (event.getSource()) {
//			textButton.setText("Pushed");    
//			
//		}
	}
	
	public void changeSelectedRoom(int selectedRoom) {
		this.selectedRoomID = selectedRoom;
		
		//creaturesInRoom = DisplayThreeSQLHandler.getVectorOfCreaturesInRoom(selectedRoomID);
		creaturesInRoom = testV2; //also for item
		creatureSelector.changeContentsToNewRoom(creaturesInRoom);
		
	}
	
	private String getInstructionString() {
		return "[instructions]";
	}



}

//static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//public static final double WINDOW_HEIGHT = screenSize.getHeight() / 10;
//public static final double WINDOW_WIDTH = screenSize.getWidth() / 8;



/*
//JTextField f = new JTextField("You can select me");
////f.setEditable(false);
////f.setBorder(null);
//f.setForeground(UIManager.getColor("Label.foreground"));
//f.setFont(UIManager.getFont("Label.font"));
//JPanel j = new JPanel();
//
//j.setBounds(300, 50, 100, 200);
//
//j.setBackground(new Color(180,200,200));
//
//imageLabel = new JLabel("North");
//j.add(imageLabel);
//
//j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
//
//add(j);


JPanel p = new Selector();

add(p);



//
//
//textLabel = new JLabel("North");
//add("North",textLabel);
//
//imageButton = new JButton(createImage());
//add("West",imageButton);
//imageLabel = new JLabel(createImage());
//add("South",imageLabel);
//textButton = new JButton("A Button");
//
//textButton.addActionListener(this);
//
//add("East",textButton);
//
//JPanel centerPanel = new JPanel(new GridLayout(2,3));
//
//JLabel[][] labelArray = new JLabel[2][3];
//for (int r=0;r<2;r++) {
//	for (int c=0;c<3;c++) {        
//		labelArray[r][c] = new JLabel(" ("+r+":"+c+") ");        
//		centerPanel.add(labelArray[r][c]);    
//		
//	}
//	
//}
//add("Center",centerPanel);


setSize((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);

//pack();


setVisible(true);
}









	public ImageIcon createImage() {
		
		BufferedImage exampleImage = new BufferedImage(50,50,BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics drawer = exampleImage.getGraphics();   
	
		
		drawer.setColor(new Color(200,200,200));   
		drawer.fillRect(0, 0, 50, 50);        
		drawer.setColor(new Color(0,255,0));        
		drawer.fillOval(20, 20, 10, 10);
		
		return new ImageIcon(exampleImage);
	}











//
//		
//		textLabel = new JLabel("North");
//		add("North",textLabel);
//		
//		imageButton = new JButton(createImage());
//		add("West",imageButton);
//		imageLabel = new JLabel(createImage());
//		add("South",imageLabel);
//		textButton = new JButton("A Button");
//		
//		textButton.addActionListener(this);
//		
//		add("East",textButton);
//		
//		JPanel centerPanel = new JPanel(new GridLayout(2,3));
//		
//		JLabel[][] labelArray = new JLabel[2][3];
//		for (int r=0;r<2;r++) {
//			for (int c=0;c<3;c++) {        
//				labelArray[r][c] = new JLabel(" ("+r+":"+c+") ");        
//				centerPanel.add(labelArray[r][c]);    
//				
//			}
//			
//		}
//		add("Center",centerPanel);
*/