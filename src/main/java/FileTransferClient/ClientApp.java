package FileTransferClient;

/**
 * Created by HYUNKILP on 2016-10-09.
 */
public class ClientApp {

    public static void main(String[] args) {

        Client client = new Client("test.txt");
        client.act();
    }
}
