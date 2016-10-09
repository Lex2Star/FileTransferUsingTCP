package FileTransferServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class Server {

    private FileSender fs;
    private String filePath;
    private int port;

    public Server() {

        this.port = 5000;
    }

    public Server(int port) {

        this.port = port;
    }

    public void act() {

        try {

            ServerSocket serverSock = new ServerSocket(port);
            System.out.println("Started Sever...");

            while(true) {

                Socket sock = serverSock.accept();
                System.out.println("Connected Client...");

                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                String fileName = null;
                while ((fileName = reader.readLine()) == null)
                    ;
                System.out.println("./send/" + fileName);
                filePath = ("./send/" + fileName);

                fs = new FileSender(filePath, sock);
                fs.send();

                System.out.println("DisConnected Client...");
                sock.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
