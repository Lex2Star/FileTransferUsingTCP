package FileTransferServer;

/**
 * Created by Lex2Star
 * 서버 구동 어플리케이션
 */
public class ServerApp {
    public static void main(String[] args) {
        /*
         * 전송할 파일 경로는 프로젝트 root에서 "./send/"
         *
         * Server 개체 생성 시
         * 기본 IP (ip): "localhost"
         * 기본 제어 포트 (port) : 25111
         * 기본 파일 전송 포트 (fsPort) : 25112
         */
        Server server = new Server();
        server.act();
    }
}
