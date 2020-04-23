package part_3;

import java.awt.ActiveEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

class Selector extends JPanel implements ActionListener {
	
	public static final int WIDTH = DisplayThree.SELECTOR_WIDTH;
	public static final int HEIGHT = DisplayThree.SELECTOR_HEIGHT;
	
	private JButton[] middleButtons = new JButton[4];
	private int[] itemsVisible = {0, 1, 2, 3};
	
	JButton upArrowButton, downArrowButton;
	
	private Vector<String> thingsInColumn;
	
	public Selector(JFrame frame, Vector<String> thingsInColumn) {
		
		this.thingsInColumn = thingsInColumn;
		//this.setBounds(50, 50, 100, 200);
		this.setBackground(Color.LIGHT_GRAY);

		
		this.addUpArrow();
			
		
		this.assignColumnItemsToButton();
		
		for (JButton j : middleButtons) {
			this.add(j);
		}
				
		
		this.addDownArrow();
		    
				
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(null);

		this.setBorder(new LineBorder(Color.BLACK));				
	}
	
	

	private void addUpArrow() {
		
		Image upArrow = new ImageIcon(this.getClass().getResource("/UpArrow.PNG")).getImage()
				.getScaledInstance((int)(WIDTH), HEIGHT / 7, Image.SCALE_SMOOTH);
		upArrowButton = new JButton("");
		upArrowButton.setSize(WIDTH, HEIGHT / 8);
		upArrowButton.setBorder(new LineBorder(Color.BLACK));
		upArrowButton.setIcon(new ImageIcon(upArrow));
		upArrowButton.setLocation(0, 0);
		
		upArrowButton.addActionListener(this);
		
		this.add(upArrowButton);
		
		
	}

	private void addDownArrow() {
		
		Image downArrow = new ImageIcon(this.getClass().getResource("/downArrow.PNG")).getImage()
				.getScaledInstance((int)(WIDTH), HEIGHT / 7, Image.SCALE_SMOOTH);
		
		downArrowButton = new JButton("");
		downArrowButton.setSize(WIDTH, HEIGHT / 8);
		downArrowButton.setBorder(new LineBorder(Color.BLACK));
		downArrowButton.setIcon(new ImageIcon(downArrow));
		
		downArrowButton.setLocation(0, HEIGHT - downArrowButton.getHeight());
		
		downArrowButton.addActionListener(this);

		this.add(downArrowButton);
		
	}

	private void assignColumnItemsToButton() {
		
//		for (JButton j : middleButtons) {
//			j.
//		}
		
		for (int i = 0; i < middleButtons.length; i++) {
			middleButtons[i] = new JButton(thingsInColumn.get(i));
			middleButtons[i].addActionListener(this);
			middleButtons[i].setContentAreaFilled(false);
			middleButtons[i].setBorderPainted(false);
			middleButtons[i].setOpaque(false);
			middleButtons[i].setSize(WIDTH, HEIGHT / 8);
			
			middleButtons[i].setLocation(0, upArrowButton.getHeight() + 
					(i * ((HEIGHT - (upArrowButton.getHeight() * 2)) / 4) + 2));
//			System.out.println(upArrowButton.getHeight() + 
//					(i * ((HEIGHT - (upArrowButton.getHeight() * 2) / 4))));
			
//			System.out.println(i + " " + middleButtons[i].getLocation().getX() + ", " + middleButtons[i].getLocation().getX());
			
			
			//this.add(middleButtons[i]);        MAY ONLY NEED TO DO THIS ONCE

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == upArrowButton) {
			
			scrollUp();
						

		} else if (e.getSource() == downArrowButton) {

			scrollDown();
		}
	}



	private void scrollUp() {
		
		if (itemsVisible[0] != 0) {
			
			for (int i = 0; i < itemsVisible.length; i++) {
				itemsVisible[i]--;
			}
			
			for (int i = 0; i <=3; i++) {
				middleButtons[i].setText(thingsInColumn.get(itemsVisible[i]));
			}
		}
		
	}
	

	private void scrollDown() {
		
		if (itemsVisible[3] != thingsInColumn.size() - 1) {
			
			for (int i = 0; i < itemsVisible.length; i++) {
				itemsVisible[i]++;
			}
			
			
			for (int i = 0; i <=3; i++) {
				middleButtons[i].setText(thingsInColumn.get(itemsVisible[i]));
			}
		}
	}
	

}
