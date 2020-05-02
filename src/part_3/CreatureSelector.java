package part_3;

import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CreatureSelector extends Selector {

	protected int indexOfSelectedCreature = 0;
	
	public CreatureSelector(JFrame frame, Vector<String> thingsInColumn) {
		super(frame, thingsInColumn);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DisplayThree.getInstance().addBox.changeContents("CREATURE", true);
		
		if (e.getSource() == upArrowButton) {
			
			scrollUp();
						
		} else if (e.getSource() == downArrowButton) {
			
			scrollDown();
			
		} else if (e.getSource() == middleButtons[0]) {
			indexOfSelectedCreature = itemsVisible[0];
		} else if (e.getSource() == middleButtons[1]) {
			indexOfSelectedCreature = itemsVisible[1];
		} else if (e.getSource() == middleButtons[2]) {
			indexOfSelectedCreature = itemsVisible[2];
		} else if (e.getSource() == middleButtons[3]) {
			indexOfSelectedCreature = itemsVisible[3];
		}
		
	}

	public void deleteSelectedAndRefresh() {
		
		//DisplayThreeSQLHandler.changeRoomOfCreature(creatureID);("Creatures", super.getFocusedRoom(), this.getFocusedCreatureID());
		DisplayThreeSQLHandler.changeRoom("CREATURE", indexOfSelectedCreature, -1);
//		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}System.out.println();

		//		thingsInColumn.removeElementAt(indexOfSelectedCreature);
		removeElement(indexOfSelectedCreature);

//		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}

		this.updateButtons();
	}
	
//	public void updateButtons() {
//		for (int i = 0; i < middleButtons.length; i++) {
//
//			if (i < thingsInColumn.size()) {
//				
//				middleButtons[i].setText(thingsInColumn.get(itemsVisible[i]));
//			} else {
//				
//				middleButtons[i].setText("");
//				middleButtons[i].setVisible(false);
//
//			}
//
//		}
//	}

	public void changeContentsToNewRoom(Vector<String> creaturesInRoom) {

		indexOfSelectedCreature = 0;
		
		itemsVisible[0] = 0;
		itemsVisible[1] = 1;
		itemsVisible[2] = 2;
		itemsVisible[3] = 3;
		
		thingsInColumn = creaturesInRoom;
		
		updateButtons();
		
	}

	public void addCreatureToRoom(String creature) {
		
		thingsInColumn.add(creature);
		updateButtons();

		//DisplayThreeSQLHandler.changeRoomOfCreature(creature);
		DisplayThreeSQLHandler.changeRoom("CREATURE", Integer.parseInt(creature)
				, DisplayThree.getInstance().selectedRoomID);

	}

}
