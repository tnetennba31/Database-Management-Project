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
		

	public static int itemsBegin;
	
	public int selectedIndex;

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
	
		} else if (e.getSource() == middleButtons[1]) {
			selectedIndex = itemsVisible[1];

		} else if (e.getSource() == middleButtons[2]) {
			selectedIndex = itemsVisible[2];
			
		} else if (e.getSource() == middleButtons[3]) {
			selectedIndex = itemsVisible[3];
			
		}
	}
	
	
	public void moveSelectedToRoom(int selectedRoomID) {
		addSelectedToRoom(selectedRoomID);
		removeAddedFromAddBox();
		
	}
	
	
	private void addSelectedToRoom(int selectedRoomID) {
		
		if (selectedIndex >= itemsBegin) {
			DisplayThree.getInstance().itemSelector
				.addItemToRoom(thingsInColumn.get(selectedIndex));
			
		} else {			
			DisplayThree.getInstance().creatureSelector
				.addCreatureToRoom(thingsInColumn.get(selectedIndex));
		}
				
	}
	
	private void removeAddedFromAddBox() {
		
		thingsInColumn.removeElementAt(selectedIndex);
		updateButtons();

	}
	
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