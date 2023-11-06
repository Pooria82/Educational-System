package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentRegistration extends JDialog implements ActionListener {
    JTextField studentNameTextField,courseNameTextField,usernameTextField,passwordTextField;
    JButton submitButton;
    StudentRegistration(){
        this.setTitle("Student Registration");
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("Student's name:");
        studentNameTextField = new JTextField(20);
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
        panel.add(studentNameTextField);
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
                if (!studentNameTextField.getText().equals("") && !courseNameTextField.getText().equals("") && !usernameTextField.getText().equals("") && !passwordTextField.getText().equals("")) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "new student");
                    jobj.put("field",courseNameTextField.getText());
                    jobj.put("name",studentNameTextField.getText());
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
