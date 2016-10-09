package FileTransferClient;

import java.io.*;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class FileReceiver {

    private String fileName;
    private Socket socket;

    public FileReceiver(String fileName, Socket msocket) {
        this.fileName = fileName;
        while(msocket.isConnected()) {
            System.out.print("conneting..");
            try {
                this.socket = new Socket("localhost", 5001);
                if(this.socket.isConnected()) {
                    System.out.println("Conneted File Transfer Server");
                    break;
                }
            } catch (IOException e) {

            }
        }
    }

    public void receive() {
        try {
            byte[] contents = new byte[10000];
            int bytesRead;

            FileOutputStream fos = new FileOutputStream("./receive/" + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream is = socket.getInputStream();

            while ((bytesRead = is.read(contents)) != -1)
                bos.write(contents, 0, bytesRead);

            bos.flush();
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
