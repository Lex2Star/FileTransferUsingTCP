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
        this.fileName = fileName;   // 전달 받은 파일명을 통한 String 개체 생성
        this.ip = ip;   // 전달 받은 IP를 통한 String 개체 생성
        this.port = port;   // 전달 받은 포트번호를 통한 int 변수 생성
        /*
         * 파일 전송 서버 연결 시도를 위한 반복
         * 서버에 연결이 될 때까지 시도
         */
        while(msocket.isConnected()) {
            System.out.println("#conneting..");  // 연결 중 출력
            try {
                this.socket = new Socket(ip, port); // 서버에 접속하기 위한 Socket 개체 생성
                if(this.socket.isConnected()) { // 만약 서버에 접속하였다면
                    System.out.println("#Conneted File Transfer Server");    // 서버 접속 완료 메시지 출력
                    break;  // 서버 접속 시도 반복 탈출
                }
            } catch (IOException e) {
                // 서버에 접속 하지 못했을 경우 에러 메시지 출력을 하는데, 미관상 좋지 않기 때문에 이를 로그를 출력하지 않음
            }
        }
    }

    public void receive() {
        try {
            byte[] contents = new byte[10000];  // 패킷을 받을 byte 배열 생성
            int bytesRead;  // 전달 받은 Byte를 저장하기 위한 int 변수

            FileOutputStream fos = new FileOutputStream("./receive/" + fileName);   // 파일 출력 스트림 생성
            BufferedOutputStream bos = new BufferedOutputStream(fos);               // 출력 스트림 버퍼 생성
            InputStream is = socket.getInputStream();                               // 입력 스트림 생성

            /*
             * 입력 스트림을 통해 파일을 읽어들이고
             * 출력 스트림 버퍼를 통해 파일 작성
             */
            while ((bytesRead = is.read(contents)) != -1)   // 입력 스트림에 의해 읽어들인 byte가 -1(EOF)가 아닌 동안 반복
                bos.write(contents, 0, bytesRead);  // 파일 출력 스트림을 통해 파일 생성

            /*
             * 파일 전송 클라이언트 종료
             */
            bos.flush();
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
