package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HireProfessor extends JDialog implements ActionListener {
    JTextField professorNameTextField,courseNameTextField,usernameTextField,passwordTextField;
    JButton submitButton;
    HireProfessor(){
        this.setTitle("Hire Professor");
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("Professor's name:");
        professorNameTextField = new JTextField(20);
        JLabel courseLabel = new JLabel("Field name:");
        courseNameTextField = new JTextField(20);
        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordTextField = new JTextField(20);
        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(professorNameTextField);
        panel.add(courseLabel);
        panel.add(courseNameTextField);
        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(submitButton);

        this.add(panel);
        this.pack();
        this.setModal(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == submitButton) {
                if (!professorNameTextField.getText().equals("") && !courseNameTextField.getText().equals("") && !usernameTextField.getText().equals("") && !passwordTextField.getText().equals("")) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "new teacher");
                    jobj.put("field",courseNameTextField.getText());
                    jobj.put("name",professorNameTextField.getText());
                    jobj.put("username",usernameTextField.getText());
                    jobj.put("password",passwordTextField.getText());
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if(xx){
                        this.dispose();
                    }
                }
            }
        }
        catch (Exception ex){

        }
    }
}
