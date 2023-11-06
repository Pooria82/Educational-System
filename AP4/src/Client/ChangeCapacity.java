package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.simple.JSONObject;
public class ChangeCapacity  extends JDialog implements ActionListener {
    private JTextField UnitID;
    private JTextField newCapacityField;
    JButton submitButton;


    ChangeCapacity(){


        setTitle("Edit Unit Capacity");
        setSize(400, 200);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(4, 1));
        JLabel currentCodeLabel = new JLabel("Unit ID:");
        UnitID = new JTextField();
        JLabel capacityLabel = new JLabel("New Unit Capacity:");
        newCapacityField = new JTextField();

        fieldsPanel.add(currentCodeLabel);
        fieldsPanel.add(UnitID);
        fieldsPanel.add(capacityLabel);
        fieldsPanel.add(newCapacityField);

        panel.add(fieldsPanel, BorderLayout.CENTER);


        submitButton = new JButton("SUBMIT");

        submitButton.addActionListener(this);
        submitButton.setFocusable(false);


        panel.add(submitButton, BorderLayout.SOUTH);

        add(panel);
        setModal(true);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == submitButton){
                if(!UnitID.getText().equals("") && !newCapacityField.getText().equals("")) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("command", "edit vahed");
                    jobj.put("vahedlimit", Integer.parseInt(newCapacityField.getText()));
                    jobj.put("id",Integer.parseInt(UnitID.getText()));
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
