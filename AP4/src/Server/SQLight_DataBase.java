package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLight_DataBase {
    public Connection handle;
    public void construct() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        handle = DriverManager.getConnection("jdbc:sqlite:data.db");
    }
    public void createSQL() throws ClassNotFoundException,SQLException {
        Class.forName("org.sqlite.JDBC");
        handle = DriverManager.getConnection("jdbc:sqlite:data.db");
        String query = "";
        PreparedStatement prep;
        //--user table
        query = "CREATE TABLE IF NOT EXISTS users"
                + "(id INTEGER PRIMARY KEY NOT NULL,username TEXT,password TEXT,name TEXT ,role TEXT , field TEXT)";
        prep = handle.prepareStatement(query);
        prep.executeUpdate();
        //---admin setup
        query = "INSERT INTO users (username , password , role,name) VALUES ('Modir' , '12345678' , 'modir','I am modir')";
        prep = handle.prepareStatement(query);
        prep.executeUpdate();
        //--- lessons table
        query = "CREATE TABLE IF NOT EXISTS lessons"
                + "(id INTEGER PRIMARY KEY NOT NULL,name TEXT,vahed INT , vahedlimit INT,teacherid INT,field TEXT)";
        prep = handle.prepareStatement(query);
        prep.executeUpdate();
        //--- scores table
        query = "CREATE TABLE IF NOT EXISTS scores"
                + "(id INTEGER PRIMARY KEY NOT NULL,lessonid INT,userid INT , score TEXT, final INT,  obj INT)";
        prep = handle.prepareStatement(query);
        prep.executeUpdate();
        query = "CREATE TABLE IF NOT EXISTS messages"
                + "(id INTEGER PRIMARY KEY NOT NULL,senderid INT,receiverid INT , msg TEXT)";
        prep = handle.prepareStatement(query);
        prep.executeUpdate();
        query = "CREATE TABLE IF NOT EXISTS publicmsg"
                + "(id INTEGER PRIMARY KEY NOT NULL, msg TEXT)";
        prep = handle.prepareStatement(query);
        prep.executeUpdate();
        query = "CREATE TABLE IF NOT EXISTS fields"
                + "(id INTEGER PRIMARY KEY NOT NULL, name TEXT,modirid INT,oc INT)";
        prep = handle.prepareStatement(query);
        prep.executeUpdate();
    }
    public  boolean CheckUsername(String username) {
        // TODO Auto-generated method stub
        try {
            String query = "";
            PreparedStatement prep;
            query = "SELECT * FROM users WHERE username = ?";
            prep = handle.prepareStatement(query);
            prep.setString(1, username);
            ResultSet rs = prep.executeQuery();
            if(!rs.isBeforeFirst()) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    public  boolean CheckPassword(String username, String password) {
        // TODO Auto-generated method stub
        try {
            String query = "";
            PreparedStatement prep;
            query = "SELECT * FROM users WHERE username = ? and password = ?";
            prep = handle.prepareStatement(query);
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            if(!rs.isBeforeFirst()) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    public HashMap getUser(String username , String password) throws ClassNotFoundException,SQLException {
        String query = "";
        PreparedStatement prep;
        HashMap ans = new HashMap();
        query = "SELECT * FROM users WHERE username = ? and password = ?";
        prep = handle.prepareStatement(query);
        prep.setString(1, username);
        prep.setString(2, password);
        ResultSet rs = prep.executeQuery();
        if(!rs.isBeforeFirst()) {
            return ans;
        }
        while(rs.next()) {
            ans.put("id",rs.getInt("id"));
            ans.put("username",rs.getString("username"));
            ans.put("password",rs.getString("password"));
            ans.put("name",rs.getString("name"));
            ans.put("role",rs.getString("role"));
            ans.put("field",rs.getString("field"));
            return ans;
        }
        return ans;
    }
    public HashMap getUser(int id) throws ClassNotFoundException,SQLException {
        String query = "";
        PreparedStatement prep;
        HashMap ans = new HashMap();
        query = "SELECT * FROM users WHERE id = ?";
        prep = handle.prepareStatement(query);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        if(!rs.isBeforeFirst()) {
            return ans;
        }
        while(rs.next()) {
            ans.put("id",rs.getInt("id"));
            ans.put("username",rs.getString("username"));
            ans.put("password",rs.getString("password"));
            ans.put("name",rs.getString("name"));
            ans.put("role",rs.getString("role"));
            ans.put("field",rs.getString("field"));
            return ans;
        }
        return ans;
    }
    public void addField(String fieldName) throws SQLException,ClassNotFoundException{
        // TODO Auto-generated method stub
        String query = "";
        PreparedStatement prep;
        query = "INSERT INTO fields (name,oc) VALUES (?,0)";
        prep = handle.prepareStatement(query);
        prep.setString(1, fieldName);
        prep.executeUpdate();
    }
    public ArrayList<HashMap> getTeachersList() throws SQLException,ClassNotFoundException{
        // TODO Auto-generated method
        ArrayList<HashMap> ans = new ArrayList<>();
        String query = "";
        PreparedStatement prep;
        query = "SELECT id,name,field,role FROM users WHERE role = 'teacher' or role = 'modirgroup' ";
        prep = handle.prepareStatement(query);
        ResultSet res = prep.executeQuery();

        while (res.next()) {
            HashMap temp = new HashMap();
            temp.put("id",res.getInt("id"));
            temp.put("name",res.getString("name"));
            temp.put("field",res.getString("field"));
            temp.put("role",res.getString("role"));
            ans.add(new HashMap(temp));
        }

        return ans;
    }
    public ArrayList<HashMap> getStudentsList() throws SQLException,ClassNotFoundException{
        // TODO Auto-generated method
        ArrayList<HashMap> ans = new ArrayList<>();
        String query = "";
        PreparedStatement prep;
        query = "SELECT id,name,field,role FROM users WHERE role = 'student' ";
        prep = handle.prepareStatement(query);
        ResultSet res = prep.executeQuery();

        while (res.next()) {
            HashMap temp = new HashMap();
            temp.put("id",res.getInt("id"));
            temp.put("name",res.getString("name"));
            temp.put("field",res.getString("field"));
            temp.put("role",res.getString("role"));
            ans.add(new HashMap(temp));
        }

        return ans;
    }
    public boolean newTeacher(String user, String pass,String name,String field) throws ClassNotFoundException,SQLException {
        String query = "";
        PreparedStatement prep;
        query = "SELECT * FROM users WHERE username = ?";
        prep = handle.prepareStatement(query);
        prep.setString(1, user);
        ResultSet res = prep.executeQuery();
        if(!res.isBeforeFirst()) {
            query = "INSERT INTO users (username,password,name,field,role) Values(?,?,?,?,'teacher')";
            prep = handle.prepareStatement(query);
            prep.setString(1, user);
            prep.setString(2, pass);
            prep.setString(3, name);
            prep.setString(4, field);
            prep.execute();
            return true;
        }
        else {
            return false;
        }
    }
    public boolean newStudent(String user, String pass,String name,String field) throws ClassNotFoundException,SQLException {
        String query = "";
        PreparedStatement prep;
        query = "SELECT * FROM users WHERE username = ?";
        prep = handle.prepareStatement(query);
        prep.setString(1, user);
        ResultSet res = prep.executeQuery();
        if(!res.isBeforeFirst()) {
            query = "INSERT INTO users (username,password,name,field,role) Values(?,?,?,?,'student')";
            prep = handle.prepareStatement(query);
            prep.setString(1, user);
            prep.setString(2, pass);
            prep.setString(3, name);
            prep.setString(4, field);
            prep.execute();
            return true;
        }
        else {
            return false;
        }
    }
    public void setModirGroup(int id,String field) throws ClassNotFoundException,SQLException {
        String query = "";
        PreparedStatement prep;
        query = "UPDATE users SET role = 'teacher' WHERE role = 'modirgroup' and field = ?";
        prep = handle.prepareStatement(query);
        prep.setString(1,field);
        prep.executeUpdate();
        query = "UPDATE users SET role = 'modirgroup' WHERE id = ?";
        prep = handle.prepareStatement(query);
        prep.setInt(1,id);
        prep.executeUpdate();
        query = "UPDATE fields SET modirid = ? WHERE name = ?";
        prep = handle.prepareStatement(query);
        prep.setInt(1,id);
        prep.setString(2,field);
        prep.executeUpdate();

    }
    public ArrayList<HashMap> getVahedList(String field) throws SQLException,ClassNotFoundException{
        // TODO Auto-generated method
        ArrayList<HashMap> ans = new ArrayList<>();
        String query = "";
        PreparedStatement prep;
        query = "SELECT * FROM lessons WHERE field = ? ";
        prep = handle.prepareStatement(query);
        prep.setString(1,field);
        ResultSet res = prep.executeQuery();

        while (res.next()) {
            HashMap temp = new HashMap();
            temp.put("id",res.getInt("id"));
            temp.put("name",res.getString("name"));
            temp.put("field",res.getString("field"));
            temp.put("vahed",res.getInt("vahed"));
            temp.put("vahedlimit",res.getInt("vahedlimit"));
            temp.put("teacherid",res.getInt("teacherid"));
            ans.add(new HashMap(temp));
        }

        return ans;
    }
    public void newLesson(HashMap data) throws SQLException,ClassNotFoundException {
        //----
        String query = "";
        PreparedStatement prep;
        query = "INSERT INTO lessons (name,vahed,vahedlimit,teacherid,field) VALUES (?,?,?,?,?)";
        prep = handle.prepareStatement(query);
        prep.setString(1, (String) data.get("name"));
        prep.setInt(2,(int) data.get("vahed"));
        prep.setInt(3, (int) data.get("vahedlimit"));
        prep.setInt(4, (int) data.get("teacherid"));
        prep.setString(5, (String) data.get("field"));
        prep.executeUpdate();
    }
    public void removeLesson(int id) throws SQLException,ClassNotFoundException {
        //-----
        String query = "";
        PreparedStatement prep;
        query = "DELETE FROM lessons WHERE id = ?";
        prep = handle.prepareStatement(query);
        prep.setInt(1, id);
        prep.executeUpdate();
    }
    public void editLesson(int id,int vahedlimit)  {
        try {
            String query = "";
            PreparedStatement prep;
            query = "UPDATE lessons SET vahedlimit = ? WHERE id = ?";
            prep = handle.prepareStatement(query);
            prep.setInt(1, vahedlimit);
            prep.setInt(2, id);
            prep.executeUpdate();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void openCloseSelect(String field,int oc) throws SQLException,ClassNotFoundException {//0 for close 1 for open
        String query = "";
        PreparedStatement prep;
        query = "UPDATE fields SET oc = ? WHERE name = ?";
        prep = handle.prepareStatement(query);
        prep.setInt(1, oc);
        prep.setString(2, field);
        prep.executeUpdate();
    }
    public boolean getopenCloseSelect(String field) throws SQLException,ClassNotFoundException {//0 for close 1 for open
        String query = "";
        PreparedStatement prep;
        query = "SELECT * FROM fields WHERE name = ?";
        prep = handle.prepareStatement(query);
        prep.setString(1, field);
        ResultSet res = prep.executeQuery();
        if(!res.isBeforeFirst()) {
            return false;
        }
        else {
           boolean oc = false;
           while (res.next()){
               if(res.getInt("oc") == 1){
                   oc = true;
                   break;
               }
           }
           return oc;
        }
    }
    public void setScoreFinal(int lessonid,int isfinal) throws SQLException,ClassNotFoundException {//0 for close 1 for open
        String query = "";
        PreparedStatement prep;
        query = "UPDATE scores SET final = ? WHERE lessonid = ? ";
        prep = handle.prepareStatement(query);
        prep.setInt(1, isfinal);
        prep.setInt(2, lessonid);
        prep.executeUpdate();
    }

    public void setScore(int studentid, int lessonid, String score) throws SQLException,ClassNotFoundException{
        String query = "";
        PreparedStatement prep;
        query = "UPDATE scores SET score = ? WHERE lessonid = ? and userid = ?";
        prep = handle.prepareStatement(query);
        prep.setString(1, score);
        prep.setInt(3, studentid);
        prep.setInt(2, lessonid);

        prep.executeUpdate();
    }

    public void setObjection(int lessonid, int studentid, int isobj) throws SQLException,ClassNotFoundException{//0 or 1
        String query = "";
        PreparedStatement prep;
        query = "UPDATE scores SET obj = ? WHERE lessonid = ? and userid = ?";
        prep = handle.prepareStatement(query);
        prep.setInt(1, isobj);
        prep.setInt(3, studentid);
        prep.setInt(2, lessonid);

        prep.executeUpdate();
    }
    public void sendMSG(int fromid, int toid, String msg) throws SQLException,ClassNotFoundException{
        String query = "";
        PreparedStatement prep;
        query = "INSERT INTO messages (senderid , receiverid , msg) VALUES (? , ? , ?)";
        prep = handle.prepareStatement(query);
        prep.setInt(1, fromid);
        prep.setInt(2, toid);
        prep.setString(3, msg);

        prep.executeUpdate();
    }
    public void sendPublicMSG(String msg) throws SQLException,ClassNotFoundException{
        String query = "";
        PreparedStatement prep;
        query = "INSERT INTO publicmsg (msg) VALUES (?)";
        prep = handle.prepareStatement(query);
        prep.setString(1, msg);
        prep.executeUpdate();
    }
    public ArrayList<HashMap> getMSG(int id1, int id2) throws SQLException,ClassNotFoundException{
        String query = "";
        ArrayList<HashMap> ans = new ArrayList<>();
        PreparedStatement prep;
        query = "SELECT * FROM messages WHERE (receiverid = ? and senderid = ?) or (receiverid = ? and senderid = ?)";
        prep = handle.prepareStatement(query);
        prep.setInt(1, id1);
        prep.setInt(2, id2);
        prep.setInt(3, id2);
        prep.setInt(4, id1);
        ResultSet res = prep.executeQuery();
        while (res.next()) {
            HashMap temp = new HashMap();
            temp.put("senderid",res.getInt("senderid"));
            temp.put("receiverid",res.getInt("receiverid"));
            temp.put("msg",res.getString("msg"));
            ans.add(new HashMap(temp));
        }
        return ans;
    }
    public ArrayList<String> getPublicMSG() throws SQLException,ClassNotFoundException{
        String query = "";
        ArrayList<String> ans = new ArrayList<>();
        PreparedStatement prep;
        query = "SELECT * FROM publicmsg";
        prep = handle.prepareStatement(query);
        ResultSet res = prep.executeQuery();
        while (res.next()) {
           ans.add(res.getString("msg"));
        }
        return ans;
    }
    public int getTeacherIdBylesson(int lessonid) throws SQLException,ClassNotFoundException{
        // TODO Auto-generated method
        int ans = 0;
        String query = "";
        PreparedStatement prep;
        query = "SELECT teacherid FROM lessons WHERE id = ? ";
        prep = handle.prepareStatement(query);
        prep.setInt(1,lessonid);
        ResultSet res = prep.executeQuery();
        while (res.next()) {
            ans = res.getInt("teacherid");
            return ans;
        }
        return ans;

    }

    public HashMap getReport(int userid)  throws SQLException,ClassNotFoundException{
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        HashMap ans = new HashMap();
        String[] head = {"lesson" , "vahed", "teacher"  ,"score"};
        for(String item : head) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(item);
            table.add(new ArrayList<>(temp));
        }
        String query = "";
        PreparedStatement prep;
        query = "SELECT users.id, users.name,lessons.name,lessons.vahed, lessons.teacherid, scores.score,lessons.id FROM ((scores INNER JOIN users ON scores.userid = users.id) INNER JOIN lessons ON scores.lessonid = lessons.id) WHERE scores.userid = ?";
        prep = handle.prepareStatement(query);

        prep.setInt(1, userid);

        ResultSet res = prep.executeQuery();
        if(!res.isBeforeFirst()) {
            return ans;
        }
        String title = "";
        while(res.next()) {
            title = "" + res.getInt(1) +" - " +res.getString(2);
            table.get(0).add(res.getString(3)+"("+res.getInt(7)+")");
            table.get(1).add(String.valueOf(res.getInt(4)));
            table.get(2).add(String.valueOf(res.getInt(5)));
            table.get(3).add(res.getString(6));

        }
        ans.put("title" , title);
        ans.put("table" , table);
        return ans;
    }
    public void newSelectVahed(int lessonid,int userid) throws SQLException,ClassNotFoundException {
        //----
        String query = "";
        PreparedStatement prep;
        query = "INSERT INTO scores (lessonid,userid,score,final,obj) VALUES (?,?,'-1',0,0)";
        prep = handle.prepareStatement(query);
        prep.setInt(1, lessonid);
        prep.setInt(2, userid);
        prep.executeUpdate();
    }

    public ArrayList<HashMap> getSelectionList(int id) {
        try {
            String query = "";
            ArrayList<HashMap> ans = new ArrayList<>();
            ArrayList<Integer> lid = new ArrayList<>();
            PreparedStatement prep;
            query = "SELECT * FROM scores WHERE userid = ? ";
            prep = handle.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();
            if (res.isBeforeFirst()) {
                while (res.next()) {
                    if (Double.parseDouble(res.getString("score")) >= 10) {
                        if (!lid.contains(res.getInt("lessonid"))) {
                            lid.add(res.getInt("lessonid"));
                        }
                    }
                }
            }
            String field = (String) getUser(id).get("field");
            query = "SELECT * FROM lessons WHERE field = ? ";
            prep = handle.prepareStatement(query);
            prep.setString(1, field);
            ResultSet res2 = prep.executeQuery();
            while (res2.next()) {
                if (!lid.contains(res2.getInt("id"))) {
                    HashMap temp = new HashMap();
                    temp.put("id", res2.getInt("id"));
                    temp.put("name", res2.getString("name"));
                    temp.put("field", res2.getString("field"));
                    temp.put("vahed", res2.getInt("vahed"));
                    temp.put("vahedlimit", res2.getInt("vahedlimit"));
                    temp.put("teacherid", res2.getInt("teacherid"));
                    ans.add(new HashMap(temp));
                }
            }
            return ans;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }
}
