package part_3;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Display_1 extends JFrame implements ActionListener {

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

    public Display_1(Connection conn) {
        m_dbConn = conn;
        check_size = new Dimension();
        check_size.setSize(167, 200);
        size = new Dimension();
        size.setSize(1200, 33);
        BorderLayout display = new BorderLayout(500,0);
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

    private void fill_table() {
    }

    private void createHeader() {
        Color d_gray = new Color(172, 172, 172);
        JPanel headerPanel = new JPanel(new GridLayout(1,6));
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
        for(int i=0;i<6;i++) {
            headerPanel.add(labelArray[i]);
        }
        add("North",headerPanel);
    }

    private void createButtons() {
        JPanel actionPanel = new JPanel(new GridLayout(1,5));
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

        add("South",actionPanel);
    }

    private void createTable() {
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
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addItem) {
            add_Item();
        }
        else if(e.getSource() == editItem) {
            for(int i = 0; i < itr_size; i++) {
                if(itr[i].check_box.isSelected()) {
                    edit_Item(i);
                }
            }
        }
        else if(e.getSource() == deleteItem) {
            delete_Item();
        }
    }

    private void add_Item() {
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
        edit_Item(itr_size - 1);
    }

    private void edit_Item(int index) {
        JTextField insertBox = new JTextField("Enter a new type (0 = container, 1 = armor, 2 = weapon, 3 = generic)");
        JLabel space = new JLabel();
        JLabel space2 = new JLabel();
        JLabel space3 = new JLabel();
        JLabel space4 = new JLabel();
        insertBox.setColumns(6);
        ActionListener keyboard = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == insertBox) {
                    if (action == 0) {
                        int old_type = 0;
                        if (itr[index].no_display == true) {
                            old_type = Integer.parseInt(itr[index].type.getText());
                        }
                        String newInsert = insertBox.getText();
                        itr[index].type.setText(newInsert);
                        int i_type = Integer.parseInt(itr[index].type.getText());
                        if ((i_type < 3 && itr[index].no_display == false) || (old_type != Integer.parseInt(itr[index].type.getText()) && itr[index].no_display == true)) {
                            add_More_Button(itr[index], itr[index].more, i_type);
                        }
                        insertBox.setText("Enter a new ID");
                        pack();
                        action++;
                    } else if (action == 1) {
                        String newInsert = insertBox.getText();
                        itr[index].id.setText(newInsert);
                        insertBox.setText("Enter a new volume");
                        pack();
                        action++;
                    } else if (action == 2) {
                        String newInsert = insertBox.getText();
                        itr[index].volume.setText(newInsert);
                        insertBox.setText("Enter a new weight");
                        pack();
                        action++;
                    } else if (action == 3) {
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
                        if (i_type < 3) {
                            edit_more(itr[index], i_type);
                            itr[index].no_display = true;
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

    private void add_More_Button(ItemRow itemRow, JButton more, int i_type) {
        if (itemRow.no_display == true) {
            more.removeActionListener(itemRow.moreListener);
        }
        JFrame display = new JFrame();
        display.setLayout(new BorderLayout());
        JPanel more_variables;
        GridLayout layout = new GridLayout(2, 2);
        if (i_type > 1) {
            layout.setRows(1);
        }
        more_variables = new JPanel(layout);
        Dimension more_size = new Dimension();
        more_size.setSize(600, 100);
        display.setPreferredSize(more_size);
        more_variables.setPreferredSize(more_size);
        itemRow.more_layout = layout;
        switch (i_type) {
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
        ActionListener button = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == more) {
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

    private void edit_more(ItemRow itemRow, int i_type) {
        itemRow.more_display.setVisible(true);
        itemRow.more_layout.setRows(itemRow.more_layout.getRows() + 1);
        JTextField textbox = new JTextField("Enter new value");
        JLabel space = new JLabel();
        ActionListener edit_boxes = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == textbox) {
                    if (action == 0) {
                        itemRow.more1.setText(textbox.getText());
                        if (i_type < 2) {
                            textbox.setText("Enter new value");
                            pack();
                            action++;
                        } else {
                            itemRow.more_variables.remove(textbox);
                            itemRow.more_variables.remove(space);
                            itemRow.more_layout.setRows(itemRow.more_layout.getRows() - 1);
                            pack();
                            action = 0;
                        }
                    } else if (action == 1) {
                        itemRow.more2.setText(textbox.getText());
                        itemRow.more_variables.remove(textbox);
                        itemRow.more_variables.remove(space);
                        itemRow.more_layout.setRows(itemRow.more_layout.getRows() - 1);
                        pack();
                        action = 0;
                    }
                }
            }
        };
        textbox.addActionListener(edit_boxes);
        itemRow.more_variables.add(textbox);
        itemRow.more_variables.add(space);
        pack();
    }

    private void delete_Item() {
        int deleted = 0;
        for(int i = 0; i < itr_size; i++) {
            if(itr[i].check_box.isSelected()) {
                deleted++;
                itemRows.remove(itr[i].check_box);
                itemRows.remove(itr[i].id);
                itemRows.remove(itr[i].type);
                itemRows.remove(itr[i].volume);
                itemRows.remove(itr[i].weight);
                itemRows.remove(itr[i].more);
                itr[i].check_box.setSelected(false);
            }
        }
        i_rows.setRows((i_rows.getRows() - deleted));
        pack();
    }
}
