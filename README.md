# Encyrpted_Socket_Chat
![WhatsApp Image 2024-10-12 at 11 31 24_9d82f0f5](https://github.com/user-attachments/assets/1b181208-cfa8-4b10-9de5-5024f466307f)

This project demonstrates a basic client-server communication using sockets in Java. The communication between the client and the server is encrypted using a Substitution Cipher, ensuring that the usernames and messages sent over the server are encrypted on the server side. However, the client-side decrypts the messages for users to read the plain text.

## Features

- **Server-Client Communication:** The server can handle multiple clients and enables communication between them.
- **Encryption with Substitution Cipher:** All messages and usernames are encrypted on the server using a simple Substitution Cipher. The clients decrypt the messages to display plain text.
- **Multi-Client Support:** Multiple clients can connect to the server, and messages from one client are relayed to all connected clients.

## Files in the Project

- `ChatServer.java` - The server-side code handling client connections, encryption, and message broadcasting.
- `ChatClient.java` - The client-side code to send and receive messages from the server.
- `SubstitutionCipher.java` - Contains the logic for encrypting and decrypting messages using a substitution cipher.
- `.class` Files - Compiled Java classes.

## How to Run

### To Run the Server
    javac ChatServer.java
    java ChatServer

### To Run the Client
    javac ChatClient.java
    java ChatClient
