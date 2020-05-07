package part_3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ItemRow {

    JCheckBox check_box;
    JLabel type;
    JLabel id;
    JLabel volume;
    JLabel weight;
    JButton more;

    JFrame more_display;
    GridLayout more_layout;
    JPanel more_variables;
    JLabel more1;
    JLabel more2;
    ActionListener moreListener;

    int index;

    boolean no_display;
    boolean editing;

    public ItemRow(JCheckBox ck, JLabel tp, JLabel i_d, JLabel vl, JLabel wt, JButton mb) {
        check_box = ck;
        type = tp;
        id = i_d;
        volume = vl;
        weight = wt;
        more = mb;
        no_display = false;
        editing = false;
    }
}
