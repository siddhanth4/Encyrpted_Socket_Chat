import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost"; // Server IP or hostname
    private static final int SERVER_PORT = 12345; // Same port as the server
    private static final int ENCRYPTION_KEY = 2; // Example key for substitution

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to the chat server");

            // Initialize the substitution cipher
            SubstitutionCipher cipher = new SubstitutionCipher(ENCRYPTION_KEY);

            // Input and output streams for the client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // Get and encrypt client name
            System.out.println("Enter your name: ");
            String clientName = scanner.nextLine();
            String encryptedClientName = cipher.encrypt(clientName);
            out.println(encryptedClientName); // Send encrypted name to server

            // Separate thread to listen for messages from the server
            new Thread(new ServerListener(in, cipher)).start();

            // Main thread to send messages
            while (true) {
                String message = scanner.nextLine();
                String encryptedMessage = cipher.encrypt(message); // Encrypt message before sending
                out.println(encryptedMessage); // Send encrypted message to server
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Listener for incoming messages from the server
    private static class ServerListener implements Runnable {
        private BufferedReader in;
        private SubstitutionCipher cipher;

        public ServerListener(BufferedReader in, SubstitutionCipher cipher) {
            this.in = in;
            this.cipher = cipher;
        }

        @Override
        public void run() {
            try {
                String encryptedName;
                String encryptedMessage;

                while ((encryptedName = in.readLine()) != null && (encryptedMessage = in.readLine()) != null) {
                    String decryptedName = cipher.decrypt(encryptedName); // Decrypt sender's name
                    String decryptedMessage = cipher.decrypt(encryptedMessage); // Decrypt message
                    System.out.println(decryptedName + ": " + decryptedMessage); // Display decrypted data
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
