package FileTransferServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lex2Star
 * 제어 서버를 위한 Sever 클래스
 */
public class Server {
    private FileSender fs;
    private String filePath;
    private int port;
    private int fsPort;

    /*
     * Server 개체 생성자 오버로딩
     * 기본 제어 포트 : 5000
     * 기본 파일전송 포트 : 5001
     * 개체 생성시 포트 번호를 부여하여 조정가능
     */
    public Server() {
        this.port = 5000;
        this.fsPort = 5001;
    }

    public Server(int port, int fsPort) {
        this.port = port;
        this.fsPort = fsPort;
    }

    public void act() {
        try {
            /*
            * 제어 서버 소켓 구동
            */
            ServerSocket serverSock = new ServerSocket(port);
            System.out.println("Started Control Sever...");

            while(true) {
                /*
                 * 클라이언트 접속할 때 까지 대기
                 */
                Socket sock = serverSock.accept();
                System.out.println("Connected Client...");

                /*
                 * 클라이언트가 보내올 파일명 받기
                 */
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String fileName = null;
                while ((fileName = reader.readLine()) == null)
                    ;
                System.out.println("./send/" + fileName);
                filePath = ("./send/" + fileName);

                /*
                 * 파일 전송 서버를 위한 FileSender 개체 생성
                 */
                fs = new FileSender(filePath, fsPort);
                fs.send();

                /*
                 * 클라이언트와 제어 서버 소켓 연결끊기
                 */
                sock.close();
                System.out.println("DisConnected Client...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
