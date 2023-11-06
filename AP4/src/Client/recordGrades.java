package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class recordGrades extends JDialog implements ActionListener {
    private JTextField usernameField;
    private JTextField courseNameField;
    private JTextField gradeField;
    JButton submitButton;
    recordGrades(){
        setTitle("Record Students Grades");
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("  Student ID:");
        usernameField = new JTextField();
        JLabel courseNameLabel = new JLabel("  Course ID:");
        courseNameField = new JTextField();
        JLabel gradeLabel = new JLabel("  Grade:");
        gradeField = new JTextField();

        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);

        add(usernameLabel);
        add(usernameField);
        add(courseNameLabel);
        add(courseNameField);
        add(gradeLabel);
        add(gradeField);
        add(new JLabel());
        add(submitButton);
        setModal(true);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == submitButton){
                if(!usernameField.getText().equals("") && !courseNameField.getText().equals("")&&!gradeField.getText().equals("")) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("command", "set score");
                    jobj.put("studentid", Integer.parseInt(usernameField.getText()));
                    jobj.put("lessonid", Integer.parseInt(courseNameField.getText()));
                    jobj.put("score", gradeField.getText());
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if (xx) {
                        this.dispose();
                    }
                }
            }
        }catch (Exception ex){

        }
    }
}
