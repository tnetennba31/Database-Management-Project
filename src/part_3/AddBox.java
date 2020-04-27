package part_3;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class AddBox extends JPanel {
	
	public AddBox(String type) {
		
		setBackground(Color.WHITE);
		this.setBorder(new LineBorder(Color.BLACK));				

		
		if (type == "generic") {
			addFieldsForGeneric();
			
		}
		
	}
	//JTextField f = new JTextField("You can select me");
////f.setEditable(false);
////f.setBorder(null);
//f.setForeground(UIManager.getColor("Label.foreground"));
//f.setFont(UIManager.getFont("Label.font"));

	private void addFieldsForGeneric() {
		setLayout(new GridLayout(4, 2, 10, 10));
		

		
		add(new JLabel("test", JLabel.CENTER));
		add(new JTextField(10));
		
		add(new JLabel("test2", JLabel.CENTER));
		add(new JTextField(6));	
		
		add(new JLabel("test3", JLabel.CENTER));
		add(new JTextField(10));
		
		add(new JLabel("test4", JLabel.CENTER));
		add(new JTextField(6));	
		
		
	}

}
