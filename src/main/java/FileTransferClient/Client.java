package FileTransferClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Lex2Star
 */
public class Client {
    private String fileName;
    private FileReceiver fr;
    private String ip;
    private int port;
    private int frPort;

    /*
    * Client 개체 생성자 오버로딩
    * 기본 제어 포트 : 5000
    * 기본 파일전송 포트 : 5001
    * 개체 생성시 IP와 포트 번호를 부여하여 조정가능
    */
    public Client(String fileName) {
        this.ip = "localhost";
        this.port = 5000;
        this.frPort = 5001;
        this.fileName = fileName;
    }

    public Client(String ip,int port, int frPort, String fileName) {
        this.ip = ip;
        this.port = port;
        this.frPort = frPort;
        this.fileName = fileName;
    }

    public void act() {
        try {
            /*
             * 제어 서버 접속
             */
            Socket socket = new Socket(ip, port);
            while (!socket.isConnected())
                ;
            System.out.println("Connected server...");

            /*
             * 제어 서버로 파일명 전송
             */
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(fileName);
            writer.flush();
            System.out.println(fileName);

            /*
             * 파일을 전달받을 클라이언트를 위한 FileReceiver 개체 생성
             */
            fr = new FileReceiver(fileName, ip, frPort, socket);
            fr.receive();

            /*
             * 클라이언트와 제어 서버 소켓 연결끊기
             */
            writer.close();
            socket.close();
            System.out.println("Disconnect server...");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
