package part_3;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Display_4 extends JFrame implements ActionListener
{
  ArrayList<String> characterList = new ArrayList<>();
  JList characters;

  ArrayList<Integer> itemsOwnedList = new ArrayList<>();
  JList itemsOwned;

  ArrayList<Integer> itemsWornList = new ArrayList<>();
  JList itemsWorn;

  ArrayList<Integer> itemsOwnedIDs = new ArrayList<>();
  ArrayList<Integer> itemsWornIDs = new ArrayList<>();

  Color dark_pink = new Color(213 , 166 , 189);
  Color light_pink = new Color(234 , 209 , 220);
  Border blackBorder = BorderFactory.createLineBorder(Color.black);  // the standard border for components

  public static void main(String[] args) throws SQLException {
    new Display_4();
  }
  public Display_4() throws SQLException {
    // assign lists for SQL query results
    characters = new JList(characterList.toArray());
    itemsOwned = new JList(itemsOwnedList.toArray());
    itemsWorn = new JList(itemsWornList.toArray());

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
    C_CreateItemInfo();
    C_CreateButton();

    // run right panel creation methods
    R_CreatePanel();
    R_CreateHeading();
    R_CreateItemsWornList();
    R_CreateItemInfo();
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

  ArrayList<JButton> items_owned_info_buttons = new ArrayList<>(itemsOwnedList.size());
  ArrayList<JFrame> items_owned_info_windows = new ArrayList<>(itemsOwnedList.size());

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
    C_ListPanel.add(C_ListScrollPane, BorderLayout.CENTER);
  }

  public void C_CreateItemInfo() throws SQLException {
    // entire panel
    JPanel infoPanel = new JPanel();
    infoPanel.setBackground(Color.white);
    BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
    infoPanel.setLayout(info_layout);

    // make an info button for each item in the list
    for (int i = 0; i < itemsOwnedList.size(); i++) {
      // fill the panel with buttons
      JButton infoButton = new JButton("    .  .  .     ");
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

    C_ListPanel.add(infoPanel, BorderLayout.EAST);
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

  ArrayList<JButton> items_worn_info_buttons = new ArrayList<>(itemsWornList.size());
  ArrayList<JFrame> items_worn_info_windows = new ArrayList<>(itemsWornList.size());

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
    R_ListPanel.add(R_ListScrollPane, BorderLayout.CENTER);
  }

  public void R_CreateItemInfo() throws SQLException {
    // entire panel
    JPanel infoPanel = new JPanel();
    infoPanel.setBackground(Color.white);
    BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
    infoPanel.setLayout(info_layout);

    // make info button for each item in the list
    for (int i = 0; i < itemsWornList.size(); i++) {
      // fill the panel with buttons
      JButton infoButton = new JButton("    .  .  .     ");
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
      ArrayList<String> itemAttributes = getItemType(itemsWornIDs.get(i));
      ArrayList<Integer> attributeValues = getAttributeValues(itemsWornIDs.get(i), itemAttributes);
      R_InformationPanel = new JPanel(new GridLayout(itemAttributes.size(), 2));
      R_InformationPanel.setBackground(light_pink);
      R_InformationPanel.setBorder(blackBorder);
      for (int x = 0; x < itemAttributes.size(); x++)
      {
        // attribute title in left block
        JLabel idk = new JLabel("   "  + itemAttributes.get(x));
        idk.setBorder(blackBorder);
        R_InformationPanel.add(idk);

        // attribute value in right block
        JLabel yeet = new JLabel("   " + attributeValues.get(x));
        yeet.setBorder(blackBorder);
        R_InformationPanel.add(yeet);
      }
      window.add(R_InformationPanel);
    }
    R_ListPanel.add(infoPanel, BorderLayout.EAST);
  }

  public void R_CreateButton()
  {
    removeItemsFromCharacterButton = new JButton("Remove Items From Character");
    removeItemsFromCharacterButton.setBackground(dark_pink);
    removeItemsFromCharacterButton.setBorder(blackBorder);
    removeItemsFromCharacterButton.addActionListener(this);
    rightPanel.add(removeItemsFromCharacterButton, BorderLayout.SOUTH);
  }


  //----------------- ACTION LISTENER ------------------------------------------//

  @Override
  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == L_Login) {
      String player_login = L_Login.getText();
      try {
        getCharactersWithStoredProcedure(player_login);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    } else if (event.getSource() == selectCharacterButton) {
      String character = (String) characters.getSelectedValue();
      selectCharacterButton.setText("Character Selected: " + character);
      selectCharacterButton.setBackground(light_pink);
      try {
        getItemsOwnedWithStoredProcedure(character);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      try {
        getItemsWornWithStoredProcedure(character);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    } else if (event.getSource() == addItemsToCharacterButton) {
      int[] stuff = itemsOwned.getSelectedIndices();
      ArrayList<Integer> itemsToSwitch = new ArrayList<>();
      for (int i = 0; i < stuff.length; i++) {
        itemsToSwitch.add(itemsOwnedIDs.get(stuff[i]));
      }
      try {
        switchItemToWornWithStoredProcedure(itemsToSwitch);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    } else if (event.getSource() == removeItemsFromCharacterButton) {
      int[] stuff = itemsWorn.getSelectedIndices();
      ArrayList<Integer> itemsToSwitch = new ArrayList<>();
      for (int i = 0; i < stuff.length; i++) {
        itemsToSwitch.add(itemsWornIDs.get(stuff[i]));
      }
      try {
        switchItemToOwnedWithStoredProcedure(itemsToSwitch);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    } else {
      for (int i = 0; i < itemsOwnedList.size(); i++) {
        if (event.getSource() == items_owned_info_buttons.get(i)) {
          items_owned_info_buttons.get(i).setBackground(dark_pink);
          items_owned_info_windows.get(i).setVisible(true);
          if (!items_owned_info_windows.get(i).isVisible()) {
            items_owned_info_buttons.get(i).setBackground(Color.white);
          }
        }
      }
      for (int i = 0; i < itemsWornList.size(); i++) {
        if (event.getSource() == items_worn_info_buttons.get(i)) {
          items_worn_info_buttons.get(i).setBackground(dark_pink);
          items_worn_info_windows.get(i).setVisible(true);
          if (!items_worn_info_windows.get(i).isVisible()) {
            items_worn_info_buttons.get(i).setBackground(Color.white);
          }
        }
      }
    }
  }


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
    Connection m_dbConn = null;
    assert false;
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, player_login);
    stmt.execute();
    ResultSet characterSet = stmt.getResultSet();

    while (characterSet.next()) {
      String name = characterSet.getString("C_Name");
      characterList.add(name);
    }
  }

  public void getItemsOwnedWithStoredProcedure(String character_name) throws SQLException {
    String sql = "CALL get_items_owned(?)";
    Connection m_dbConn = null;
    assert false;
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, character_name);
    stmt.execute();
    ResultSet setOwned = stmt.getResultSet();

    while (setOwned.next()) {
      int data = setOwned.getInt("ID");
      itemsOwnedList.add(data);
    }
  }

  public void getItemsWornWithStoredProcedure(String character_name) throws SQLException {
    String sql = "CALL get_items_worn(?)";
    Connection m_dbConn = null;
    assert false;
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, character_name);
    stmt.execute();
    ResultSet setWorn = stmt.getResultSet();

    while(setWorn.next()) {
      int data = setWorn.getInt("ID");
      itemsWornList.add(data);
    }
  }

  public void switchItemToWornWithStoredProcedure(@NotNull ArrayList<Integer> item_IDs) throws SQLException {
    String sql = "CALL switch_item_to_worn(?)";
    Connection m_dbConn = null;
    assert false;
    for (int i = 0; i < item_IDs.size(); i++) {
      CallableStatement stmt = m_dbConn.prepareCall(sql);
      stmt.setInt(1, item_IDs.get(i));
      stmt.execute();
    }
  }

  public void switchItemToOwnedWithStoredProcedure(@NotNull ArrayList<Integer> item_IDs) throws SQLException {
    String sql = "CALL switch_item_to_owned(?)";
    Connection m_dbConn = null;
    assert false;
    for (int i = 0; i < item_IDs.size(); i++) {
      CallableStatement stmt = m_dbConn.prepareCall(sql);
      stmt.setInt(1, item_IDs.get(i));
      stmt.execute();
    }
  }

  public ArrayList<Integer> getAttributeValues(int itemID, ArrayList<String> attributeNames) throws SQLException {
    ArrayList<Integer> values = new ArrayList<>(attributeNames.size());
    String select;
    Connection m_dbConn = null;
    assert false;

    if (attributeNames.get(0) == "Container ID") {
      select = new String("SELECT * FROM CONTAINER WHERE I_ID = (?)");
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      values.add(0, results.getInt("Con_ID"));
      values.add(1, results.getInt("I_ID"));
      values.add(2, results.getInt("Volume_Limit"));
      values.add(3, results.getInt("Weight_Limit"));
    } else if (attributeNames.get(0) == "Armor ID") {
      select = new String("SELECT * FROM ARMOR WHERE I_ID = (?)");
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      values.add(0, results.getInt("A_ID"));
      values.add(1, results.getInt("I_ID"));
      values.add(2, results.getInt("Place"));
      values.add(3, results.getInt("Protection_Amount"));
    } else if (attributeNames.get(0) == "Weapon ID") {
      select = new String("SELECT * FROM WEAPON WHERE I_ID = (?)");
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      values.add(0, results.getInt("W_ID"));
      values.add(1, results.getInt("I_ID"));
      values.add(2, results.getInt("Ability_ID"));
    } else if (attributeNames.get(0) == "Generic Item ID") {
      select = new String("SELECT * FROM GENERIC_ITEM WHERE I_ID = (?)");
      CallableStatement stmt = m_dbConn.prepareCall(select);
      stmt.setInt(1, itemID);
      stmt.execute();
      ResultSet results = stmt.getResultSet();
      values.add(0, results.getInt("GI_ID"));
      values.add(1, results.getInt("I_ID"));
    }

    return values;
  }
}