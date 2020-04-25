package part_3;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Display_4 extends JFrame implements ActionListener
{

  String[] container_stats = {"Container ID", "Item ID", "Volume Limit", "Weight Limit"};
  String[] armor_stats = {"Armor ID", "Item ID", "Place", "Protection Amount"};
  String[] weapon_stats = {"Weapon ID", "Item ID", "Ability ID"};
  String[] generic_stats = {"Generic Item ID", "Item ID"};

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
    addRightInfo(); // add the info buttons to the right panel
    addPanelButtons();  // add the button content

    pack();
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public ArrayList<Integer> getItemsOwnedWithStoredProcedure(String character_name) throws SQLException {
    String sql = "CALL get_items_owned(?)";
    Connection m_dbConn = null;
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, character_name);
    stmt.execute();
    ResultSet setOwned = stmt.getResultSet();

    ArrayList<Integer> items_owned = new ArrayList<>();
    while (setOwned.next()) {
      int data = setOwned.getInt("ID");
      items_owned.add(data);
    }
    return items_owned;
  }

  public ArrayList<Integer> getItemsWornWithStoredProcedure(String character_name) throws SQLException {
    String sql = "CALL get_items_worn(?)";
    Connection m_dbConn = null;
    CallableStatement stmt = m_dbConn.prepareCall(sql);
    stmt.setString(1, character_name);
    stmt.execute();
    ResultSet setWorn = stmt.getResultSet();

    ArrayList<Integer> items_worn = new ArrayList<>();
    while(setWorn.next()) {
      int data = setWorn.getInt("ID");
      items_worn.add(data);
    }
    return items_worn;
  }





  public void formatButtons()
  {




    removeItemsFromCharacterButton = new JButton("Remove Items From Character");
    removeItemsFromCharacterButton.setBackground(dark_pink);
    removeItemsFromCharacterButton.setBorder(blackBorder);
    rightPanel.add(removeItemsFromCharacterButton, BorderLayout.SOUTH);
  }







  public void addPanelButtons()
  {


    removeItemsFromCharacterButton.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == removeItemsFromCharacterButton)
    {
      switch_item_to_owned((String) items_right.getSelectedValue());
      removeItemsFromCharacterButton.setText("Items Removed");
      removeItemsFromCharacterButton.setBackground(light_pink);
    } else {
      for (int i = 0; i < i_w.length; i++) {
        if (event.getSource() == items_worn_info_buttons.get(i))
        {
          items_worn_info_buttons.get(i).setBackground(dark_pink);
          items_worn_info_windows.get(i).setVisible(true);
        }
      }
//      if(items_owned_info_windows.get(0).isVisible() == false) {
//        items_worn_info_buttons.get(0).setBackground(Color.white);
//      }
    }
  }

  public void switch_item_to_worn(String item) {
    //TODO
  }

  public void switch_item_to_owned(String item) {
    //TODO
  }
}