package FileTransferClient;

/**
 * Created by Lex2Star
 * 클라이언트 구동 어플리케이션
 */
public class ClientApp {
    private static String defaultFileName = "test.txt";

    public static void main(String args[]) {
        /*
         * 전송 받을 파일 저장 경로는 프로젝트 root에서 "./receive/"
         *
         * Client 개체 생성 시
         * 매개변수에 파일명만 기재할 경우 기본 설정 사용
         * 기본 IP (ip): "localhost"
         * 기본 제어 포트 (port) : 25111
         * 기본 파일 전송 포트 (fsPort) : 25112
         * 기본 파일명 (defaultFileName) : "text.txt"
         */
        Client client = new Client(defaultFileName);
        client.act();
    }
}
