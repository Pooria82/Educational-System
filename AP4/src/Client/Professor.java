package Client;



import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Professor extends JDialog implements ActionListener {
    private JButton recordScoreButton;
    private JButton correspondenceButton;
    private JButton responseButton;
    int id;
    Professor(HashMap info){
        id =(int) info.get("id");
        this.setTitle("Professor Page");

        this.setSize(600, 300);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6,6));

        // Labels
        JLabel idLabel = new JLabel("  ID : "+ info.get("id"));
        JLabel usernameLabel = new JLabel("  Username : "+(String) info.get("username"));
        JLabel nameLabel = new JLabel("  Name : "+(String) info.get("name"));
        JLabel roleLabel = new JLabel("  Role : "+(String) info.get("role"));

        panel.add(idLabel);
        panel.add(new JLabel()); // Empty cell
        panel.add(usernameLabel);
        panel.add(new JLabel()); // Empty cell
        panel.add(nameLabel);
        panel.add(new JLabel()); // Empty cell
        panel.add(roleLabel);
        panel.add(new JLabel()); // Empty cell

        // Buttons
        recordScoreButton = new JButton("Record Score");
        recordScoreButton.setFocusable(false);
        recordScoreButton.addActionListener(this);
        panel.add(recordScoreButton);

        correspondenceButton = new JButton("Correspondence with the Users Student");
        correspondenceButton.setFocusable(false);
        correspondenceButton.addActionListener(this);
        panel.add(correspondenceButton);

        responseButton = new JButton("Response to Objections");
        responseButton.setFocusable(false);
        responseButton.addActionListener(this);
        panel.add(responseButton);

        this.add(panel);
        this.setModal(true);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == correspondenceButton) {
                JSONObject jobj = new JSONObject();
                jobj.put("command", "student list");
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if (xx) {
                    Message ms = new Message((ArrayList<HashMap>) x.get("studentlist"), id);
                }

            }
            if (e.getSource() == recordScoreButton) {
                recordGrades rg = new recordGrades();
            }
            if (e.getSource() == responseButton) {
                Objections ob = new Objections(0);
            }
        }catch (Exception ex){

        }
    }
}
