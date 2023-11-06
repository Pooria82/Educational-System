package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetermineGroupManager extends JDialog implements ActionListener {
    JTextField idTextField;
    JTextField courseTextField;
    JButton submitButton;
    DetermineGroupManager() {
        // Set this properties
        this.setTitle("Determine Group Manager");

        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        JLabel idLabel = new JLabel("ID:");
        JLabel courseLabel = new JLabel("Course name:");

        idTextField = new JTextField(20);
        courseTextField = new JTextField(20);
        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(courseLabel);
        panel.add(courseTextField);
        panel.add(submitButton);

        this.add(panel);
        this.pack();
        this.setModal(true);
        // Display the JFrame
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {//Todo : test it
        try {
            if (e.getSource() == submitButton) {
                if (!idTextField.getText().equals("") && !courseTextField.getText().equals("")) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "set modirgroup");
                    jobj.put("field",courseTextField.getText());
                    jobj.put("id",Integer.parseInt(idTextField.getText()));
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

