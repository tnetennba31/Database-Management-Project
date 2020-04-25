package part_3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display4_Right_Panel extends Display_4 {
  JPanel rightPanel, heading, listPanel, informationPanel;
  JScrollPane items_worn_list;
  JButton removeItemsFromCharacterButton;

  String[] i_w = {"Armor", "Weapon", "Another Weapon", "Better Armor"};
  JList items_right = new JList(i_w);
  ArrayList<JButton> items_worn_info_buttons = new ArrayList<>(i_w.length);
  ArrayList<JFrame> items_worn_info_windows = new ArrayList<>(i_w.length);

  public Display4_Right_Panel() {}

  public void createPanel()
  {
    // add the right panel to the UI
    rightPanel = new JPanel(new BorderLayout());//(new GridLayout(3, 1));
    add("Right", rightPanel);

    // format it
    rightPanel.setBackground(Color.white);
    rightPanel.setBorder(blackBorder);
  }

  public void createHeading()
  {
    // entire heading
    heading = new JPanel(new BorderLayout());
    heading.setBackground(light_pink);
    heading.setBorder(blackBorder);
    rightPanel.add(heading, BorderLayout.NORTH);

    // list title bar
    JPanel r_major = new JPanel(new GridLayout(3, 1));
    r_major.setBackground(light_pink);
    r_major.setBorder(blackBorder);
    heading.add(r_major, BorderLayout.CENTER);
    JPanel r_top_major = new JPanel();
    r_top_major.setBackground(light_pink);
    r_major.add(r_top_major);
    JLabel items_worn = new JLabel("Items On Character");
    items_worn.setHorizontalAlignment(SwingConstants.CENTER);
    r_major.add(items_worn);

    // info title bar
    JPanel r_minor = new JPanel(new GridLayout(3, 1));
    r_minor.setBackground(dark_pink);
    r_minor.setBorder(blackBorder);
    heading.add(r_minor, BorderLayout.EAST);
    JPanel r_top_minor = new JPanel();
    r_top_minor.setBackground(dark_pink);
    r_minor.add(r_top_minor);
    JLabel info_right = new JLabel("          Info          ");
    info_right.setHorizontalAlignment(SwingConstants.CENTER);
    r_minor.add(info_right);
  }

  public void createItemsWornList()
  {
    // list panel
    listPanel = new JPanel(new BorderLayout());
    listPanel.setBackground(Color.white);
    listPanel.setBorder(blackBorder);
    rightPanel.add(listPanel, BorderLayout.CENTER);

    // scroll pane list
    items_worn_list = new JScrollPane(items_right);
    items_right.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    listPanel.add(items_worn_list, BorderLayout.CENTER);
  }

  public void createItemInfo()
  {
    // entire panel
    JPanel infoPanel = new JPanel();
    infoPanel.setBackground(Color.white);
    BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
    infoPanel.setLayout(info_layout);

    // make info button for each item in the list
    for (int i = 0; i < i_w.length; i++) {
      // fill the panel with buttons
      JButton infoButton = new JButton("   . . .   ");
      items_worn_info_buttons.add(i, infoButton);
      infoButton.addActionListener(this);
      infoButton.setBackground(Color.white);
      infoButton.setBorder(blackBorder);
      infoPanel.add(infoButton);  // add button to list to keep track

      // link each button to a new pop-up window
      JFrame window = new JFrame();
      window.pack();
      window.setDefaultCloseOperation(HIDE_ON_CLOSE);
      window.setLayout(new GridLayout());
      window.setLocationRelativeTo(items_worn_info_buttons.get(i));
      items_worn_info_windows.add(i, window);  // add window to list to keep track

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
}