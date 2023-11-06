package Client;


import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ModirGrooh extends JDialog implements ActionListener {
    private JButton toggleButton;
    private JButton showListButton;
    private JButton addUnitButton;
    private JButton removeUnitButton;
    private JButton changeCapacityButton;
    private JButton finalizeGradesButton , moreAction;
    String field;
    HashMap info;
    public ModirGrooh(HashMap info) {
        try {
            this.info = info;
            this.setSize(400, 400);
            this.setLocationRelativeTo(null);
            field = (String) info.get("field");
            toggleButton = new JButton("Off");
            toggleButton.setFocusable(false);
            toggleButton.addActionListener(this);
            JSONObject jobj2 = new JSONObject();
            jobj2.put("command", "get open close select");
            jobj2.put("field", field);
            Login.outputStream.writeObject(jobj2);
            Login.outputStream.flush();
            JSONObject x2 = (JSONObject) Login.inputStream.readObject();
            boolean xx2 = (Boolean) x2.get("valid");
            if (xx2) {
                toggleButton.setText("On");
            }
            JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));

            // On/Off Switch
            JLabel switchLabel = new JLabel("Open and Close Unit Selection:");


            // Add labels
            JLabel idLabel = new JLabel("  ID : " + info.get("id"));
            JLabel usernameLabel = new JLabel("  Username : " + (String) info.get("username"));
            JLabel nameLabel = new JLabel("  Name : " + (String) info.get("name"));
            JLabel roleLabel = new JLabel("  Role : " + (String) info.get("role"));

            panel.add(idLabel);
            panel.add(new JLabel()); // Empty cell
            panel.add(usernameLabel);
            panel.add(new JLabel()); // Empty cell
            panel.add(nameLabel);
            panel.add(new JLabel()); // Empty cell
            panel.add(roleLabel);
            panel.add(new JLabel()); // Empty cell
            panel.add(switchLabel);
            panel.add(toggleButton);

            // Show List of Units Button
            showListButton = new JButton("Show the List of Units");
            showListButton.setFocusable(false);
            showListButton.addActionListener(this);
            panel.add(showListButton);
            moreAction = new JButton("more Actions");
            moreAction.setFocusable(false);
            moreAction.addActionListener(this);
            panel.add(moreAction); // Empty cell

            // Add Unit Button
            addUnitButton = new JButton("Add Unit");
            addUnitButton.setFocusable(false);
            addUnitButton.addActionListener(this);
            panel.add(addUnitButton);

            // Remove Unit Button
            removeUnitButton = new JButton("Remove Unit");
            removeUnitButton.setFocusable(false);
            removeUnitButton.addActionListener(this);
            panel.add(removeUnitButton);

            // Change Unit Capacity Button
            changeCapacityButton = new JButton("Change the Unit Capacity");
            changeCapacityButton.setFocusable(false);
            changeCapacityButton.addActionListener(this);
            panel.add(changeCapacityButton);

            // Finalize Grades Button
            finalizeGradesButton = new JButton("Finalize Grades");
            finalizeGradesButton.setFocusable(false);
            finalizeGradesButton.addActionListener(this);
            panel.add(finalizeGradesButton);

            this.add(panel);
            this.setModal(true);
            this.setVisible(true);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == moreAction){
                Professor prf = new Professor(info);
            }
            if(e.getSource() == addUnitButton){
                AddUnit au = new AddUnit();
            }
            if(e.getSource() == removeUnitButton){
                RemoveUnit ru = new RemoveUnit();
            }
            if(e.getSource() == showListButton){
                JSONObject jobj = new JSONObject();
                jobj.put("command" , "vahed list");
                jobj.put("field",field);
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if(xx){
                    ListDialog2 ld = new ListDialog2((ArrayList<HashMap>) x.get("vahedlist"));
                }
            }
            if(e.getSource() == changeCapacityButton){
                ChangeCapacity cc = new ChangeCapacity();
            }
            if(e.getSource() == finalizeGradesButton){
                String ans = JOptionPane.showInputDialog(null,"lesson id :");
                if(ans != null && !ans.equals("")){
                    int lid = Integer.parseInt(ans);
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "set score final");
                    jobj.put("lessonid",lid);
                    jobj.put("final",1);
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if(xx){
                        //...
                    }
                }
            }
            if(e.getSource() == toggleButton){
                if (toggleButton.getText().equals("Off")) {
                    toggleButton.setText("On");
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "open close select");
                    jobj.put("oc",0);
                    jobj.put("field",field);
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if(xx){

                    }
                }
                else if (toggleButton.getText().equals("On")) {
                    toggleButton.setText("Off");
                    JSONObject jobj = new JSONObject();
                    jobj.put("command" , "open close select");
                    jobj.put("oc",1);
                    jobj.put("field",field);
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if(xx){

                    }
                }
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
