package FileTransferClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class Client {

    String fileName;
    FileReceiver fr;

    public Client(String fileName) {
        this.fileName = fileName;
    }

    public void act() {

        try {
            Socket s = new Socket("localhost", 5000);
            System.out.println("Connected server...");

            PrintWriter writer = new PrintWriter(s.getOutputStream());

            writer.println(fileName);
            System.out.println(fileName);

//            fr = new FileReceiver(fileName, s);
//            fr.receive();

            System.out.println("Received File...");
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
