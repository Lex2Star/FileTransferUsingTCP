package FileTransferServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lex2Star
 * 파일 전송 서버를 위한 FileSender 클래스
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
        this.file = new File(fileName); // 전달 받은 fileName을 통한 File 개체 생성
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port); // 파일 전송 서버 구동
            System.out.println("#Started Control Sever...");    // 파일 전송 서버 구동 메시지 출력
            this.socket = serverSocket.accept();    // 클라이언트 접속 대기
            System.out.println("#Connected Client..."); // 클라이언트 접속 시 출력
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));   //파일 입력 버퍼 생성
            OutputStream os = socket.getOutputStream();                                     //출력 스트림 생성

            byte[] contents;    // 파일을 byte 단위로 보내기 위한 byte 배열 생성
            long fileLength = file.length();    // 파일 크기 변수
            long current = 0;   // 파일 순차 전송을 위한 현재 보낸 파일 크기 저장 변수

            /*
             * 보낼 파일을 파일 입력 버퍼에 작성 후
             * 출력 스트림을 통해 파일 전송
             */
            System.out.println("#Start...");
            while (current != fileLength) {
                int size = 10000;   // size 크기 만큼 패킷 전송위한 변수

                if (fileLength - current >= size)
                    current += size;    // 보낼 파일 크기 size 만큼 추가
                else {
                    size = (int) (fileLength - current);    // 변수 size 보다 작은 패킷 크기 추출
                    current = fileLength;   // 보낼 파일 크기 size 만큼 추가
                }

                contents = new byte[size];  // byte 개체 생성후 contents에 저장
                bis.read(contents, 0, size);    // 파일 입력 버퍼가 0부터 size 만큼 byte를 파일 입력 버퍼에 저장
                os.write(contents); //파일 입력 버퍼에 있는 내용을 출력 스트림을 통해 전송
            }
            /*
             * 파일 전송 서버 종료
             */
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
