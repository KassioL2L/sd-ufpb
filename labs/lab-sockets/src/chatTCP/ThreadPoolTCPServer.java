import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTCPServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private ExecutorService threadPool;

    public static void main(String[] args) {
        int serverPort = 6666;
        try {
            ThreadPoolTCPServer server = new ThreadPoolTCPServer();
            server.start(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        threadPool = Executors.newFixedThreadPool(10); // Definindo um pool de threads com 10 threads
        System.out.println("Servidor iniciado na porta " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket, this);
            clients.add(clientHandler);
            threadPool.execute(clientHandler); // Enviando a tarefa para o pool de threads
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private ThreadPoolTCPServer server;
        private DataInputStream input;
        private DataOutputStream output;

        public ClientHandler(Socket socket, ThreadPoolTCPServer server) {
            this.socket = socket;
            this.server = server;
        }

        @Override
        public void run() {
            try {
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());

                String message;
                while ((message = input.readUTF()) != null) {
                    System.out.println("Mensagem recebida: " + message);
                    server.broadcastMessage(message, this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                    output.close();
                    socket.close();
                    server.removeClient(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            try {
                output.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
