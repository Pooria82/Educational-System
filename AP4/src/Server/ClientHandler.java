package Server;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread{
    Socket clieSocket;
    SQLight_DataBase sql;
    public ClientHandler(Socket clieSocket) {
        try{
            this.clieSocket = clieSocket;
            sql = new SQLight_DataBase();
            sql.construct();
        }
        catch (Exception ex){

        }
    }
    @Override
    public void run(){
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try{
            outputStream = new ObjectOutputStream(clieSocket.getOutputStream());
            inputStream = new ObjectInputStream(clieSocket.getInputStream());
        }
        catch (Exception ex){

        }
        while(clieSocket.isConnected()){
            try{
                JSONObject req = (JSONObject) inputStream.readObject();
                String command = (String) req.get("command");
                if(command.equals("login")){
                    JSONObject ans = new JSONObject();
                    String uname = (String) req.get("username");
                    String pass = (String) req.get("password");
                    if(sql.CheckUsername(uname)){
                        if(sql.CheckPassword(uname,pass)){
                            ans.put("valid",true);
                            ans.put("info",sql.getUser(uname,pass));
                        }
                        else {
                            ans.put("valid",false);
                        }
                    }
                    else {
                        ans.put("valid",false);
                    }
                    outputStream.writeObject(ans);
                }
                else if(command.equals("new field")){
                    JSONObject ans = new JSONObject();
                    String fname = (String) req.get("fieldname");
                    sql.addField(fname);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("teacher list")){
                    JSONObject ans = new JSONObject();
                    ans.put("teacherlist",sql.getTeachersList());
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("student list")){
                    JSONObject ans = new JSONObject();
                    ans.put("studentlist",sql.getStudentsList());
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("new teacher")){
                    JSONObject ans = new JSONObject();
                    String uname = (String) req.get("username");
                    String pass = (String) req.get("password");
                    String name = (String) req.get("name");
                    String field = (String) req.get("field");
                    if(sql.newTeacher(uname,pass,name,field)){
                        ans.put("valid",true);
                    }
                    else{
                        ans.put("valid",false);
                    }
                    outputStream.writeObject(ans);
                }
                else if(command.equals("new student")){
                    JSONObject ans = new JSONObject();
                    String uname = (String) req.get("username");
                    String pass = (String) req.get("password");
                    String name = (String) req.get("name");
                    String field = (String) req.get("field");
                    if(sql.newStudent(uname,pass,name,field)){
                        ans.put("valid",true);
                    }
                    else{
                        ans.put("valid",false);
                    }
                    outputStream.writeObject(ans);
                }
                else if(command.equals("set modirgroup")){
                    JSONObject ans = new JSONObject();
                    int id = (int) req.get("id");
                    String field = (String) req.get("field");
                    sql.setModirGroup(id,field);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("vahed list")){
                    JSONObject ans = new JSONObject();
                    String field = (String) req.get("field");
                    ans.put("vahedlist",sql.getVahedList(field));
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("new vahed")){
                    JSONObject ans = new JSONObject();
                    HashMap data = (HashMap) req.get("data");
                    sql.newLesson(data);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("remove vahed")){
                    JSONObject ans = new JSONObject();
                    int id = (int) req.get("id");
                    sql.removeLesson(id);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("edit vahed")){//taghiir zarfiat
                    JSONObject ans = new JSONObject();
                    int id = (int) req.get("id");
                    int vahedlimit = (int) req.get("vahedlimit");
                    sql.editLesson(id,vahedlimit);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("open close select")){
                    JSONObject ans = new JSONObject();
                    int oc = (int) req.get("oc");
                    String field = (String) req.get("field");
                    sql.openCloseSelect(field,oc);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("set score final")){
                    JSONObject ans = new JSONObject();
                    int isfinal = (int) req.get("final");
                    int lessonid = (int) req.get("lessonid");
                    sql.setScoreFinal(lessonid,isfinal);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("set score")){
                    JSONObject ans = new JSONObject();
                    String score = (String) req.get("score");
                    int lessonid = (int) req.get("lessonid");
                    int studentid = (int) req.get("studentid");
                    sql.setScore(studentid,lessonid,score);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("answered obj")){
                    JSONObject ans = new JSONObject();
                    int isobj = (int) req.get("obj");//0 or 1
                    int lessonid = (int) req.get("lessonid");
                    int studentid = (int) req.get("studentid");
                    sql.setObjection(lessonid,studentid,isobj);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("send msg")){
                    JSONObject ans = new JSONObject();
                    int fromid = (int) req.get("senderid");
                    int toid = (int) req.get("receiverid");
                    String msg = (String) req.get("msg");
                    sql.sendMSG(fromid,toid,msg);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("get msg")){
                    JSONObject ans = new JSONObject();
                    int id1 = (int) req.get("firstid");
                    int id2 = (int) req.get("secondid");
                    ans.put("messages",sql.getMSG(id1,id2));
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("send obj")){
                    JSONObject ans = new JSONObject();
                    int isobj = (int) req.get("obj");//0 or 1
                    int lessonid = (int) req.get("lessonid");
                    int studentid = (int) req.get("studentid");
                    if(isobj == 1){
                        sql.sendMSG(studentid,sql.getTeacherIdBylesson(lessonid),"id : "+studentid+" has send an objection on id : " + lessonid);
                    }
                    sql.setObjection(lessonid,studentid,isobj);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("send public msg")){
                    JSONObject ans = new JSONObject();
                    String msg = (String) req.get("msg");
                    sql.sendPublicMSG(msg);
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("get public msg")){
                    JSONObject ans = new JSONObject();
                    ans.put("public msg",sql.getPublicMSG());
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("get report")){
                    JSONObject ans = new JSONObject();
                    int id = (int) req.get("id");
                    ans.put("report",sql.getReport(id));
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("new selection")){
                    JSONObject ans = new JSONObject();
                    ArrayList<HashMap> arr = (ArrayList<HashMap>) req.get("new selection");
                    for(HashMap i : arr){
                        sql.newSelectVahed((int)i.get("lessonid"),(int)i.get("userid"));
                    }
                    ans.put("valid",true);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("get selection list")){
                    JSONObject ans = new JSONObject();
                    int id = (int) req.get("id");
                    //System.out.println("[[[");
                    ans.put("selection list" , sql.getSelectionList(id));
                    ans.put("valid",true);
                    //System.out.println(ans);
                    outputStream.writeObject(ans);
                }
                else if(command.equals("get open close select")){
                    JSONObject ans = new JSONObject();
                    //int oc = (int) req.get("oc");
                    String field = (String) req.get("field");
                    if(sql.getopenCloseSelect(field)){
                        ans.put("valid",true);
                    }
                    else{
                        ans.put("valid",false);
                    }
                    outputStream.writeObject(ans);
                }
                else if(command.equals("exit")){
                    break;
                }
                outputStream.flush();
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }
}
