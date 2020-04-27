package part_3;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ItemSelector extends Selector {
	
	//int genericItemsBegin = 0;
	int armorsBegin = 0;
	int weaponsBegin = 0;
	int containersBegin = 0;



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
			if (indexOfSelectedItem < weaponsBegin) {System.out.println("this is armor");} else {System.out.println("this is weapon");}
		} else if (e.getSource() == middleButtons[1]) {
			indexOfSelectedItem = itemsVisible[1];
			if (indexOfSelectedItem < weaponsBegin) {System.out.println("this is armor");} else {System.out.println("this is weapon");}
		} else if (e.getSource() == middleButtons[2]) {
			indexOfSelectedItem = itemsVisible[2];
			if (indexOfSelectedItem < weaponsBegin) {System.out.println("this is armor");} else {System.out.println("this is weapon");}
		} else if (e.getSource() == middleButtons[3]) {
			indexOfSelectedItem = itemsVisible[3];
			if (indexOfSelectedItem < weaponsBegin) {System.out.println("this is armor");} else {System.out.println("this is weapon");}
		}
	}
	
	public void deleteSelectedAndRefresh() {
		
		//DisplayThreeSQLHandler.delete("Creatures", super.getFocusedRoom(), this.getFocusedCreatureID());
				
		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}System.out.println();
		thingsInColumn.removeElementAt(indexOfSelectedItem);
		for (String s : this.thingsInColumn) {System.out.print(s + "  ");}

		this.updateButtons();
	}

	public void updateButtons() {
		for (int i = 0; i < middleButtons.length; i++) {
			middleButtons[i].setText(thingsInColumn.get(itemsVisible[i]));

		}
		
	}

}
