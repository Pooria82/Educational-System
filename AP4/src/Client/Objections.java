package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Objections extends JDialog implements ActionListener {
    JButton onButton;
    JButton offButton;
    JTextField unitIdTextField = new JTextField();
    JTextField studentIdTextField = new JTextField();
    int id;
    Objections(int id){
        this.id =id;

        this.setSize(400, 200);

        this.setLayout(new GridLayout(3, 2));
        this.setLocationRelativeTo(null);

        // Create label and text field for Student ID
        JLabel studentIdLabel = new JLabel("Student ID:");

        this.add(studentIdLabel);
        this.add(studentIdTextField);

        // Create label and text field for Unit ID
        JLabel unitIdLabel = new JLabel("Unit ID:");

        this.add(unitIdLabel);
        this.add(unitIdTextField);

        // Create on/off buttons
        onButton = new JButton("On");
        offButton = new JButton("Off");
        onButton.setFocusable(false);
        onButton.addActionListener(this);
        offButton.setFocusable(false);
        offButton.addActionListener(this);
        if(id != 0){
            studentIdTextField.setEnabled(false);
        }
        this.add(onButton);
        this.add(offButton);
        this.setModal(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            String cmd = "send obj";
            if(id == 0){
                id = Integer.parseInt(studentIdTextField.getText());
                cmd = "answered obj";
            }
            if(e.getSource() == onButton){
                JSONObject jobj = new JSONObject();
                jobj.put("command" , cmd);
                jobj.put("studentid" , id);
                jobj.put("lessonid",Integer.parseInt(unitIdTextField.getText()));
                jobj.put("obj",1);
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if(xx){

                }
            }
            if(e.getSource() == offButton){
                JSONObject jobj = new JSONObject();
                jobj.put("command" , cmd);
                jobj.put("studentid" , id);
                jobj.put("lessonid",Integer.parseInt(unitIdTextField.getText()));
                jobj.put("obj",0);
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if(xx){

                }
            }
        }catch (Exception ex){

        }
    }
}
