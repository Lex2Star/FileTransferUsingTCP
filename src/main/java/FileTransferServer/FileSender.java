package FileTransferServer;

import java.io.*;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class FileSender {

    private File file;
    private Socket socket;

    public FileSender(String fileName, Socket socket) {
        this.file = new File(fileName);
        this.socket  = socket;
    }

    public void send() {

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            OutputStream os = socket.getOutputStream();

            byte[] contents;
            long fileLength = file.length();
            long current = 0;

            while (current != fileLength) {
                int size = 10000;
                if (fileLength - current >= size)
                    current += size;
                else {
                    size = (int) (fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size);
                os.write(contents);
                System.out.print("Sending file ... " + (current * 100) / fileLength + "% complete!");
            }

            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
