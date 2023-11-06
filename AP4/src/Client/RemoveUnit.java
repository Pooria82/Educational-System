package Client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoveUnit extends JDialog implements ActionListener {
    private JLabel label;
    private JTextField textField;
    private JButton button;
    RemoveUnit(){
        setTitle("Remove Lesson");
        setSize(550, 150);
        setLocationRelativeTo(null);

        // Create components
        label = new JLabel("Enter the ID of the Lesson you want to remove:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        textField = new JTextField(30);
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        button = new JButton("Remove");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusable(false);
        button.addActionListener(this);

        // Add components to frame
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        this.add(panel);

        this.setModal(true);
        // Display the frame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == button){
                if(!textField.getText().equals("")) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("command", "remove vahed");
                    jobj.put("id",Integer.parseInt( textField.getText()));
                    Login.outputStream.writeObject(jobj);
                    Login.outputStream.flush();
                    JSONObject x = (JSONObject) Login.inputStream.readObject();
                    boolean xx = (Boolean) x.get("valid");
                    if (xx) {
                        //System.out.println("[[[");
                        this.dispose();
                    }
                }
            }
        }catch (Exception ex){

        }
    }
}
