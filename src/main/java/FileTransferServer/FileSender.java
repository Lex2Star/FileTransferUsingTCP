package FileTransferServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class FileSender {

    private File file;
    private Socket socket;
    private ServerSocket serverSocket;

    public FileSender(String fileName, Socket socket) {
        this.file = new File(fileName);
        try {
            this.serverSocket = new ServerSocket(5001);
            this.socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                System.out.println("Sending file ... " + (current * 100) / fileLength + "% complete!");
            }

            os.flush();
            os.close();
            bis.close();
            fis.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
