import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleTCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public static void main(String[] args) {
        String serverIp = "127.0.0.1";
        int serverPort = 6666;
        try {
            SimpleTCPClient client = new SimpleTCPClient();
            client.start(serverIp, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(String serverIp, int serverPort) throws IOException {
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        Thread readMessageThread = new Thread(() -> {
            try {
                while (true) {
                    String message = input.readUTF();
                    System.out.println("Mensagem do servidor: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readMessageThread.start();

        while (true) {
            String message = scanner.nextLine();
            output.writeUTF(message);
        }
    }
}
