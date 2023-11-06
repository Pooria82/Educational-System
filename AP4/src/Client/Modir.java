package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Modir extends JDialog implements ActionListener {
    // Create buttons
    JButton addCourseButton;
    JButton viewProfessorsButton ;
    JButton hireProfessorButton ;
    JButton groupManagerButton;
    JButton studentRegistrationButton ;
    JButton publicMSG;
    Modir(HashMap info) {
        try {
            this.setTitle("University Management System");
            this.setSize(400, 300);
            this.setLocationRelativeTo(null);

            // Create labels
            JLabel idLabel = new JLabel("  ID : "+ info.get("id"));
            JLabel usernameLabel = new JLabel("  Username : "+(String) info.get("username"));
            JLabel nameLabel = new JLabel("  Name : "+(String) info.get("name"));
            JLabel roleLabel = new JLabel("  Role : "+(String) info.get("role"));

            // Create buttons
            addCourseButton = new JButton("Add Course");
            addCourseButton.setFocusable(false);
            addCourseButton.addActionListener(this);
            viewProfessorsButton = new JButton("View List of Professors");
            viewProfessorsButton.setFocusable(false);
            viewProfessorsButton.addActionListener(this);
            hireProfessorButton = new JButton("Hire a Professor");
            hireProfessorButton.setFocusable(false);
            hireProfessorButton.addActionListener(this);
            groupManagerButton = new JButton("Determine Group Manager");
            groupManagerButton.setFocusable(false);
            groupManagerButton.addActionListener(this);
            studentRegistrationButton = new JButton("Student Registration");
            studentRegistrationButton.setFocusable(false);
            studentRegistrationButton.addActionListener(this);
            publicMSG = new JButton("public msg");
            publicMSG.setFocusable(false);
            publicMSG.addActionListener(this);
            // Set layout for the this
            this.setLayout(new GridLayout(7, 7));

            // Add components to the this
            this.add(idLabel);
            this.add(new JLabel()); // Empty cell
            this.add(usernameLabel);
            this.add(new JLabel()); // Empty cell
            this.add(nameLabel);
            this.add(new JLabel()); // Empty cell
            this.add(roleLabel);
            this.add(new JLabel()); // Empty cell
            this.add(addCourseButton);
            this.add(viewProfessorsButton);
            this.add(hireProfessorButton);
            this.add(groupManagerButton);
            this.add(studentRegistrationButton);
            this.add(publicMSG);
            this.setModal(true);
            this.setVisible(true);
        }catch (Exception ex){
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == groupManagerButton) {
                DetermineGroupManager dt = new DetermineGroupManager();
            }
            if(e.getSource() == addCourseButton) {
                AddingCourse ad = new AddingCourse();
            }
            if(e.getSource() == publicMSG){
                SendPublicMSG spm = new SendPublicMSG();
            }
            if(e.getSource() == studentRegistrationButton){
                StudentRegistration st = new StudentRegistration();
            }
            if(e.getSource() == hireProfessorButton){
                HireProfessor st = new HireProfessor();
            }
            if(e.getSource() == viewProfessorsButton){
                JSONObject jobj = new JSONObject();
                jobj.put("command" , "teacher list");
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if(xx){
                    ListDialog ld = new ListDialog((ArrayList<HashMap>) x.get("teacherlist"));
                }
            }
        }
        catch (Exception ex){

        }
    }
}
