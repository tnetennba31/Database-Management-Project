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

public class Display_1 extends JFrame implements ActionListener
{
    
    JButton addItem;
    JButton editItem;
    JButton deleteItem;
    
    Border blackBorder = BorderFactory.createLineBorder(Color.black);
    Connection m_dbConn;
    Dimension size;
    
    GridLayout i_rows;
    JPanel itemRows;
    ItemRow[] itr;
    int itr_size;
    
    Dimension check_size;
    
    int action = 0;
    
    boolean can_add_new;
    
    public Display_1(Connection conn) throws SQLException
    {
        m_dbConn = conn;
        check_size = new Dimension();
        check_size.setSize(167, 200);
        size = new Dimension();
        size.setSize(1200, 33);
        BorderLayout display = new BorderLayout(500, 0);
        setLayout(display);
        createHeader();
        createButtons();
        createTable();
        fill_table();
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        can_add_new = true;
    }
    
    private void fill_table() throws SQLException
    {
        get_all_items();
    }
    
    private void get_all_items() throws SQLException
    {
        String sql = "CALL get_all_items(?)";
        CallableStatement st = m_dbConn.prepareCall(sql);
        int blank = 0;
        st.setInt(1, blank);
        st.execute();
        ResultSet set = st.getResultSet();
        while (set.next())
        {
            add_Item();
            String id = set.getString("ID");
            String volume = set.getString("Volume");
            String weight = set.getString("Weight");
            add_db_item(itr[itr_size - 1], id, volume, weight);
        }
    }
    
    private void createHeader()
    {
        Color d_gray = new Color(172, 172, 172);
        JPanel headerPanel = new JPanel(new GridLayout(1, 6));
        headerPanel.setBorder(blackBorder);
        headerPanel.setBackground(d_gray);
        headerPanel.setPreferredSize(size);
        JLabel[] labelArray = new JLabel[6];
        labelArray[0] = new JLabel("Select");
        labelArray[0].setPreferredSize(check_size);
        labelArray[1] = new JLabel("| Type");
        labelArray[2] = new JLabel("| ID");
        labelArray[3] = new JLabel("| Volume");
        labelArray[4] = new JLabel("| Weight");
        labelArray[5] = new JLabel("| More");
        for (int i = 0; i < 6; i++)
        {
            headerPanel.add(labelArray[i]);
        }
        add("North", headerPanel);
    }
    
    private void createButtons()
    {
        JPanel actionPanel = new JPanel(new GridLayout(1, 5));
        actionPanel.setBorder(blackBorder);
        actionPanel.setBackground(Color.white);
        actionPanel.setPreferredSize(size);
        
        addItem = new JButton("Add New Item");
        addItem.addActionListener(this);
        actionPanel.add(addItem);
        
        JLabel addDes = new JLabel("  | Creates a new item");
        actionPanel.add(addDes);
        JLabel actionDes = new JLabel("Actions |  ");
        actionPanel.add(actionDes);
        actionDes.setHorizontalTextPosition(SwingConstants.RIGHT);
        
        editItem = new JButton("Edit");
        editItem.addActionListener(this);
        actionPanel.add(editItem);
        
        deleteItem = new JButton("Delete");
        deleteItem.addActionListener(this);
        actionPanel.add(deleteItem);
        
        add("South", actionPanel);
    }
    
