package Server;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            SQLight_DataBase sql = new SQLight_DataBase();
            File file = new File("data.db");
            if(!file.exists()) {
                sql.createSQL();
            }
            else {
                sql.construct();
            }
            ServerSocket serverSocket = new ServerSocket(7070);
            while(true){
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        }
        catch (Exception e) {
            // throw new RuntimeException(e);
        }
    }
}