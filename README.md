# FileTransferUsingTCP
----
제주대학교 컴퓨터교육과 2016-2 컴퓨터통신 과제 __(TCP를 활용한 파일 전송 프로그램)__ 입니다.
구동은 ServerApp과 ClientApp을 통해 이루어지며, 수행 시나리오는 다음과 같습니다.

1. Server 구동
    * 서버 측
        1. 제어 서버 구동
2. Sever에 Client 접속
    * 클라이언트 측
        1. 제어 클라이언트 구동 및 제어 서버에 접속
3. Client가 Server에 파일 요청
    * 클라이언트측
        1. 제어 서버에 메시지를 통해 파일명 전송
        2. FileReceiver 개체를 통해 파일 전송 클라이언트 생성 및 접속 대기
4. Server가 파일을 전송
    * 서버 측
        1. FileSender 개체를 이용해 파일 전송 서버 구동
        2. 출력 스트림을 통한 파일 전송
    * 클라이언트 측
        1. 파일 전송 서버에 접속
5. Client가 전송 받은 파일을 저장
    * 클라이언트 측
        1. 입력 스트림을 통해 파일 전달 받고 파일로 출력
        2. 다운로드 완료 후 파일 전송 서버와 접속 종료
        3. 제어 서버와 접속종료
    * 서버 측
        1. 파일 전달 완료 후 파일 전송 서버 종료
        2. 제어 서버 대기

## ServerApp
----
전송할 파일 경로는 프로젝트 root에서

    "./send/"

Server 개체 생성 시
매개변수를 주지 않을 경우 기본 설정 사용

    기본 IP (ip): "localhost"
    기본 제어 포트 (port) : 5000
    기본 파일 전송 포트 (fsPort) : 5001

설정 변경시

    new Server(String ip, int port, int fsPort);

## ClientApp
----
전송 받을 파일 저장 경로는 프로젝트 root에서

    "./receive/"

전송 받을 파일은 어플리케이션 매개변수(args)를 통해 파일 설정 가능
args를 입력하지 않아도 defaultFileName을 통해 파일 설정 가능

Client 개체 생성 시 매개변수에 파일명만 기재할 경우 기본 설정 사용

    기본 IP (ip): "localhost"
    기본 제어 포트 (port) : 5000
    기본 파일 전송 포트 (fsPort) : 5001

설정 변경시

    new Client(String ip, int port, int fsPort, String fileName);
