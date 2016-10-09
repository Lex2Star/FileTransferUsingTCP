package FileTransferServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class Server {
    private FileSender ft;
    private String filePath;

    public void act() {

        try {
            ServerSocket serverSock = new ServerSocket(5000);
            System.out.println("Started Sever...");

            while(true) {
                Socket sock = serverSock.accept();
                System.out.println("Connected Client...");

                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                String fileName = reader.readLine();
                System.out.println("./send/" + fileName);
                filePath = ("./send/" + fileName);

//                ft = new FileSender(filePath, sock);
//                ft.send();

                System.out.println("DisConnected Client...");
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
