package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Student extends JDialog implements ActionListener {
    JButton selectUnitButton ;
    JButton seeReportCardButton ;
    JButton correspondenceWithProfessorButton;
    JButton objectionButton ;
    int id;
    String field;
    Student(HashMap info) {
        id =(int) info.get("id");
        field =(String) info.get("field");
        this.setTitle("Student Page");

        this.setSize(600, 300);
        this.setLocationRelativeTo(null);

        // Create labels
        JLabel idLabel = new JLabel("  ID : "+ info.get("id"));
        JLabel usernameLabel = new JLabel("  Username : "+(String) info.get("username"));
        JLabel nameLabel = new JLabel("  Name : "+(String) info.get("name"));
        JLabel roleLabel = new JLabel("  Role : "+(String) info.get("role"));


        // Create buttons
        selectUnitButton = new JButton("Select Unit");
        selectUnitButton.setFocusable(false);
        selectUnitButton.addActionListener(this);
        seeReportCardButton = new JButton("See Report Card");
        seeReportCardButton.setFocusable(false);
        seeReportCardButton.addActionListener(this);
        correspondenceWithProfessorButton = new JButton("Correspondence with Professor");
        correspondenceWithProfessorButton.setFocusable(false);
        correspondenceWithProfessorButton.addActionListener(this);
        objectionButton = new JButton("send Objection");
        objectionButton.setFocusable(false);
        objectionButton.addActionListener(this);

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
        this.add(selectUnitButton);
        this.add(seeReportCardButton);
        this.add(correspondenceWithProfessorButton);
        this.add(objectionButton);
        this.setModal(true);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == selectUnitButton){
                JSONObject jobj2 = new JSONObject();
                jobj2.put("command" , "get open close select");
                jobj2.put("field",field);
                Login.outputStream.writeObject(jobj2);
                Login.outputStream.flush();
                JSONObject x2 = (JSONObject) Login.inputStream.readObject();
                boolean xx2 = (Boolean) x2.get("valid");
                if(xx2) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("command", "get selection list");
                    jobj.put("id", id);
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if (xx) {
                        //System.out.println("[[[");
                        SelectVahed sv = new SelectVahed((ArrayList<HashMap>) x.get("selection list"), id);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"not open");
                }
            }
            if(e.getSource() == seeReportCardButton){//todo test it
                JSONObject jobj = new JSONObject();
                jobj.put("command" , "get report");
                jobj.put("id",id);
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if(xx){
                    //System.out.println("[[[");
                    ShowScoreTable sst = new ShowScoreTable((ArrayList<ArrayList<String>>) ((HashMap)x.get("report")).get("table"),(String) ((HashMap)x.get("report")).get("title"));
                }
            }
            if(e.getSource() == objectionButton){
                Objections ob = new Objections(id);//todo :test it
            }
            if(e.getSource() == correspondenceWithProfessorButton){
                JSONObject jobj = new JSONObject();
                jobj.put("command" , "teacher list");
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if(xx){
                    Message ms = new Message((ArrayList<HashMap>) x.get("teacherlist"),id);
                }

            }
        }catch (Exception ex){

        }
    }
}
