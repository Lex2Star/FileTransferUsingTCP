package FileTransferClient;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lex2Star
 */
public class FileReceiver {
    private String fileName;
    private Socket socket;
    private String ip;
    private int port;

    /*
     * FileReceiver 개체 생성 시
     * 제어 서버 연결이 보장되고 있는 동안
     * 파일 전송 서버와 연결
     */
    public FileReceiver(String fileName, String ip, int port, Socket msocket) {
        this.fileName = fileName;
        this.ip = ip;
        this.port = port;
        while(msocket.isConnected()) {
            System.out.print("conneting..");
            try {
                this.socket = new Socket(ip, 5001);
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

            FileOutputStream fos = new FileOutputStream("./receive/" + fileName);   // 파일 출력 스트림 생성
            BufferedOutputStream bos = new BufferedOutputStream(fos);               // 출력 스트림 버퍼 생성
            InputStream is = socket.getInputStream();                               // 입력 스트림 생성

            /*
             * 입력 스트림을 통해 파일을 읽어들이고
             * 출력 스트림 버퍼를 통해 파일 작성
             */
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
