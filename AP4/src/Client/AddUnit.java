package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AddUnit extends JDialog implements ActionListener {
    private JLabel lessonNameLabel, lessonCodeLabel, lessonUnitsLabel, lessonCapacityLabel, professorNameLabel, infoLabel;
    private JTextField lessonNameField, lessonField, lessonUnitsField, lessonCapacityField, professorIdField;
    private JButton submitButton;
    AddUnit(){
        this.setTitle("Add Unit");
        this.setSize(500, 500);
        this.setLayout(new GridLayout(8, 2));
        setLocationRelativeTo(null);

        // Create the labels
        lessonNameLabel = new JLabel("Unit Name:");
        lessonCodeLabel = new JLabel("Field Name:");
        lessonUnitsLabel = new JLabel("Number of Units:");
        lessonCapacityLabel = new JLabel("Unit Capacity:");
        professorNameLabel = new JLabel("Unit's Professor's ID:");
        infoLabel = new JLabel("Enter Unit Information:");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Create the text fields
        lessonNameField = new JTextField();
        lessonField = new JTextField();
        lessonUnitsField = new JTextField();
        lessonCapacityField = new JTextField();
        professorIdField = new JTextField();

        // Create the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setFocusable(false);

        // Add the components to the window
        this.add(infoLabel);
        this.add(new JLabel());
        this.add(lessonNameLabel);
        this.add(lessonNameField);
        this.add(lessonCodeLabel);
        this.add(lessonField);
        this.add(lessonUnitsLabel);
        this.add(lessonUnitsField);
        this.add(lessonCapacityLabel);
        this.add(lessonCapacityField);
        this.add(professorNameLabel);
        this.add(professorIdField);
        this.add(new JLabel());
        this.add(submitButton);
        // Show the window
        this.setModal(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == submitButton){
                if (!lessonCapacityField.getText().equals("") && !lessonField.getText().equals("") && !lessonNameField.getText().equals("") && !lessonUnitsField.getText().equals("")&&!professorIdField.getText().equals("")) {
                    HashMap data = new HashMap();
                    data.put("name",lessonNameField.getText());
                    data.put("vahed",Integer.parseInt(lessonUnitsField.getText()));
                    data.put("vahedlimit",Integer.parseInt(lessonCapacityField.getText()));
                    data.put("teacherid",Integer.parseInt(professorIdField.getText()));
                    data.put("field",lessonField.getText());
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "new vahed");
                    jobj.put("data" , data);
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