    private void createTable()
    {
        i_rows = new GridLayout(0, 7);
        itemRows = new JPanel(i_rows);
        Dimension itemSize = new Dimension();
        itemSize.setSize(1000, 600);
        /*itemRows.setPreferredSize(itemSize); */
        itemRows.setBackground(Color.white);
        itr = new ItemRow[2000];
        itr_size = 0;
        JScrollPane pane = new JScrollPane(itemRows);
        pane.setPreferredSize(itemSize);
        add("Center", pane);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addItem)
        {
            add_Item();
        } else if (e.getSource() == editItem)
        {
            for (int i = 0; i < itr_size; i++)
            {
                if (itr[i].check_box.isSelected())
                {
                    itr[i].editing = true;
                    edit_Item(i);
                }
            }
        } else if (e.getSource() == deleteItem)
        {
            try
            {
                delete_Item();
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }
    
    private void add_Item()
    {
        ItemRow newRow = new ItemRow(new JCheckBox(), new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JButton(". . ."));
        newRow.check_box.setPreferredSize(check_size);
        itr[itr_size] = newRow;
        itr_size = itr_size + 1;
        i_rows.setRows((i_rows.getRows() + 1));
        itemRows.add(newRow.check_box);
        itemRows.add(newRow.type);
        itemRows.add(newRow.id);
        itemRows.add(newRow.volume);
        itemRows.add(newRow.weight);
        itemRows.add(newRow.more);
        pack();
        if (can_add_new != false)
        {
            edit_Item(itr_size - 1);
        }
    }
    
    private void add_db_item(ItemRow itemRow, String int1, String int2, String int3) throws SQLException
    {
        itemRow.id.setText(int1);
        itemRow.volume.setText(int2);
        itemRow.weight.setText(int3);
        JLabel var1 = new JLabel();
        JLabel var1fill = new JLabel();
        JLabel var2 = new JLabel();
        JLabel var2fill = new JLabel();
        int type = Integer.parseInt(int1);
        int id = Integer.parseInt(itemRow.id.getText());
        String sql = "";
        if (type <= 500)
        {
            type = 0;
            var1.setText(" Volume Limit |");
            var2.setText(" Weight Limit |");
            sql = "CALL get_container(?)";
        } else if (type <= 1000)
        {
            type = 1;
            var1.setText(" Place |");
            var2.setText(" Protection |");
            sql = "CALL get_armor(?)";
            id = id - 500;
        } else if (type <= 1500)
        {
            type = 2;
            var1.setText(" Ability ID |");
            sql = "CALL get_weapon(?)";
            id = id - 1000;
        } else
        {
            type = 3;
        }
        itemRow.type.setText("" + type);
        if (type < 3)
        {
            CallableStatement st = m_dbConn.prepareCall(sql);
            st.setInt(1, id);
            st.execute();
            ResultSet set = st.getResultSet();
            JFrame display = new JFrame();
            Dimension more_size = new Dimension();
            more_size.setSize(600, 100);
            display.setPreferredSize(more_size);
            GridLayout more_grid = new GridLayout(2, 2);
            if (type > 1)
            {
                more_grid.setRows(1);
            }
            JPanel more_values = new JPanel(more_grid);
            more_values.setPreferredSize(more_size);
            itemRow.more_layout = more_grid;
            itemRow.no_display = true;
            while (set.next())
            {
                var1fill.setText(set.getString(2));
                more_values.add(var1);
                more_values.add(var1fill);
                itemRow.more1 = var1fill;
                if (type < 2)
                {
                    var2fill.setText(set.getString(3));
                    more_values.add(var2);
                    more_values.add(var2fill);
                    itemRow.more2 = var2fill;
                }
            }
            itemRow.more_variables = more_values;
            display.add(more_values);
            itemRow.more_display = display;
            ActionListener more_button = new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (e.getSource() == itemRow.more)
                    {
                        itemRow.more_display.setVisible(true);
                        pack();
                    }
                }
            };
            itemRow.more.addActionListener(more_button);
            pack();
        }
    }
    
    private void edit_Item(int index)
    {
        JTextField insertBox = new JTextField("Enter a new type (0 = container, 1 = armor, 2 = weapon, 3 = generic)");
        JLabel space = new JLabel();
        JLabel space2 = new JLabel();
        JLabel space3 = new JLabel();
        JLabel space4 = new JLabel();
        insertBox.setColumns(6);
        ActionListener keyboard = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == insertBox)
                {
                    if (action == 0)
                    {
                        int old_type = 0;
                        if (itr[index].no_display == true)
                        {
                            old_type = Integer.parseInt(itr[index].type.getText());
                        }
                        String newInsert = insertBox.getText();
                        itr[index].type.setText(newInsert);
                        int i_type = Integer.parseInt(itr[index].type.getText());
                        if ((i_type < 3 && itr[index].no_display == false) || (old_type != Integer.parseInt(itr[index].type.getText()) && itr[index].no_display == true))
                        {
                            add_More_Button(itr[index], itr[index].more, i_type);
                        }
                        insertBox.setText("Enter a new ID");
                        pack();
                        action++;
                    } else if (action == 1)
                    {
                        String newInsert = insertBox.getText();
                        itr[index].id.setText(newInsert);
                        insertBox.setText("Enter a new volume");
                        pack();
                        action++;
                    } else if (action == 2)
                    {
                        String newInsert = insertBox.getText();
                        itr[index].volume.setText(newInsert);
                        insertBox.setText("Enter a new weight");
                        pack();
                        action++;
                    } else if (action == 3)
                    {
                        action = 0;
                        String newInsert = insertBox.getText();
                        itr[index].weight.setText(newInsert);
                        itemRows.remove(insertBox);
                        itemRows.remove(space);
                        itemRows.remove(space2);
                        itemRows.remove(space3);
                        itemRows.remove(space4);
                        i_rows.setRows((i_rows.getRows() - 1));
                        pack();
                        int i_type = Integer.parseInt(itr[index].type.getText());
                        if (i_type < 3)
                        {
                            edit_more(itr[index], i_type);
                            itr[index].no_display = true;
                        }
                        String sql = "CALL add_item(?, ?, ?)";
                        if (itr[index].editing == true)
                        {
                            sql = "CALL update_item(?, ?, ?)";
                        }
                        CallableStatement st = null;
                        try
                        {
                            st = m_dbConn.prepareCall(sql);
                        } catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                        int id = Integer.parseInt(itr[index].id.getText());
                        try
                        {
                            st.setInt(1, id);
                        } catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                        int vol = Integer.parseInt(itr[index].volume.getText());
                        try
                        {
                            st.setInt(2, vol);
                        } catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                        int wei = Integer.parseInt(itr[index].weight.getText());
                        try
                        {
                            st.setInt(3, wei);
                        } catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                        try
                        {
                            st.execute();
                        } catch (SQLException throwables)
                        {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        };
        insertBox.addActionListener(keyboard);
        i_rows.setRows((i_rows.getRows() + 1));
        itemRows.add(insertBox);
        itemRows.add(space);
        itemRows.add(space2);
        itemRows.add(space3);
        itemRows.add(space4);
        pack();
    }
    
    private void add_More_Button(ItemRow itemRow, JButton more, int i_type)
    {
        if (itemRow.no_display == true)
        {
            more.removeActionListener(itemRow.moreListener);
        }
        JFrame display = new JFrame();
        display.setLayout(new BorderLayout());
        JPanel more_variables;
        GridLayout layout = new GridLayout(2, 2);
        if (i_type > 1)
        {
            layout.setRows(1);
        }
        more_variables = new JPanel(layout);
        Dimension more_size = new Dimension();
        more_size.setSize(600, 100);
        display.setPreferredSize(more_size);
        more_variables.setPreferredSize(more_size);
        itemRow.more_layout = layout;
        switch (i_type)
        {
            case 0:
                JLabel var1 = new JLabel(" Volume Limit |");
                JLabel var1_fill = new JLabel();
                JLabel var2 = new JLabel(" Weight Limit |");
                JLabel var2_fill = new JLabel();
                var1.setPreferredSize(more_size);
                itemRow.more1 = var1_fill;
                itemRow.more2 = var2_fill;
                more_variables.add(var1);
                more_variables.add(var1_fill);
                more_variables.add(var2);
                more_variables.add(var2_fill);
                break;
            case 1:
                var1 = new JLabel(" Place |");
                var1_fill = new JLabel("(0=head,1=chest,2=arms,3=legs");
                var2 = new JLabel(" Protection Amount |");
                var2_fill = new JLabel();
                var1.setPreferredSize(more_size);
                itemRow.more1 = var1_fill;
                itemRow.more2 = var2_fill;
                more_variables.add(var1);
                more_variables.add(var1_fill);
                more_variables.add(var2);
                more_variables.add(var2_fill);
                break;
            case 2:
                var1 = new JLabel(" Ability ID |");
                var1_fill = new JLabel();
                var1.setPreferredSize(more_size);
                itemRow.more1 = var1_fill;
                more_variables.add(var1);
                more_variables.add(var1_fill);
                break;
        }
        display.add(more_variables);
        ActionListener button = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == more)
                {
                    display.setVisible(true);
                    display.pack();
                }
            }
        };
        more.addActionListener(button);
        itemRow.more_display = display;
        itemRow.more_variables = more_variables;
        itemRow.moreListener = button;
    }
    
    private void edit_more(ItemRow itemRow, int i_type)
    {
        itemRow.more_display.setVisible(true);
        itemRow.more_layout.setRows(itemRow.more_layout.getRows() + 1);
        JTextField textbox = new JTextField("Enter new value");
        JLabel space = new JLabel();
        ActionListener edit_boxes = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == textbox)
                {
                    if (action == 0)
                    {
                        itemRow.more1.setText(textbox.getText());
                        if (i_type < 2)
                        {
                            textbox.setText("Enter new value");
                            pack();
                            action++;
                        } else
                        {
                            itemRow.more_variables.remove(textbox);
                            itemRow.more_variables.remove(space);
                            itemRow.more_layout.setRows(itemRow.more_layout.getRows() - 1);
                            pack();
                            action = 0;
                        }
                    } else if (action == 1)
                    {
                        itemRow.more2.setText(textbox.getText());
                        itemRow.more_variables.remove(textbox);
                        itemRow.more_variables.remove(space);
                        itemRow.more_layout.setRows(itemRow.more_layout.getRows() - 1);
                        pack();
                        action = 0;
                        int id = Integer.parseInt(itemRow.id.getText());
                        String sql = null;
                        CallableStatement st = null;
                        int n_id = id;
                        if (i_type == 0)
                        {
                            sql = "CALL add_container(?, ?, ?, ?)";
                            if (itemRow.editing == true)
                            {
                                sql = "CALL update_container(?, ?, ?)";
                            }
                            try
                            {
                                st = m_dbConn.prepareCall(sql);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            n_id = n_id + 1;
                            n_id = n_id - 1;
                            int more1 = Integer.parseInt(itemRow.more1.getText());
                            int more2 = Integer.parseInt(itemRow.more2.getText());
                            try
                            {
                                st.setInt(1, n_id);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(2, more1);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(3, more2);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            if (itemRow.editing == false)
                            {
                                try
                                {
                                    st.setInt(4, id);
                                } catch (SQLException throwables)
                                {
                                    throwables.printStackTrace();
                                }
                            }
                            try
                            {
                                st.execute();
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            
                        } else if (i_type == 1)
                        {
                            n_id = n_id - 500;
                            sql = "CALL add_armor(?, ?, ?, ?)";
                            if (itemRow.editing == true)
                            {
                                sql = "CALL update_armor(?, ?, ?, ?)";
                            }
                            try
                            {
                                st = m_dbConn.prepareCall(sql);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            int more1 = Integer.parseInt(itemRow.more1.getText());
                            int more2 = Integer.parseInt(itemRow.more2.getText());
                            try
                            {
                                st.setInt(1, n_id);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(2, more1);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(3, more2);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(4, id);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.execute();
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                        } else if (i_type == 2)
                        {
                            n_id = n_id - 1000;
                            sql = "CALL add_weapon(?, ?, ?)";
                            if (itemRow.editing == true)
                            {
                                sql = "CALL update_weapon(?, ?, ?)";
                            }
                            try
                            {
                                st = m_dbConn.prepareCall(sql);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            int more1 = Integer.parseInt(itemRow.more1.getText());
                            try
                            {
                                st.setInt(1, n_id);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(2, more1);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(3, id);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.execute();
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                        } else if (i_type == 3)
                        {
                            n_id = n_id - 1500;
                            sql = "CALL add_generic(?, ?)";
                            if (itemRow.editing == true)
                            {
                                sql = "CALL update_generic(?, ?)";
                            }
                            try
                            {
                                st = m_dbConn.prepareCall(sql);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(1, n_id);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.setInt(2, id);
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                            try
                            {
                                st.execute();
                            } catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }
                        }
                    }
                    itemRow.editing = false;
                }
            }
        };
        textbox.addActionListener(edit_boxes);
        itemRow.more_variables.add(textbox);
        itemRow.more_variables.add(space);
        pack();
    }
    
    private void delete_Item() throws SQLException
    {
        int deleted = 0;
        for (int i = 0; i < itr_size; i++)
        {
            if (itr[i].check_box.isSelected())
            {
                deleted++;
                itemRows.remove(itr[i].check_box);
                itemRows.remove(itr[i].id);
                itemRows.remove(itr[i].type);
                itemRows.remove(itr[i].volume);
                itemRows.remove(itr[i].weight);
                itemRows.remove(itr[i].more);
                itr[i].check_box.setSelected(false);
                int i_type = Integer.parseInt(itr[i].type.getText());
                String sql = null;
                CallableStatement st = null;
                if (i_type == 0)
                {
                    sql = "CALL delete_container(?)";
                } else if (i_type == 1)
                {
                    sql = "CALL delete_armor(?)";
                } else if (i_type == 2)
                {
                    sql = "CALL delete_weapon(?)";
                } else if (i_type == 3)
                {
                    sql = "CALL delete_generic(?)";
                }
                st = m_dbConn.prepareCall(sql);
                st.setInt(1, i_type);
                st.execute();
                sql = "CALL delete_item(?)";
                st = m_dbConn.prepareCall(sql);
                st.setInt(1, i_type);
                st.execute();
            }
        }
        i_rows.setRows((i_rows.getRows() - deleted));
        pack();
        
    }
}
