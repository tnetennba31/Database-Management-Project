package part_3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class AddBox extends Selector {
		

	String itemOrCreature = "creature";
	public static int itemsBegin;
	public int selectedIndex = 0;
	private Boolean[] buttonLastPressed = {false, false, false, false};

	public AddBox(JFrame frame, Vector<String> thingsInColumn) {
		super(frame, thingsInColumn, DisplayThree.ADDING_BOX_WIDTH, 
				DisplayThree.ADDING_BOX_HEIGHT, Color.WHITE);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == upArrowButton) {
			
			scrollUp();		

		} else if (e.getSource() == downArrowButton) {

			scrollDown();
			
		} else if (e.getSource() == middleButtons[0]) {
			selectedIndex = itemsVisible[0];
			for (Boolean b : buttonLastPressed) {b = false;}
			buttonLastPressed[0] = true;
	
		} else if (e.getSource() == middleButtons[1]) {
			selectedIndex = itemsVisible[1];
			for (Boolean b : buttonLastPressed) {b = false;}
			buttonLastPressed[1] = true;

		} else if (e.getSource() == middleButtons[2]) {
			selectedIndex = itemsVisible[2];
			for (Boolean b : buttonLastPressed) {b = false;}
			buttonLastPressed[2] = true;
			
		} else if (e.getSource() == middleButtons[3]) {
			selectedIndex = itemsVisible[3];
			for (Boolean b : buttonLastPressed) {b = false;}
			buttonLastPressed[3] = true;
			
		}
	}
	
	
	public void moveSelectedToRoom(int selectedRoomID) {
//		System.out.println(selectedIndex);
		addSelectedToRoom(selectedRoomID);
		removeElement(selectedIndex);
//		removeAddedFromAddBox();
//		updateVisible();
		
	}
	
	public void changeContents(String iOrC, boolean checkIfUnchanged) {

//		System.out.println(iOrC);
		if (checkIfUnchanged && (itemOrCreature == iOrC)) {
			return;
		}
		
		selectedIndex = 0;
		itemsVisible[0] = 0;
		itemsVisible[1] = 1;
		itemsVisible[2] = 2;
		itemsVisible[3] = 3;
		this.itemOrCreature = iOrC;
		
		if (iOrC == null) {
			thingsInColumn = DisplayThreeSQLHandler.getAllThatCanBeAddedToRoom(this.itemOrCreature);
		} else {
			thingsInColumn = DisplayThreeSQLHandler.getAllThatCanBeAddedToRoom(iOrC);
		}
		

//		if (iOrC == "creature") {
//			thingsInColumn = DisplayThreeSQLHandler.getAllThatCanBeAddedToRoom("CREATURE");
//			
//		} else if (iOrC == "item") {
//			thingsInColumn = DisplayThreeSQLHandler.getAllThatCanBeAddedToRoom("ITEM");
//
//		} else {System.out.println("this is wrong");}

		updateButtons();		
	}
	

	private void addSelectedToRoom(int selectedRoomID) {
//		for (int i : itemsVisible) {System.out.println(i + " ");}

		if (itemOrCreature == "CREATURE") {
			DisplayThree.getInstance().creatureSelector
				.addCreatureToRoom(thingsInColumn.get(selectedIndex));	
			
		} else if (itemOrCreature == "ITEM") {
			DisplayThree.getInstance().itemSelector
				.addItemToRoom(thingsInColumn.get(selectedIndex));	
			
		} else {System.out.println("this is wrong");}
			
	}
	
//	private void removeAddedFromAddBox() {
//		
//		thingsInColumn.removeElementAt(selectedIndex);
//		updateButtons();
//
//	}
//	
//	private void updateVisible() {
//		if (buttonLastPressed[0] == true) {
//			itemsVisible[0]--;
//		} else if (buttonLastPressed[1] == true) {
//			itemsVisible[1]--;
//		} else if (buttonLastPressed[2] == true) {
//			itemsVisible[2]--;
//		} else if (buttonLastPressed[3] == true) {
//			itemsVisible[3]--;
//		}
//		
//	}
	
//	public void updateButtons() {
//		for (int i = 0; i < middleButtons.length; i++) {
//			middleButtons[i].setText(thingsInColumn.get(itemsVisible[i]));
//
//		}
//		
//	}

}








//setBackground(Color.WHITE);

//this.setBorder(new LineBorder(Color.BLACK));	
//
//setLayout(new BorderLayout());
//
//this.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
//this.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
//this.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
//this.add(Box.createHorizontalStrut(10), BorderLayout.WEST);


//JTextField f = new JTextField("You can select me");
////f.setEditable(false);
////f.setBorder(null);
//f.setForeground(UIManager.getColor("Label.foreground"));
//f.setFont(UIManager.getFont("Label.font"));

//private void addFieldsForGeneric() {
//innerPanel.setLayout(new GridLayout(4, 2, 10, 10));
//
////GI_ID INT NOT NULL, I_ID INT NOT NULL, PRIMARY KEY (GI_ID), FOREIGN KEY (I_ID) REFERENCES ITEM(ID))
//JLabel id = new JLabel("GI_ID");
//JLabel id = new JLabel("GI_ID");
//
//
//innerPanel.add(new JLabel("test", JLabel.CENTER));
//innerPanel.add(new JTextField(10)); //MAKE CORRECT NUMBERS FOR DATABASE
//
//innerPanel.add(new JLabel("test2", JLabel.CENTER));
//innerPanel.add(new JTextField(6));	
//
//innerPanel.add(new JLabel("test3", JLabel.CENTER));
//innerPanel.add(new JTextField(10));
//
//innerPanel.add(new JLabel("test4", JLabel.CENTER));
//innerPanel.add(new JTextField(6));	
//
//
//}