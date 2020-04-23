package part_3;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display_4 extends JFrame implements ActionListener
{
  JPanel leftPanel, centerPanel, rightPanel;  // the three central panels

  JPanel L_login, C_items, R_items; // the three panel headings
  JScrollPane L_list, C_list, R_list; // the lists for each panel
  JButton selectCharacter, addItemsToCharacter, removeItemsFromCharacter; // the three main buttons

  String[] c = {"Roy", "Moss", "Richmond", "Jen", "Douglas"};
  JList characters = new JList(c);

  String[] i_o = {"Weapon", "Armor", "Generic Item", "Sword", "Container", "Beefy Sword"};
  JList items_center = new JList(i_o);

  String[] i_w = {"Armor", "Weapon", "Another Weapon", "Better Armor"};
  JList items_right = new JList(i_w);

  Color dark_pink = new Color(213 , 166 , 189);
  Color light_pink = new Color(234 , 209 , 220);
  Border blackBorder = BorderFactory.createLineBorder(Color.black);  // the standard border for components

  public static void main(String[] args)
  {
    Display_4 gui = new Display_4();
  }
  public Display_4()
  {
    // set layout to grid for three panels
    setLayout(new GridLayout(1, 3));
    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    createPanels(); // create left, center, and right panels
    formatTitles(); // format the title portion of each panel
    formatLists();  // format the list portion of each panel
    formatButtons(); // format the button portion of each panel
    addPanelTitles(); // add the title content
    addPanelLists();  // add the list content
    addPanelButtons();  // add the button content

    /**
     * for pop up windows use jframe and have their action listener set them visible
     */
    pack();
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void createPanels()
  {
    leftPanel = new JPanel(new BorderLayout());//GridLayout(3, 1));
    centerPanel = new JPanel(new BorderLayout());//GridLayout(3, 1));
    rightPanel = new JPanel(new BorderLayout());//(new GridLayout(3, 1));

    add("Left", leftPanel);
    add("Center", centerPanel);
    add("Right", rightPanel);

    leftPanel.setBackground(Color.white);
    centerPanel.setBackground(Color.white);
    rightPanel.setBackground(Color.white);

    leftPanel.setBorder(blackBorder);
    centerPanel.setBorder(blackBorder);
    rightPanel.setBorder(blackBorder);
  }

  public void formatTitles()
  {
    L_login = new JPanel(new GridLayout(3,1));
    L_login.setBackground(light_pink);
    L_login.setBorder(blackBorder);
    leftPanel.add(L_login, BorderLayout.NORTH);

    C_items = new JPanel(new BorderLayout());
    C_items.setBackground(light_pink);
    C_items.setBorder(blackBorder);
    centerPanel.add(C_items, BorderLayout.NORTH);

    R_items = new JPanel(new BorderLayout());
    R_items.setBackground(light_pink);
    R_items.setBorder(blackBorder);
    rightPanel.add(R_items, BorderLayout.NORTH);
  }

  public void formatLists()
  {
    L_list = new JScrollPane(characters);
    L_list.setBackground(Color.white);
    L_list.setBorder(blackBorder);
    leftPanel.add(L_list, BorderLayout.CENTER);

    C_list = new JScrollPane(items_center);
    C_list.setBackground(Color.white);
    C_list.setBorder(blackBorder);
    centerPanel.add(C_list, BorderLayout.CENTER);

    R_list = new JScrollPane(items_right);
    R_list.setBackground(Color.white);
    R_list.setBorder(blackBorder);
    rightPanel.add(R_list, BorderLayout.CENTER);
  }

  public void formatButtons()
  {
    selectCharacter = new JButton("Select Character");
    selectCharacter.setBackground(dark_pink);
    selectCharacter.setBorder(blackBorder);
    leftPanel.add(selectCharacter, BorderLayout.SOUTH);

    addItemsToCharacter = new JButton("Add Items to Character");
    addItemsToCharacter.setBackground(dark_pink);
    addItemsToCharacter.setBorder(blackBorder);
    centerPanel.add(addItemsToCharacter, BorderLayout.SOUTH);

    removeItemsFromCharacter = new JButton("Remove Items From Character");
    removeItemsFromCharacter.setBackground(dark_pink);
    removeItemsFromCharacter.setBorder(blackBorder);
    rightPanel.add(removeItemsFromCharacter, BorderLayout.SOUTH);
  }

  public void addPanelTitles()
  {
    // left panel
    JPanel idk = new JPanel(new BorderLayout());
    idk.setBackground(dark_pink);
    L_login.add(idk);
    JLabel instruction = new JLabel("Enter Player Login:");
    idk.add(instruction);

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
    JTextField center = new JTextField();
    background.add(center, BorderLayout.CENTER);

    L_login.add(background);

    JLabel characters = new JLabel("Characters");
    characters.setBackground(light_pink);
    characters.setBorder(blackBorder);
    characters.setHorizontalAlignment(SwingConstants.CENTER);
    L_login.add(characters);

    // center panel
    JPanel c_major = new JPanel(new GridLayout(3, 1));
    JPanel c_minor = new JPanel(new GridLayout(3, 1));
    c_major.setBackground(light_pink);
    c_minor.setBackground(dark_pink);
    c_major.setBorder(blackBorder);
    c_minor.setBorder(blackBorder);
    C_items.add(c_major, BorderLayout.CENTER);
    C_items.add(c_minor, BorderLayout.EAST);

    JPanel c_top_major = new JPanel();
    c_top_major.setBackground(light_pink);
    c_major.add(c_top_major);
    JPanel c_top_minor = new JPanel();
    c_top_minor.setBackground(dark_pink);
    c_minor.add(c_top_minor);

    JLabel items_owned = new JLabel("Items Owned By Character");
    items_owned.setHorizontalAlignment(SwingConstants.CENTER);
    c_major.add(items_owned);

    JLabel info_middle = new JLabel("          Info          ");
    info_middle.setHorizontalAlignment(SwingConstants.CENTER);
    c_minor.add(info_middle);

    // right panel
    JPanel r_major = new JPanel(new GridLayout(3, 1));
    JPanel r_minor = new JPanel(new GridLayout(3, 1));
    r_major.setBackground(light_pink);
    r_minor.setBackground(dark_pink);
    r_major.setBorder(blackBorder);
    r_minor.setBorder(blackBorder);
    R_items.add(r_major, BorderLayout.CENTER);
    R_items.add(r_minor, BorderLayout.EAST);

    JPanel r_top_major = new JPanel();
    r_top_major.setBackground(light_pink);
    r_major.add(r_top_major);
    JPanel r_top_minor = new JPanel();
    r_top_minor.setBackground(dark_pink);
    r_minor.add(r_top_minor);

    JLabel items_worn = new JLabel("Items On Character");
    items_worn.setHorizontalAlignment(SwingConstants.CENTER);
    r_major.add(items_worn);

    JLabel info_right = new JLabel("          Info          ");
    info_right.setHorizontalAlignment(SwingConstants.CENTER);
    r_minor.add(info_right);
  }

  public void addPanelLists()
  {
    //left panel

  }

  public void addPanelButtons()
  {
    selectCharacter.addActionListener(this);
    addItemsToCharacter.addActionListener(this);
    removeItemsFromCharacter.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == selectCharacter)
    {
      selectCharacter.setText("Character Selected");
      selectCharacter.setBackground(light_pink);
    } else if (event.getSource() == addItemsToCharacter)
    {
      addItemsToCharacter.setText("Items Added");
      addItemsToCharacter.setBackground(light_pink);
    } else if (event.getSource() == removeItemsFromCharacter)
    {
      removeItemsFromCharacter.setText("Items Removed");
      removeItemsFromCharacter.setBackground(light_pink);
    }
  }
}