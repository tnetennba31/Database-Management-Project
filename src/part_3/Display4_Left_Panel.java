package part_3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Display4_Left_Panel extends Display_4 {
  JPanel leftPanel, heading;
  JTextField login;
  JScrollPane characterList;
  JButton selectCharacterButton;

  String[] c = {"Roy", "Moss", "Richmond", "Jen", "Douglas"};
  JList characters = new JList(c);

  public Display4_Left_Panel() {}

  public void createPanel()
  {
    // add the left panel to the UI
    leftPanel = new JPanel(new BorderLayout());//GridLayout(3, 1));
    add("Left", leftPanel);

    // format it
    leftPanel.setBackground(Color.white);
    leftPanel.setBorder(blackBorder);
  }

  public void createHeading()
  {
    // entire heading
    heading = new JPanel(new GridLayout(3,1));
    heading.setBackground(light_pink);
    heading.setBorder(blackBorder);
    leftPanel.add(heading, BorderLayout.NORTH);

    // instruction bar
    JPanel idk = new JPanel(new BorderLayout());
    idk.setBackground(dark_pink);
    JLabel instruction = new JLabel("Enter Player Login:");
    idk.add(instruction);
    heading.add(idk);

    // login bar
    JPanel background = new JPanel(new BorderLayout());
    JPanel west = new JPanel();
    west.setBackground(dark_pink);
    background.add(west, BorderLayout.WEST);
    JPanel east = new JPanel();
    east.setBackground(dark_pink);
    background.add(east, BorderLayout.EAST);
    JPanel north = new JPanel();
    north.setBackground(dark_pink);
    background.add(north, BorderLayout.NORTH);
    JPanel south = new JPanel();
    south.setBackground(dark_pink);
    background.add(south, BorderLayout.SOUTH);
    login = new JTextField();
    background.add(login, BorderLayout.CENTER);
    heading.add(background);

    // list title bar
    JLabel characters = new JLabel("Characters");
    characters.setBackground(light_pink);
    characters.setBorder(blackBorder);
    characters.setHorizontalAlignment(SwingConstants.CENTER);
    heading.add(characters);
  }

  public void createCharacterList()
  {
    characterList = new JScrollPane(characters);
    characterList.setBackground(Color.white);
    characterList.setBorder(blackBorder);
    leftPanel.add(characterList, BorderLayout.CENTER);
  }

  public void createButton()
  {
    selectCharacterButton = new JButton("Select Character");
    selectCharacterButton.setBackground(dark_pink);
    selectCharacterButton.setBorder(blackBorder);
    selectCharacterButton.addActionListener(this);
    leftPanel.add(selectCharacterButton, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == selectCharacterButton)
    {
      String character = (String) characters.getSelectedValue();
      selectCharacterButton.setText("Character Selected: " + character);
      selectCharacterButton.setBackground(light_pink);
    }
  }
}