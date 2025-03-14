package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    // This method returns a Consumer that handles client connections.
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                // Sends a greeting message to the client
                toSocket.println("Hello from server " + clientSocket.getInetAddress());
            } catch (IOException e) {
                // In case of an error, print the stack trace
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;  // Port number on which the server will listen
        Server server = new Server();  // Create an instance of the Server class
        try {
            // Create a ServerSocket to listen on the specified port
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);  // Set the timeout for client connections (10 seconds)
            System.out.println("Server is listening on port " + port);
            
            while (true) {
                // Accept a client connection when one is made
                Socket clientSocket = serverSocket.accept();
                
                // Create and start a new thread to handle the client connection
                // A new thread is created for each client that connects to the server
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                thread.start();  // Start the thread to process the client request
            }
        } catch (IOException e) {
            // In case of an error (e.g., server socket error), print the stack trace
            e.printStackTrace();
        }
    }
}
