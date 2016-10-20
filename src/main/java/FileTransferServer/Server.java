package FileTransferServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Lex2Star
 * 제어 서버를 위한 Sever 클래스
 */
public class Server {
    private FileSender fs;  // 파일 전송을 위한 개체 생성
    private String filePath;    // 파일의 경로를 위한 String 개체
    private String fileName = null; // 파일 이름을 저장하기 위한 String 개체
    private String order = null;    // 명령을 저장하기 위한 String 개체
    private StringTokenizer st = null;  // 명령을 토큰화하여 파일명을 추출하기 위한 StringTokenizer 개체
    private int port;   // 제어 포트 번호
    private int fsPort; // 파일 전송 포트 번호

    /*
     * Server 개체 생성자
     * 기본 제어 포트 : 25111
     * 기본 파일전송 포트 : 25112
     */
    public Server() {
        this.port = 25111;
        this.fsPort = 25112;
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
                Socket sock = serverSock.accept();  // 클라이언트 접속 대기
                System.out.println("Connected Client..."); // 클라이언트 접속 시 출력

                /*
                 * 클라이언트에게 환영 문자 전송
                 */
                PrintWriter writer = new PrintWriter(sock.getOutputStream());   // 환영 문자 전송위한 PrintWriter 개체를 출력 스트림으로 생성
                writer.println("Welcome to File Server");   // 버퍼에 환영문자 저장
                writer.flush(); // 버퍼 비우기를 통한 전송

                /*
                 * 클라이언트가 보내올 파일명 받기
                 */
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));   // 명령을 받기위한 BufferedReader 개체 입력 스트림으로 생성
                while ((order = reader.readLine()) == null) // 버퍼에서 라인단위로 String 개체 추출
                    ;
                st = new StringTokenizer(order, " ");   // 명령에서f " "를 토큰으로 하여 파일명 추출위한 StingTokenizer 개체 생성
                fileName = st.nextToken();  // st에서 첫 토큰은 get
                fileName = st.nextToken().toString();   // st의 다음 토큰이 파일명이기 때문에 toSting()을 통한 String 개체 추출
                filePath = ("./send/" + fileName);  // 파일 경로 생성
                System.out.println(filePath); // 파일 경로 확인 위한 출력

                /*
                 * 파일 전송 서버를 위한 FileSender 개체 생성
                 */
                fs = new FileSender(filePath, fsPort);  // 파일 전송 서버 개체 생성
                fs.send();  // 파일 전송 하기

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
