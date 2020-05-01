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


/**
 * Class to create the fourth display for the UI, which allows players to login and switch items
 * from being worn to being in storage and vice versa on their different characters.
 */
public class Display_4 extends JFrame implements ActionListener, ListSelectionListener
{
	/**
	 * Le base of data
	 */
	Connection m_dbConn;
	
	/**
	 * Instance variables for #aesthetic
	 */
	Color dark_pink = new Color(213, 166, 189);
	Color light_pink = new Color(234, 209, 220);
	Border blackBorder = BorderFactory.createLineBorder(Color.black);  // the standard border for components
	
	/**
	 * Instance variables for the left panel
	 */
	JPanel leftPanel, L_Heading;
	JTextField L_Login;
	JScrollPane L_ListScrollPane;
	JButton selectCharacterButton;
	ArrayList<String> characterList = new ArrayList<>();
	JList characters;
	
	/**
	 * Instance variables for the center panel
	 */
	JPanel centerPanel, C_Heading, C_ListPanel, C_InformationPanel;
	JScrollPane C_ListScrollPane;
	JButton addItemsToCharacterButton;
	ArrayList<Integer> itemsOwnedIDs = new ArrayList<>();
	ArrayList<JButton> items_owned_info_buttons = new ArrayList<>(itemsOwnedIDs.size());
	ArrayList<JFrame> items_owned_info_windows = new ArrayList<>(itemsOwnedIDs.size());
	JList itemsOwned;
	
	/**
	 * Instance variables for the right panel
	 */
	JPanel rightPanel, R_Heading, R_ListPanel, R_InformationPanel;
	JScrollPane R_ListScrollPane;
	JButton removeItemsFromCharacterButton;
	ArrayList<Integer> itemsWornIDs = new ArrayList<>();
	ArrayList<JButton> items_worn_info_buttons = new ArrayList<>(itemsWornIDs.size());
	ArrayList<JFrame> items_worn_info_windows = new ArrayList<>(itemsWornIDs.size());
	JList itemsWorn;
	
	
	/**
	 * Main method that does nothing
	 *
	 * @param args pointless parameter
	 */
	public static void main(String[] args)
	{
	}
	
