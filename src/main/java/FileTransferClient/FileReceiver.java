package FileTransferClient;

import java.io.*;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class FileReceiver {

    private String fileName;
    private Socket socket;

    public FileReceiver(String fileName, Socket socket) {
        this.fileName = fileName;
        this.socket = socket;
    }

    public void receive() {
        try {
            byte[] contents = new byte[10000];

            FileOutputStream fos = new FileOutputStream("./receive/test.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream is = socket.getInputStream();

            int bytesRead = 0;

            while ((bytesRead = is.read(contents)) != -1)
                bos.write(contents, 0, bytesRead);

            bos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
