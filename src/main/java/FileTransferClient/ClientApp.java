package FileTransferClient;

/**
 * Created by Lex2Star
 * 클라이언트 구동 어플리케이션
 */
public class ClientApp {
    private static String fileName;
    private static String defaultFileName = "test.txt";

    public static void main(String[] args) {
        /*
         * 전송 받을 파일 저장 경로는 프로젝트 root에서 "./receive/"
         *
         * 어플리케이션 매개변수를 통해 파일 설정 가능
         * args를 입력하지 않아도
         * defaultFileName을 통해 파일 설정 가능
         *
         * Client 개체 생성 시
         * 매개변수에 파일명만 기재할 경우 기본 설정 사용
         * 기본 IP (ip): "localhost"
         * 기본 제어 포트 (port) : 5000
         * 기본 파일 전송 포트 (fsPort) : 5001
         * 설정 변경시
         * new Client(String ip, int port, int fsPort, String fileName);
         */
        fileName = checkFileName(args);
        Client client = new Client(fileName);
        client.act();
    }

    private static String checkFileName(String[] args) {
        if(args.length != 1)
            return defaultFileName;
        return args[0];
    }
}
