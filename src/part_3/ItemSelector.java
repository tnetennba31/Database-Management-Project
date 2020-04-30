package part_3;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ItemSelector extends Selector {
	
//	int armorsBegin = 0;
//	int weaponsBegin = 0;
//	int containersBegin = 0;



	protected int indexOfSelectedItem = 0;

	public ItemSelector(JFrame frame, Vector<String> thingsInColumn) {
		super(frame, thingsInColumn);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == upArrowButton) {
			
			scrollUp();
						
		} else if (e.getSource() == downArrowButton) {
			
			scrollDown();
			
		} else if (e.getSource() == middleButtons[0]) {
			indexOfSelectedItem = itemsVisible[0];
		} else if (e.getSource() == middleButtons[1]) {
			indexOfSelectedItem = itemsVisible[1];
		} else if (e.getSource() == middleButtons[2]) {
			indexOfSelectedItem = itemsVisible[2];
		} else if (e.getSource() == middleButtons[3]) {
			indexOfSelectedItem = itemsVisible[3];
		}
	}
	
//	private void signalCorrectAddBoxToDisplay() {
//		
//		if (indexOfSelectedItem < armorsBegin) {
//			DisplayThree.getInstance().setAddBox("generic");
//		} else if (indexOfSelectedItem < weaponsBegin) {
//			DisplayThree.getInstance().setAddBox("armor");
//		} else if (indexOfSelectedItem < containersBegin) {
//			DisplayThree.getInstance().setAddBox("weapon");
//		} else if (indexOfSelectedItem >= containersBegin) {
//			DisplayThree.getInstance().setAddBox("container");
//		}
//	}

	public void deleteSelectedAndRefresh() {
		
		//TODO: DisplayThreeSQLHandler.delete("Creatures", super.getFocusedRoom(), this.getFocusedCreatureID());
				
		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}System.out.println();
		thingsInColumn.removeElementAt(indexOfSelectedItem);
		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}

		this.updateButtons();
	}

//	public void updateButtons() {
//		for (int i = 0; i < middleButtons.length; i++) {
//			middleButtons[i].setText(thingsInColumn.get(itemsVisible[i]));
//
//		}
//		
//	}

//	public void setWhereNewItemTypesBegin(int a, int w, int c) {
//		
//		armorsBegin = a;
//		weaponsBegin = w;
//		containersBegin = c;
//	}
	
	public void changeContentsToNewRoom(Vector<String> itemsInRoom) {

		indexOfSelectedItem = 0;
		
		itemsVisible[0] = 0;
		itemsVisible[1] = 1;
		itemsVisible[2] = 2;
		itemsVisible[3] = 3;
		
		thingsInColumn = itemsInRoom;
		
		updateButtons();
		
	}

	public void addItemToRoom(String item) {
		
		thingsInColumn.add(item);
		updateButtons();

		//DisplayThreeSQLHandler.changeRoomOfCreature(item);
		
	}

}

