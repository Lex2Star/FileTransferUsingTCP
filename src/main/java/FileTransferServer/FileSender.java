package FileTransferServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lex2Star
 *  파일 전송 서버를 위한 FileSender 클래스
 */
public class FileSender {
    private File file;
    private Socket socket;
    private ServerSocket serverSocket;
    private int port;

    /*
     * FileSender 개체 생성 시
     * 파일 개체 생성한 후
     * 파일 전송 서버 구동
     */
    public FileSender(String fileName, int port) {
        this.file = new File(fileName);
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(5001);
            System.out.println("#Started Control Sever...");
            this.socket = serverSocket.accept();
            System.out.println("#Connected Client...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));   //파일 입력 버퍼 생성
            OutputStream os = socket.getOutputStream();                                     //출력 스트림 생성

            byte[] contents;
            long fileLength = file.length();
            long current = 0;

            /*
             * 보낼 파일을 파일 입력 버퍼에 작성 후
             * 출력 스트림을 통해 파일 전송
             */
            System.out.println("#Start...");
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
            }
            System.out.println("#Complete...");
            os.flush();
            os.close();
            bis.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
