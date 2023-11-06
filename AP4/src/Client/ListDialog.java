package Client;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;

public class ListDialog extends JDialog{
    private JList list;
    public ListDialog(ArrayList<HashMap> mylist) {
        // TODO Auto-generated constructor stub
        try {
            DefaultListModel dlm = new DefaultListModel();
            for(HashMap i : mylist) {
                String ii = ""+(int)i.get("id") +" - "+ (String)i.get("name") +" - "+ (String)i.get("field") +" - "+ (String)i.get("role");
                dlm.addElement(ii);
            }
            list = new JList(dlm);
            list.setVisibleRowCount(10);
            list.setBounds(0,0,500,500);
            this.setResizable(false);
            this.setSize(500,500);
            this.setLayout(null);
            this.setModal(true);

            this.add(list);
            this.setVisible(true);
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
}
