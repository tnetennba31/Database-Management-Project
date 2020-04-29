package part_3;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Display_4 extends JFrame implements ActionListener, ListSelectionListener {
  Connection m_dbConn;

  ArrayList<String> characterList = new ArrayList<>();
  JList characters;

  JList itemsOwned;
  JList itemsWorn;

  ArrayList<Integer> itemsOwnedIDs = new ArrayList<>();
  ArrayList<Integer> itemsWornIDs = new ArrayList<>();

  Color dark_pink = new Color(213 , 166 , 189);
  Color light_pink = new Color(234 , 209 , 220);
  Border blackBorder = BorderFactory.createLineBorder(Color.black);  // the standard border for components

  public static void main(String[] args) {}

  public Display_4(Connection conn) throws SQLException {
    m_dbConn = conn;

    // assign lists for SQL query results
    characters = new JList(new DefaultListModel());
    itemsWorn = new JList(new DefaultListModel());
    itemsOwned = new JList(new DefaultListModel());

    // format window
    setLayout(new GridLayout(1, 3));
    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    // run left panel creation methods
    L_CreatePanel();
    L_CreateHeading();
    L_CreateCharacterList();
    L_CreateButton();

    // run center panel creation methods
    C_CreatePanel();
    C_CreateHeading();
    C_CreateItemsOwnedList();
    C_CreateItemInfoButtons();
    C_CreateButton();

    // run right panel creation methods
    R_CreatePanel();
    R_CreateHeading();
    R_CreateItemsWornList();
    R_CreateItemInfoButtons();
    R_CreateButton();

    pack();
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  //----------------- LEFT PANEL ------------------------------------------//

  JPanel leftPanel, L_Heading;
  JTextField L_Login;
  JScrollPane L_ListScrollPane;
  JButton selectCharacterButton;

  public void L_CreatePanel()
  {
    // add the left panel to the UI
    leftPanel = new JPanel(new BorderLayout());
    add("Left", leftPanel);

    // format it
    leftPanel.setBackground(Color.white);
    leftPanel.setBorder(blackBorder);
  }

  public void L_CreateHeading()
  {
    // entire heading
    L_Heading = new JPanel(new GridLayout(3,1));
    L_Heading.setBackground(light_pink);
    L_Heading.setBorder(blackBorder);
    leftPanel.add(L_Heading, BorderLayout.NORTH);

    // instruction bar
    JPanel idk = new JPanel(new BorderLayout());
    idk.setBackground(dark_pink);
    JLabel instruction = new JLabel("Enter Player Login:");
    idk.add(instruction);
    L_Heading.add(idk);

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
    L_Login = new JTextField();
    L_Login.addActionListener(this);
    background.add(L_Login, BorderLayout.CENTER);
    L_Heading.add(background);

    // list title bar
    JLabel characters = new JLabel("Characters");
    characters.setBackground(light_pink);
    characters.setBorder(blackBorder);
    characters.setHorizontalAlignment(SwingConstants.CENTER);
    L_Heading.add(characters);
  }

  public void L_CreateCharacterList()
  {
    L_ListScrollPane = new JScrollPane(characters);
    L_ListScrollPane.setBackground(Color.white);
    L_ListScrollPane.setBorder(blackBorder);
    leftPanel.add(L_ListScrollPane, BorderLayout.CENTER);
    validate();
  }

  public void L_CreateButton()
  {
    selectCharacterButton = new JButton("Select Character");
    selectCharacterButton.setBackground(dark_pink);
    selectCharacterButton.setBorder(blackBorder);
    selectCharacterButton.addActionListener(this);
    leftPanel.add(selectCharacterButton, BorderLayout.SOUTH);
  }


  //----------------- CENTER PANEL ------------------------------------------//

  JPanel centerPanel, C_Heading, C_ListPanel, C_InformationPanel;
  JScrollPane C_ListScrollPane;
  JButton addItemsToCharacterButton;

  ArrayList<JButton> items_owned_info_buttons = new ArrayList<>(itemsOwnedIDs.size());
  ArrayList<JFrame> items_owned_info_windows = new ArrayList<>(itemsOwnedIDs.size());

  public void C_CreatePanel()
  {
    // add the center panel to the UI
    centerPanel = new JPanel(new BorderLayout());
    add("Center", centerPanel);

    // format it
    centerPanel.setBackground(Color.white);
    centerPanel.setBorder(blackBorder);
  }

  public void C_CreateHeading()
  {
    // entire heading
    C_Heading = new JPanel(new BorderLayout());
    C_Heading.setBackground(light_pink);
    C_Heading.setBorder(blackBorder);
    centerPanel.add(C_Heading, BorderLayout.NORTH);

    // list title bar
    JPanel c_major = new JPanel(new GridLayout(3, 1));
    c_major.setBackground(light_pink);
    c_major.setBorder(blackBorder);
    C_Heading.add(c_major, BorderLayout.CENTER);
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
    C_Heading.add(c_minor, BorderLayout.EAST);
    JPanel c_top_minor = new JPanel();
    c_top_minor.setBackground(dark_pink);
    c_minor.add(c_top_minor);
    JLabel info_middle = new JLabel("     Info     ");
    info_middle.setHorizontalAlignment(SwingConstants.CENTER);
    c_minor.add(info_middle);
  }

  public void C_CreateItemsOwnedList()
  {
    // list panel
    C_ListPanel = new JPanel(new BorderLayout());
    C_ListPanel.setBackground(Color.white);
    C_ListPanel.setBorder(blackBorder);
    centerPanel.add(C_ListPanel, BorderLayout.CENTER);

    // scroll pane list
    C_ListScrollPane = new JScrollPane(itemsOwned);
    itemsOwned.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    itemsOwned.addListSelectionListener(this);
    C_ListPanel.add(C_ListScrollPane, BorderLayout.CENTER);
    validate();
  }

  public void C_CreateItemInfoButtons() throws SQLException {
    items_owned_info_buttons.clear();
    // entire panel
    JPanel infoPanel = new JPanel();
    infoPanel.setBackground(Color.white);
    BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
    infoPanel.setLayout(info_layout);

    // make max number of info buttons
    for (int i = 0; i < 32; i++) {
      // fill the panel with buttons
      JButton infoButton = new JButton("    .  .  .     ");
      infoButton.addActionListener(this);
      infoButton.setBackground(Color.white);
      infoButton.setBorder(blackBorder);
      infoPanel.add(infoButton);
      items_owned_info_buttons.add(i, infoButton);  // add button to list to keep track
    }
    C_ListPanel.add(infoPanel, BorderLayout.EAST);
    validate();
  }

  public void C_AddInformationToButtons() throws SQLException {
    items_owned_info_windows.clear();
    for (int i = 0; i < itemsOwnedIDs.size(); i++) {
      // link each button to a new pop-up window
      JFrame window = new JFrame();
      window.pack();
      window.setDefaultCloseOperation(HIDE_ON_CLOSE);
      window.setLayout(new GridLayout());
      window.setLocationRelativeTo(items_owned_info_buttons.get(i));
      items_owned_info_windows.add(i, window);  // add window to list to keep track

      // add information to each window
      ArrayList<String> itemAttributes = getItemType(itemsOwnedIDs.get(i));
      ArrayList<Integer> attributeValues = getAttributeValues(itemsOwnedIDs.get(i), itemAttributes);
      C_InformationPanel = new JPanel(new GridLayout(itemAttributes.size(), 2));
      C_InformationPanel.setBackground(light_pink);
      C_InformationPanel.setBorder(blackBorder);
      for (int x = 0; x < itemAttributes.size(); x++)
      {
        // attribute title in left block
        JLabel idk = new JLabel("   "  + itemAttributes.get(x));
        idk.setBorder(blackBorder);
        C_InformationPanel.add(idk);

        // attribute value in right block
        JLabel yeet = new JLabel("   " + attributeValues.get(x));
        yeet.setBorder(blackBorder);
        C_InformationPanel.add(yeet);
      }
      window.add(C_InformationPanel);
    }
  }

  public void C_CreateButton()
  {
    addItemsToCharacterButton = new JButton("Add Items to Character");
    addItemsToCharacterButton.setBackground(dark_pink);
    addItemsToCharacterButton.setBorder(blackBorder);
    addItemsToCharacterButton.addActionListener(this);
    centerPanel.add(addItemsToCharacterButton, BorderLayout.SOUTH);
  }


  //----------------- RIGHT PANEL ------------------------------------------//

  JPanel rightPanel, R_Heading, R_ListPanel, R_InformationPanel;
  JScrollPane R_ListScrollPane;
  JButton removeItemsFromCharacterButton;

  ArrayList<JButton> items_worn_info_buttons = new ArrayList<>(itemsWornIDs.size());
  ArrayList<JFrame> items_worn_info_windows = new ArrayList<>(itemsWornIDs.size());

  public void R_CreatePanel()
  {
    // add the right panel to the UI
    rightPanel = new JPanel(new BorderLayout());
    add("Right", rightPanel);

    // format it
    rightPanel.setBackground(Color.white);
    rightPanel.setBorder(blackBorder);
  }

  public void R_CreateHeading()
  {
    // entire heading
    R_Heading = new JPanel(new BorderLayout());
    R_Heading.setBackground(light_pink);
    R_Heading.setBorder(blackBorder);
    rightPanel.add(R_Heading, BorderLayout.NORTH);

    // list title bar
    JPanel r_major = new JPanel(new GridLayout(3, 1));
    r_major.setBackground(light_pink);
    r_major.setBorder(blackBorder);
    R_Heading.add(r_major, BorderLayout.CENTER);
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
    R_Heading.add(r_minor, BorderLayout.EAST);
    JPanel r_top_minor = new JPanel();
    r_top_minor.setBackground(dark_pink);
    r_minor.add(r_top_minor);
    JLabel info_right = new JLabel("     Info     ");
    info_right.setHorizontalAlignment(SwingConstants.CENTER);
    r_minor.add(info_right);
  }

  public void R_CreateItemsWornList()
  {
    // list panel
    R_ListPanel = new JPanel(new BorderLayout());
    R_ListPanel.setBackground(Color.white);
    R_ListPanel.setBorder(blackBorder);
    rightPanel.add(R_ListPanel, BorderLayout.CENTER);

    // scroll pane list
    R_ListScrollPane = new JScrollPane(itemsWorn);
    itemsWorn.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    itemsWorn.addListSelectionListener(this);
    R_ListPanel.add(R_ListScrollPane, BorderLayout.CENTER);
    validate();
  }

  public void R_CreateItemInfoButtons() throws SQLException {
    items_worn_info_buttons.clear();
    // entire panel
    JPanel infoPanel = new JPanel();
    infoPanel.setBackground(Color.white);
    BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
    infoPanel.setLayout(info_layout);

    // make max number of info buttons
    for (int i = 0; i < 32; i++) {
      // fill the panel with buttons
      JButton infoButton = new JButton("    .  .  .     ");
      infoButton.addActionListener(this);
      items_worn_info_buttons.add(i, infoButton);
      infoButton.setBackground(Color.white);
      infoButton.setBorder(blackBorder);
      infoPanel.add(infoButton);  // add button to list to keep track
    }
    R_ListPanel.add(infoPanel, BorderLayout.EAST);
    validate();
  }

  public void R_AddInformationToButtons() throws SQLException {
    items_worn_info_windows.clear();
    for (int i = 0; i < itemsWornIDs.size(); i++) {
      // link each button to a new pop-up window
      JFrame window = new JFrame();
      window.pack();
      window.setDefaultCloseOperation(HIDE_ON_CLOSE);
      window.setLayout(new GridLayout());
      window.setLocationRelativeTo(items_worn_info_buttons.get(i));
      items_worn_info_windows.add(window);  // add window to list to keep track

      // add information to each window
      ArrayList<String> itemAttributes = getItemType(itemsWornIDs.get(i));
      ArrayList<Integer> attributeValues = getAttributeValues(itemsWornIDs.get(i), itemAttributes);
      R_InformationPanel = new JPanel(new GridLayout(itemAttributes.size(), 2));
      R_InformationPanel.setBackground(light_pink);
      R_InformationPanel.setBorder(blackBorder);
      for (int x = 0; x < itemAttributes.size(); x++) {
        // attribute title in left block
        JLabel idk = new JLabel("   " + itemAttributes.get(x));
        idk.setBorder(blackBorder);
        R_InformationPanel.add(idk);

        // attribute value in right block
        JLabel yeet = new JLabel("   " + attributeValues.get(x));
        yeet.setBorder(blackBorder);
        R_InformationPanel.add(yeet);
      }
      window.add(R_InformationPanel);
    }
  }

  public void R_CreateButton()
  {
    removeItemsFromCharacterButton = new JButton("Remove Items From Character");
    removeItemsFromCharacterButton.setBackground(dark_pink);
    removeItemsFromCharacterButton.setBorder(blackBorder);
    removeItemsFromCharacterButton.addActionListener(this);
    rightPanel.add(removeItemsFromCharacterButton, BorderLayout.SOUTH);
  }


  //----------------- LISTENERS ------------------------------------------//

  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == L_Login) {
      String player_login = L_Login.getText();
      try {
        getCharactersWithStoredProcedure(player_login);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      leftPanel.remove(L_ListScrollPane);
      L_CreateCharacterList();
    } else if (event.getSource() == selectCharacterButton) {
      String character = (String) characters.getSelectedValue();
      selectCharacterButton.setText("Character Selected: " + character);
      selectCharacterButton.setBackground(light_pink);
      try {
        getItemsOwnedWithStoredProcedure(character);
        getItemsWornWithStoredProcedure(character);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      C_ListScrollPane.validate();
      R_ListScrollPane.validate();
      try {
        C_AddInformationToButtons();
        R_AddInformationToButtons();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    } else if (event.getSource() == addItemsToCharacterButton) {
      ArrayList<Integer> toSwitch = new ArrayList<>(itemsOwned.getSelectedValuesList());
      for (int i = 0; i < toSwitch.size(); i++) {
        try {
          switchItemsToWornWithStoredProcedure(toSwitch);
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    } else if (event.getSource() == removeItemsFromCharacterButton) {
      ArrayList<Integer> toSwitch = new ArrayList<>(itemsWorn.getSelectedValuesList());
      for (int i = 0; i < toSwitch.size(); i++) {
        try {
          switchItemsToOwnedWithStoredProcedure(toSwitch);
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
    } else {
      for (int i = 0; i < itemsOwnedIDs.size(); i++) {
        if (event.getSource() == items_owned_info_buttons.get(i)) {
          items_owned_info_buttons.get(i).setBackground(dark_pink);
          items_owned_info_windows.get(i).setVisible(true);
        }
      }
      for (int i = 0; i < itemsWornIDs.size(); i++) {
        if (event.getSource() == items_worn_info_buttons.get(i)) {
          items_worn_info_buttons.get(i).setBackground(dark_pink);
          items_worn_info_windows.get(i).setVisible(true);
        }
      }
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {}


  //----------------- MISCELLANEOUS ------------------------------------------//

  public ArrayList<String> getItemType(int item_id)
  {
    String[] container_atts = {"Container ID", "Item ID", "Volume Limit", "Weight Limit"};
    String[] armor_atts = {"Armor ID", "Item ID", "Place", "Protection Amount"};
    String[] weapon_atts = {"Weapon ID", "Item ID", "Ability ID"};
    String[] generic_atts = {"Generic Item ID", "Item ID"};
    ArrayList<String> type_attributes = null;

    if (1 <= item_id && item_id <= 500) {
      type_attributes = new ArrayList<>(Arrays.asList(container_atts));
    } else if (501 <= item_id && item_id <= 1000) {
      type_attributes = new ArrayList<>(Arrays.asList(armor_atts));
    } else if (1001 <= item_id && item_id <= 1500) {
      type_attributes = new ArrayList<>(Arrays.asList(weapon_atts));
    } else if (1501 <= item_id && item_id <= 2000) {
      type_attributes = new ArrayList<>(Arrays.asList(generic_atts));
    }

    return type_attributes;
  }


  //----------------- SQL STUFF ------------------------------------------//

  private void getCharactersWithStoredProcedure(String player_login) throws SQLException {
    String sql = "CALL get_characters(?)";
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, player_login);
    stmt.execute();
    ResultSet characterSet = stmt.getResultSet();

    characterList.clear();
    while (characterSet.next()) {
      String name = characterSet.getString("C_Name");
      characterList.add(name);
    }

    DefaultListModel c = new DefaultListModel();
    for (int i = 0; i < characterList.size(); i++) {
      c.add(i, characterList.get(i));
    }

    characters = new JList(c);
  }

  public void getItemsOwnedWithStoredProcedure(String character_name) throws SQLException {
    String sql = "CALL get_items_owned(?)";
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, character_name);
    stmt.execute();
    ResultSet setOwned = stmt.getResultSet();

    itemsOwnedIDs.clear();
    while (setOwned.next())
    {
      int data = setOwned.getInt("ID");
      itemsOwnedIDs.add(data);
    }
    ((DefaultListModel)itemsOwned.getModel()).removeAllElements();

    DefaultListModel items = new DefaultListModel();
    for (int i = 0; i < itemsOwnedIDs.size(); i++) {
      items.add(i, itemsOwnedIDs.get(i));
      ((DefaultListModel)(itemsOwned.getModel())).addElement(itemsOwnedIDs.get(i));
    }
  }

  public void getItemsWornWithStoredProcedure(String character_name) throws SQLException {
    String sql = "CALL get_items_worn(?)";
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, character_name);
    stmt.execute();
    ResultSet setWorn = stmt.getResultSet();

    itemsWornIDs.clear();
    while (setWorn.next())
    {
      int data = setWorn.getInt("ID");
      itemsWornIDs.add(data);
    }
    ((DefaultListModel)itemsWorn.getModel()).removeAllElements();

    DefaultListModel items = new DefaultListModel();
    for (int i = 0; i < itemsWornIDs.size(); i++) {
      items.add(i, itemsWornIDs.get(i));
      ((DefaultListModel)(itemsWorn.getModel())).addElement(itemsWornIDs.get(i));
    }
  }

  public void switchItemsToWornWithStoredProcedure(ArrayList<Integer> item_IDs) throws SQLException {
    String sql = "CALL switch_item_to_worn(?)";
    for (int i = 0; i < item_IDs.size(); i++) {
      CallableStatement stmt = m_dbConn.prepareCall(sql);
      stmt.setInt(1, item_IDs.get(i));
      stmt.execute();
    }
    C_ListScrollPane.validate();
    R_ListScrollPane.validate();
    C_InformationPanel.validate();
    R_InformationPanel.validate();
  }

  public void switchItemsToOwnedWithStoredProcedure(ArrayList<Integer> item_IDs) throws SQLException {
    String sql = "CALL switch_item_to_owned(?)";
    for (int i = 0; i < item_IDs.size(); i++) {
      CallableStatement stmt = m_dbConn.prepareCall(sql);
      stmt.setInt(1, item_IDs.get(i));
      stmt.execute();
    }
    C_ListScrollPane.repaint();
    R_ListScrollPane.repaint();
    C_InformationPanel.repaint();
    R_InformationPanel.repaint();
  }

  public ArrayList<Integer> getAttributeValues(int itemID, ArrayList<String> attributeNames) throws SQLException {
    ArrayList<Integer> values = new ArrayList<>(attributeNames.size());
    String select;

    if (attributeNames.get(0) == "Container ID") {
      select = "SELECT * FROM CONTAINER WHERE I_ID = (?)";
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      if (results.next()) {
        values.add(0, results.getInt("Con_ID"));
        values.add(1, results.getInt("I_ID"));
        values.add(2, results.getInt("Volume_Limit"));
        values.add(3, results.getInt("Weight_Limit"));
      }
    } else if (attributeNames.get(0) == "Armor ID") {
      select = "SELECT * FROM ARMOR WHERE I_ID = (?)";
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      if (results.next()) {
        values.add(0, results.getInt("A_ID"));
        values.add(1, results.getInt("I_ID"));
        values.add(2, results.getInt("Place"));
        values.add(3, results.getInt("Protection_Amount"));
      }
    } else if (attributeNames.get(0) == "Weapon ID") {
      select = "SELECT * FROM WEAPON WHERE I_ID = (?)";
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      if (results.next()) {
        values.add(0, results.getInt("W_ID"));
        values.add(1, results.getInt("I_ID"));
        values.add(2, results.getInt("Ability_ID"));
      }
    } else if (attributeNames.get(0) == "Generic Item ID") {
      select = "SELECT * FROM GENERIC_ITEM WHERE I_ID = (?)";
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      if (results.next()) {
        values.add(0, results.getInt("GI_ID"));
        values.add(1, results.getInt("I_ID"));
      }

    }

    return values;
  }
}