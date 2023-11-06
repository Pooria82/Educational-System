package Client;

import javax.swing.*;

public class ShowPublicMSG extends JDialog {
    JLabel text = new JLabel();
    ShowPublicMSG(String msg){
        this.setTitle("public messages");

        //this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        text.setText(msg);

        this.add(text);
        this.pack();
        this.setModal(true);
        this.setVisible(true);
    }
}
