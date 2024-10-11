import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private static final int PORT = 12345;
    private static final String ENCRYPTION_KEY = "QWERTYUIOPASDFGHJKLZXCVBNM"; // Example key for substitution

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server started on port: " + PORT);

            while (true) {
                Socket socket = serverSocket.accept(); // Accept incoming client connections
                System.out.println("New client connected: " + socket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandlers.add(clientHandler);

                new Thread(clientHandler).start(); // Handle each client in a new thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast encrypted message to all clients
    public static void broadcastMessage(String encryptedName, String encryptedMessage, ClientHandler sender) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != sender) {
                clientHandler.sendMessage(encryptedName, encryptedMessage);
            }
        }
    }

    // Remove client handler when a client disconnects
    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        System.out.println("Client disconnected: " + clientHandler.getClientName());
    }
}

// Handles individual client communication
class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String encryptedClientName;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Read encrypted client name
            encryptedClientName = in.readLine();
            System.out.println("Encrypted client name received on server: " + encryptedClientName);

            String encryptedMessage;
            while ((encryptedMessage = in.readLine()) != null) {
                System.out.println("Encrypted message from " + encryptedClientName + ": " + encryptedMessage);
                ChatServer.broadcastMessage(encryptedClientName, encryptedMessage, this); // Send encrypted data
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatServer.removeClient(this);
        }
    }

    // Send encrypted message to the client
    public void sendMessage(String encryptedName, String encryptedMessage) {
        out.println(encryptedName);
        out.println(encryptedMessage);
    }

    public String getClientName() {
        return encryptedClientName;
    }
}
