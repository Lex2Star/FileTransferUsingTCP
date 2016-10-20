package FileTransferClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Lex2Star
 */
public class Client {
    private String fileName;    // 파일명 저장 String 개체
    private FileReceiver fr;    // 파일 전송 클라이언트 개체 생성
    private String ip;  // ip 저장 String 개체
    private int port;   // 제어 포트 번호 저장 int 변수
    private int frPort; // 파일 전송 포트 번호 저장 int 변수
    private String msg = null; // 메지시 저장 String 개체

    /*
    * Client 개체 생성자
    * 기본 IP : "localhost"
    * 기본 제어 포트 : 25111
    * 기본 파일전송 포트 : 25112
    * 기본 파일명 : "text.txt"
    */
    public Client(String fileName) {
        this.ip = "localhost";
        this.port = 25111;
        this.frPort = 25112;
        this.fileName = fileName;
    }

    public void act() {
        try {
            /*
             * 제어 서버 접속
             */
            Socket socket = new Socket(ip, port);   // 클라이언트 소켓 생성
            while (!socket.isConnected())   // 클라이언트가 서버에 연결 될 때 까지 대기
                ;
            System.out.println("Connected server...");  // 서버에 접속 시 출력

            /*
             * 서버가 보낸 환영 문자 받기
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 메시지을 받기위한 BufferedReader 개체 입력 스트림으로 생성
            while ((msg = reader.readLine()) == null)   // 메시지를 라인단위로 받기
                ;
            System.out.println("server : " + msg);  // 서버에서 온 메시지 출력

            /*
             * 제어 서버로 파일명 전송
             */
            PrintWriter writer = new PrintWriter(socket.getOutputStream()); // 서버에 파일명 전송 위한 PrintWriter 개체 출력 스트림으로 생성
            writer.println("get " + fileName);  // 명령을 버퍼에 저장
            writer.flush(); // 버퍼 비우기를 통한 명령 전송
            System.out.println(fileName);   // 파일명 확인위한 파일명 출력

            /*
             * 파일을 전달받을 클라이언트를 위한 FileReceiver 개체 생성
             */
            fr = new FileReceiver(fileName, ip, frPort, socket);    // 파일 전송 클라이언트 개체 생성
            fr.receive();   // 파일 전달 받기

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