	/**
	 * Constructor to create an instance of the display
	 *
	 * @param conn connection to the database
	 * @throws SQLException
	 */
	public Display_4(Connection conn) throws SQLException
	{
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
	
	/**
	 * Create the left panel of the display
	 */
	public void L_CreatePanel()
	{
		// add the left panel to the UI
		leftPanel = new JPanel(new BorderLayout());
		add("Left", leftPanel);
		
		// format it
		leftPanel.setBackground(Color.white);
		leftPanel.setBorder(blackBorder);
	}
	
	/**
	 * Create the left panel's heading and player login bar
	 */
	public void L_CreateHeading()
	{
		// entire heading
		L_Heading = new JPanel(new GridLayout(3, 1));
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
	
	/**
	 * Create the list to hold all the player's characters
	 */
	public void L_CreateCharacterList()
	{
		L_ListScrollPane = new JScrollPane(characters);
		L_ListScrollPane.setBackground(Color.white);
		L_ListScrollPane.setBorder(blackBorder);
		leftPanel.add(L_ListScrollPane, BorderLayout.CENTER);
		validate();
	}
	
	/**
	 * Create the button to let the player select a character
	 */
	public void L_CreateButton()
	{
		selectCharacterButton = new JButton("Select Character");
		selectCharacterButton.setBackground(dark_pink);
		selectCharacterButton.setBorder(blackBorder);
		selectCharacterButton.addActionListener(this);
		leftPanel.add(selectCharacterButton, BorderLayout.SOUTH);
	}
	
	
	//----------------- CENTER PANEL ------------------------------------------//
	
	/**
	 * Create the center panel of the display
	 */
	public void C_CreatePanel()
	{
		// add the center panel to the UI
		centerPanel = new JPanel(new BorderLayout());
		add("Center", centerPanel);
		
		// format it
		centerPanel.setBackground(Color.white);
		centerPanel.setBorder(blackBorder);
	}
	
	/**
	 * Create the center panel's heading
	 */
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
	
	/**
	 * Create the list to hold all the items owned by the character but not being worn
	 */
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
	
	/**
	 * Create the max number of information buttons
	 *
	 * @throws SQLException
	 */
	public void C_CreateItemInfoButtons() throws SQLException
	{
		items_owned_info_buttons.clear();
		// entire panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.white);
		BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
		infoPanel.setLayout(info_layout);
		
		// make max number of info buttons
		for (int i = 0; i < 32; i++)
		{
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
	
	/**
	 * Add information windows to each button that corresponds with an item in the center list
	 *
	 * @throws SQLException
	 */
	public void C_AddInformationToButtons() throws SQLException
	{
		items_owned_info_windows.clear();
		for (int i = 0; i < itemsOwnedIDs.size(); i++)
		{
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
				JLabel idk = new JLabel("   " + itemAttributes.get(x));
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
	
	/**
	 * Create the button that lets a player add items to their character
	 */
	public void C_CreateButton()
	{
		addItemsToCharacterButton = new JButton("Add Items to Character");
		addItemsToCharacterButton.setBackground(dark_pink);
		addItemsToCharacterButton.setBorder(blackBorder);
		addItemsToCharacterButton.addActionListener(this);
		centerPanel.add(addItemsToCharacterButton, BorderLayout.SOUTH);
	}
	
	
	//----------------- RIGHT PANEL ------------------------------------------//
	
	/**
	 * Create the right panel of the display
	 */
	public void R_CreatePanel()
	{
		// add the right panel to the UI
		rightPanel = new JPanel(new BorderLayout());
		add("Right", rightPanel);
		
		// format it
		rightPanel.setBackground(Color.white);
		rightPanel.setBorder(blackBorder);
	}
	
	/**
	 * Create the right panel's heading
	 */
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
	
	/**
	 * Create the list to hold all the items being worn by the character
	 */
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
	
	/**
	 * Create the max number of information buttons
	 *
	 * @throws SQLException
	 */
	public void R_CreateItemInfoButtons() throws SQLException
	{
		items_worn_info_buttons.clear();
		// entire panel
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.white);
		BoxLayout info_layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
		infoPanel.setLayout(info_layout);
		
		// make max number of info buttons
		for (int i = 0; i < 32; i++)
		{
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
	
	/**
	 * Add information windows to each button that corresponds with an item in the right list
	 *
	 * @throws SQLException
	 */
	public void R_AddInformationToButtons() throws SQLException
	{
		items_worn_info_windows.clear();
		for (int i = 0; i < itemsWornIDs.size(); i++)
		{
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
			for (int x = 0; x < itemAttributes.size(); x++)
			{
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
	
	/**
	 * Create the button that lets a player remove items from their character
	 */
	public void R_CreateButton()
	{
		removeItemsFromCharacterButton = new JButton("Remove Items From Character");
		removeItemsFromCharacterButton.setBackground(dark_pink);
		removeItemsFromCharacterButton.setBorder(blackBorder);
		removeItemsFromCharacterButton.addActionListener(this);
		rightPanel.add(removeItemsFromCharacterButton, BorderLayout.SOUTH);
	}
	
	
	//----------------- LISTENERS ------------------------------------------//
	
	/**
	 * Action listener for the components of the display
	 *
	 * @param event the action event
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == L_Login)
		{
			String player_login = L_Login.getText();
			try
			{
				getCharactersWithStoredProcedure(player_login);
			} catch (SQLException throwables)
			{
				throwables.printStackTrace();
			}
			leftPanel.remove(L_ListScrollPane);
			L_CreateCharacterList();
		} else if (event.getSource() == selectCharacterButton)
		{
			String character = (String) characters.getSelectedValue();
			selectCharacterButton.setText("Character Selected: " + character);
			selectCharacterButton.setBackground(light_pink);
			try
			{
				getItemsOwnedWithStoredProcedure(character);
				getItemsWornWithStoredProcedure(character);
			} catch (SQLException throwables)
			{
				throwables.printStackTrace();
			}
			C_ListScrollPane.validate();
			R_ListScrollPane.validate();
			try
			{
				C_AddInformationToButtons();
				R_AddInformationToButtons();
			} catch (SQLException throwables)
			{
				throwables.printStackTrace();
			}
		} else if (event.getSource() == addItemsToCharacterButton)
		{
			ArrayList<Integer> toSwitch = new ArrayList<>(itemsOwned.getSelectedValuesList());
			try
			{
				switchItemsToWorn(toSwitch);
			} catch (SQLException throwables)
			{
				throwables.printStackTrace();
			}
			selectCharacterButton.doClick();
		} else if (event.getSource() == removeItemsFromCharacterButton)
		{
			ArrayList<Integer> toSwitch = new ArrayList<>(itemsWorn.getSelectedValuesList());
			try
			{
				switchItemsToOwned(toSwitch);
			} catch (SQLException throwables)
			{
				throwables.printStackTrace();
			}
			selectCharacterButton.doClick();
		} else
		{
			for (int i = 0; i < itemsOwnedIDs.size(); i++)
			{
				if (event.getSource() == items_owned_info_buttons.get(i))
				{
					items_owned_info_buttons.get(i).setBackground(dark_pink);
					items_owned_info_windows.get(i).setVisible(true);
				}
			}
			for (int i = 0; i < itemsWornIDs.size(); i++)
			{
				if (event.getSource() == items_worn_info_buttons.get(i))
				{
					items_worn_info_buttons.get(i).setBackground(dark_pink);
					items_worn_info_windows.get(i).setVisible(true);
				}
			}
		}
	}
	
	/**
	 * Selection listener for the lists in the display
	 *
	 * @param e the list selection event
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
	}
	
	
	//----------------- MISCELLANEOUS ------------------------------------------//
	
	/**
	 * Figure out what type an item is
	 *
	 * @param item_id the ID of the item
	 * @return an ArrayList of the attribute names of that type
	 */
	public ArrayList<String> getItemType(int item_id)
	{
		String[] container_atts = {"Container ID", "Item ID", "Volume Limit", "Weight Limit"};
		String[] armor_atts = {"Armor ID", "Item ID", "Place", "Protection Amount"};
		String[] weapon_atts = {"Weapon ID", "Item ID", "Ability ID"};
		String[] generic_atts = {"Generic Item ID", "Item ID"};
		ArrayList<String> type_attributes = null;
		
		if (1 <= item_id && item_id <= 500)
		{
			type_attributes = new ArrayList<>(Arrays.asList(container_atts));
		} else if (501 <= item_id && item_id <= 1000)
		{
			type_attributes = new ArrayList<>(Arrays.asList(armor_atts));
		} else if (1001 <= item_id && item_id <= 1500)
		{
			type_attributes = new ArrayList<>(Arrays.asList(weapon_atts));
		} else if (1501 <= item_id && item_id <= 2000)
		{
			type_attributes = new ArrayList<>(Arrays.asList(generic_atts));
		}
		
		return type_attributes;
	}
	
	
	//----------------- SQL STUFF ------------------------------------------//
	
	/**
	 * Call the stored procedure that gets the list of characters for a certain player
	 *
	 * @param player_login the player's login
	 * @throws SQLException
	 */
	private void getCharactersWithStoredProcedure(String player_login) throws SQLException
	{
		String sql = "CALL get_characters(?)";
		CallableStatement stmt = m_dbConn.prepareCall(sql);
		stmt.setString(1, player_login);
		stmt.execute();
		ResultSet characterSet = stmt.getResultSet();
		
		characterList.clear();
		while (characterSet.next())
		{
			String name = characterSet.getString("C_Name");
			characterList.add(name);
		}
		
		DefaultListModel c = new DefaultListModel();
		for (int i = 0; i < characterList.size(); i++)
		{
			c.add(i, characterList.get(i));
		}
		
		characters = new JList(c);
		
		characterSet.close();
		stmt.close();
	}
	
	/**
	 * Calls the stored procedure that gets the items owned but not being worn by the selected character
	 *
	 * @param character_name the name of the character
	 * @throws SQLException
	 */
	public void getItemsOwnedWithStoredProcedure(String character_name) throws SQLException
	{
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
		((DefaultListModel) itemsOwned.getModel()).removeAllElements();
		
		DefaultListModel items = new DefaultListModel();
		for (int i = 0; i < itemsOwnedIDs.size(); i++)
		{
			items.add(i, itemsOwnedIDs.get(i));
			((DefaultListModel) (itemsOwned.getModel())).addElement(itemsOwnedIDs.get(i));
		}
		
		setOwned.close();
		stmt.close();
	}
	
	/**
	 * Calls the stored procedure that gets the items being worn by the selected character
	 *
	 * @param character_name the name of the character
	 * @throws SQLException
	 */
	public void getItemsWornWithStoredProcedure(String character_name) throws SQLException
	{
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
		((DefaultListModel) itemsWorn.getModel()).removeAllElements();
		
		DefaultListModel items = new DefaultListModel();
		for (int i = 0; i < itemsWornIDs.size(); i++)
		{
			items.add(i, itemsWornIDs.get(i));
			((DefaultListModel) (itemsWorn.getModel())).addElement(itemsWornIDs.get(i));
		}
		
		setWorn.close();
		stmt.close();
	}
	
	/**
	 * Add items not being worn to the character
	 *
	 * @param item_IDs the list of IDs of the items to be worn
	 * @throws SQLException
	 */
	public void switchItemsToWorn(ArrayList<Integer> item_IDs) throws SQLException
	{
		for (int i = 0; i < item_IDs.size(); i++)
		{
			Statement stmt = m_dbConn.createStatement();
			String sql = "UPDATE ITEM SET W_Name = O_Name WHERE ID = " + item_IDs.get(i) + " AND W_Name IS NULL";
			stmt.executeUpdate(sql);
			stmt.close();
		}
	}
	
	/**
	 * Remove items being worn from the character
	 *
	 * @param item_IDs the list of IDs of the items to be removed
	 * @throws SQLException
	 */
	public void switchItemsToOwned(ArrayList<Integer> item_IDs) throws SQLException
	{
		for (int i = 0; i < item_IDs.size(); i++)
		{
			Statement stmt = m_dbConn.createStatement();
			String sql = "UPDATE ITEM SET W_Name = NULL WHERE ID = " + item_IDs.get(i) + " AND W_Name = O_Name";
			stmt.executeUpdate(sql);
			stmt.close();
		}
	}
	
	/**
	 * Get the values of the item's various attributes
	 *
	 * @param itemID         the ID of the item
	 * @param attributeNames the list of attribute names for that type of item
	 * @return the list of values in corresponding order to the list of attribute names
	 * @throws SQLException
	 */
	public ArrayList<Integer> getAttributeValues(int itemID, ArrayList<String> attributeNames) throws SQLException
	{
		ArrayList<Integer> values = new ArrayList<>(attributeNames.size());
		String select;
		
		if (attributeNames.get(0) == "Container ID")
		{
			select = "SELECT * FROM CONTAINER WHERE I_ID = (?)";
			CallableStatement stmt = m_dbConn.prepareCall(select);
			stmt.setInt(1, itemID);
			stmt.execute();
			ResultSet results = stmt.getResultSet();
			if (results.next())
			{
				values.add(0, results.getInt("Con_ID"));
				values.add(1, results.getInt("I_ID"));
				values.add(2, results.getInt("Volume_Limit"));
				values.add(3, results.getInt("Weight_Limit"));
			}
		} else if (attributeNames.get(0) == "Armor ID")
		{
			select = "SELECT * FROM ARMOR WHERE I_ID = (?)";
			CallableStatement stmt = m_dbConn.prepareCall(select);
			stmt.setInt(1, itemID);
			stmt.execute();
			ResultSet results = stmt.getResultSet();
			if (results.next())
			{
				values.add(0, results.getInt("A_ID"));
				values.add(1, results.getInt("I_ID"));
				values.add(2, results.getInt("Place"));
				values.add(3, results.getInt("Protection_Amount"));
			}
		} else if (attributeNames.get(0) == "Weapon ID")
		{
			select = "SELECT * FROM WEAPON WHERE I_ID = (?)";
			CallableStatement stmt = m_dbConn.prepareCall(select);
			stmt.setInt(1, itemID);
			stmt.execute();
			ResultSet results = stmt.getResultSet();
			if (results.next())
			{
				values.add(0, results.getInt("W_ID"));
				values.add(1, results.getInt("I_ID"));
				values.add(2, results.getInt("Ability_ID"));
			}
		} else if (attributeNames.get(0) == "Generic Item ID")
		{
			select = "SELECT * FROM GENERIC_ITEM WHERE I_ID = (?)";
			CallableStatement stmt = m_dbConn.prepareCall(select);
			stmt.setInt(1, itemID);
			stmt.execute();
			ResultSet results = stmt.getResultSet();
			if (results.next())
			{
				values.add(0, results.getInt("GI_ID"));
				values.add(1, results.getInt("I_ID"));
			}
			
			results.close();
			stmt.close();
		}
		
		return values;
	}
}