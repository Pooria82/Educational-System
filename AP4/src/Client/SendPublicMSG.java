package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class SendPublicMSG extends JDialog implements ActionListener {
    JButton send;
    JTextField text;
    SendPublicMSG(){
        this.setTitle("send public msg");

        this.setSize(600, 300);

        this.setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        send = new JButton("send");
        send.setFocusable(false);
        send.addActionListener(this);
        text=new JTextField();
        panel.add(text);
        panel.add(send);

        this.add(panel);
        this.setModal(true);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == send) {
                if(!text.getText().equals("")){
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "send public msg");
                    jobj.put("msg" ,text.getText());
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if(xx){
                        this.dispose();
                    }
                }
            }
        }catch (Exception ex){

        }
    }
}
