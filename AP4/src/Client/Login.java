package Client;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;
    private JButton loginButton,seePublicmsg;

    public static Socket socket;
    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;

    public Login() {
        try {
            socket = new Socket("localhost",7070);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            // Set up the frame
            setTitle("Login");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new FlowLayout());
            setLocationRelativeTo(null);
            JPanel panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
            JPanel panel = new JPanel(new GridLayout(4, 2));

            // Add the username label and field
            JLabel usernameLabel = new JLabel("Username:");
            panel.add(usernameLabel);
            usernameField = new JTextField();
            panel.add(usernameField);

            // Add the password label, field, and show password checkbox
            JLabel passwordLabel = new JLabel("Password:");
            panel.add(passwordLabel);
            passwordField = new JPasswordField();
            panel.add(passwordField);
            showPasswordCheckbox = new JCheckBox("Show password");
            showPasswordCheckbox.addActionListener(this);
            panel.add(new JLabel(""));
            panel.add(showPasswordCheckbox);

            // Add the login button
            loginButton = new JButton("Login");
            loginButton.setFocusable(false);
            loginButton.addActionListener(this);
            seePublicmsg = new JButton("public messages");
            seePublicmsg.setFocusable(false);
            seePublicmsg.addActionListener(this);
            panel.add(new JLabel(""));
            panel.add(loginButton);
            //this.pack();
            panel2.add(panel);
            panel2.add(seePublicmsg);
            add(panel2);
            this.pack();
            setVisible(true);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (e.getSource() == loginButton) {
                JSONObject jobj = new JSONObject();
                jobj.put("command", "login");
                jobj.put("username", username);
                jobj.put("password", password);
                outputStream.writeObject(jobj);
                outputStream.flush();
                JSONObject ans = (JSONObject) inputStream.readObject();
                if((Boolean) ans.get("valid") == true){
                    HashMap response = (HashMap) ans.get("info");
                    switch ((String) response.get("role")){
                        case "modir":
                            Modir modir = new Modir(response);
                            break;
                        case "modirgroup":
                            ModirGrooh modirgroub = new ModirGrooh(response);
                            break;
                        case "student":
                            Student st = new Student(response);
                            break;
                        case "teacher":
                            Professor prf = new Professor(response);
                            break;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Login failed. Please try again.");
                }
            }
             if (e.getSource() == showPasswordCheckbox) {
                if (showPasswordCheckbox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                }
                else {
                    passwordField.setEchoChar('\u2022');
                }
            }
            if (e.getSource() == seePublicmsg) {
                JSONObject jobj = new JSONObject();
                jobj.put("command" , "get public msg");
                Login.outputStream.writeObject(jobj);
                Login.outputStream.flush();
                JSONObject x = (JSONObject) Login.inputStream.readObject();
                boolean xx = (Boolean) x.get("valid");
                if(xx){
                    ArrayList<String> pmsg = (ArrayList<String>) x.get("public msg");
                    String pmsgs = "<html>";
                    for(String i : pmsg){
                        pmsgs += (i + "<br/>");
                    }
                    pmsgs+="</html>";
                    ShowPublicMSG spm = new ShowPublicMSG(pmsgs);
                }
            }
        }catch (Exception ex){

        }
    }
}


