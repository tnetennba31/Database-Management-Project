package part_3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Display4_Center_Panel extends Display_4 {
  JPanel centerPanel, heading, listPanel, informationPanel;
  JScrollPane items_owned_list;
  JButton addItemsToCharacterButton;

  String[] i_o = {"Weapon", "Armor", "Generic Item", "Sword", "Container", "Beefy Sword"};
  JList items_center = new JList(i_o);
  ArrayList<JButton> items_owned_info_buttons = new ArrayList<>(i_o.length);
  ArrayList<JFrame> items_owned_info_windows = new ArrayList<>(i_o.length);

  public Display4_Center_Panel() {};

  public void createPanel()
  {
    // add the center panel to the UI
    centerPanel = new JPanel(new BorderLayout());//GridLayout(3, 1));
    add("Center", centerPanel);

    // format it
    centerPanel.setBackground(Color.white);
    centerPanel.setBorder(blackBorder);
  }

  public void createHeading()
  {
    // entire heading
    heading = new JPanel(new BorderLayout());
    heading.setBackground(light_pink);
    heading.setBorder(blackBorder);
    centerPanel.add(heading, BorderLayout.NORTH);

    // list title bar
    JPanel c_major = new JPanel(new GridLayout(3, 1));
    c_major.setBackground(light_pink);
    c_major.setBorder(blackBorder);
    heading.add(c_major, BorderLayout.CENTER);
    JPanel c_top_major = new JPanel();
    c_top_major.setBackground(light_pink);
    c_major.add(c_top_major);
    JLabel items_owned = new JLabel("Items Owned By Character");
    items_owned.setHorizontalAlignment(SwingConstants.CENTER);
    c_major.add(items_owned);

    // info title bar
    JPanel c_minor = new JPanel(new GridLayout(3, 1));
    c_minor.setBackground(dark_pink);
    c_minor.setBorder(blackBorder);
    heading.add(c_minor, BorderLayout.EAST);
    JPanel c_top_minor = new JPanel();
    c_top_minor.setBackground(dark_pink);
    c_minor.add(c_top_minor);
    JLabel info_middle = new JLabel("          Info          ");
    info_middle.setHorizontalAlignment(SwingConstants.CENTER);
    c_minor.add(info_middle);
  }

  public void createItemsOwnedList()
  {
    // list panel
    listPanel = new JPanel(new BorderLayout());
    listPanel.setBackground(Color.white);
    listPanel.setBorder(blackBorder);
    centerPanel.add(listPanel, BorderLayout.CENTER);

    // scroll pane list
    items_owned_list = new JScrollPane(items_center);
    items_center.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    listPanel.add(items_owned_list, BorderLayout.CENTER);
  }

  public void createItemInfo()
  {
    // entire panel
    JPanel infoPanel = new JPanel();
    infoPanel.setBackground(Color.white);
    BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
    infoPanel.setLayout(info_layout);
    
    // make an info button for each item in the list
    for (int i = 0; i < i_o.length; i++) {
      // fill the panel with buttons
      JButton infoButton = new JButton("   . . .   ");
      infoButton.addActionListener(this);
      infoButton.setBackground(Color.white);
      infoButton.setBorder(blackBorder);
      infoPanel.add(infoButton);
      items_owned_info_buttons.add(i, infoButton);  // add button to list to keep track

      // link each button to a new pop-up window
      JFrame window = new JFrame();
      window.pack();
      window.setDefaultCloseOperation(HIDE_ON_CLOSE);
      window.setLayout(new GridLayout());
      window.setLocationRelativeTo(items_owned_info_buttons.get(i));
      items_owned_info_windows.add(i, window);  // add window to list to keep track

      // add information to each window
      informationPanel = new JPanel(new GridLayout(4, 2));
      informationPanel.setBackground(light_pink);
      informationPanel.setBorder(blackBorder);
      for (int x = 0; x < 4; x++)
      {
        // attribute title in left block
        JLabel idk = new JLabel("   stuff"  + x);
        idk.setBorder(blackBorder);
        idk.setHorizontalAlignment(SwingConstants.WEST);
        informationPanel.add(idk);

        // attribute value in right block
        JLabel yeet = new JLabel("   stuff" + x + "hello");
        yeet.setBorder(blackBorder);
        yeet.setHorizontalAlignment(SwingConstants.EAST);
        informationPanel.add(yeet);
      }
      window.add(informationPanel);
    }

    listPanel.add(infoPanel, BorderLayout.EAST);
  }

  public void createButton()
  {
    addItemsToCharacterButton = new JButton("Add Items to Character");
    addItemsToCharacterButton.setBackground(dark_pink);
    addItemsToCharacterButton.setBorder(blackBorder);
    addItemsToCharacterButton.addActionListener(this);
    centerPanel.add(addItemsToCharacterButton, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == addItemsToCharacterButton)
    {
      int[] stuff = items_center.getSelectedIndices();
      switch_item_to_worn((String) items_center.getSelectedValue());
      addItemsToCharacterButton.setText("Items Added");
      try {
        wait(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      addItemsToCharacterButton.setText("Add Items to Character");
      addItemsToCharacterButton.setBackground(light_pink);
    } else {
      for (int i = 0; i < i_o.length; i++) {
        if (event.getSource() == items_owned_info_buttons.get(i))
        {
          items_owned_info_buttons.get(i).setBackground(dark_pink);
          items_owned_info_windows.get(i).setVisible(true);
        }
      }
    }
  }
}