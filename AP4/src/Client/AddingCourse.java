package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddingCourse  extends JDialog implements ActionListener {
    JButton submitButton;
    JTextField courseNameTextField;
    AddingCourse() {

        this.setTitle("Adding a Course");
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("Name of the Course:");
        courseNameTextField = new JTextField(20);
        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(courseNameTextField);
        panel.add(submitButton);

        this.add(panel);
        this.setModal(true);
        this.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == submitButton){
                if(!courseNameTextField.getText().equals("")){
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "new field");
                    jobj.put("fieldname",courseNameTextField.getText());
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
