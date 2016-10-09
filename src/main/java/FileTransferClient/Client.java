package FileTransferClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class Client {

    private String fileName;
    private FileReceiver fr;
    private String ip;
    private int port;

    public Client(String fileName) {
        this.ip = "localhost";
        this.port = 5000;
        this.fileName = fileName;
    }

    public Client(String ip,int port, String fileName) {
        this.ip = ip;
        this.port = port;
        this.fileName = fileName;
    }

    public void act() {

        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Connected server...");

            while (!socket.isConnected())
                ;
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(fileName);
            writer.flush();
            System.out.println(fileName);

            fr = new FileReceiver(fileName, socket);
            fr.receive();

            System.out.println("Received File...");
            writer.close();
            socket.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
