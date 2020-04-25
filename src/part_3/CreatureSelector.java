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
		
		//DisplayThreeSQLHandler.delete("Creatures", super.getFocusedRoom(), this.getFocusedCreatureID());
		
		
//		System.out.println(thingsInColumn.get(indexOfSelectedCreature));
		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}System.out.println();
		thingsInColumn.removeElementAt(indexOfSelectedCreature);
		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}

//		System.out.println(thingsInColumn.get(indexOfSelectedCreature));
//		System.out.println();

		this.updateButtons();
	}
	
	public void updateButtons() {
		for (int i = 0; i < middleButtons.length; i++) {
			middleButtons[i].setText(thingsInColumn.get(itemsVisible[i]));


		}
	}

	public void changeContentsToNewRoom(Vector<String> creaturesInRoom) {
		indexOfSelectedCreature = 0;
		
		itemsVisible[0] = 0;
		itemsVisible[1] = 1;
		itemsVisible[2] = 2;
		itemsVisible[3] = 3;
		
		thingsInColumn = creaturesInRoom;
		
		updateButtons();
		
	}

}
