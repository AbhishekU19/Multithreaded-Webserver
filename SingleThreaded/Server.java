package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Method that handles the server logic
    public void run() throws IOException {
        // Port on which the server will listen for client connections
        int port = 8010;

        // Create a ServerSocket to listen on the specified port
        try (ServerSocket socket = new ServerSocket(port)) {
            // Set the server socket timeout to 10 seconds
            socket.setSoTimeout(10000);

            // Infinite loop to keep the server running and accepting connections
            while (true) {
                try {
                    // Print message indicating the server is listening on the port
                    System.out.println("Server is listening on port " + port);

                    // Accept a client connection. This is a blocking call.
                    Socket acceptedConnection = socket.accept();

                    // Print message showing the client connection details
                    System.out.println("Connection accepted from client " + acceptedConnection.getRemoteSocketAddress());

                    // Setup output stream to send message to the client
                    PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);

                    // Setup input stream to receive message from the client (although it's not used in this example)
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));

                    // Send a greeting message to the client
                    toClient.println("Hello from Server");

                    // Close streams and socket connection to free resources
                    toClient.close();
                    fromClient.close();
                    acceptedConnection.close();

                } catch (IOException e) {
                    // Catch and print any IOExceptions that occur during the connection process
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // Catch and handle any IOException that occurs when creating the server socket
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create an instance of the Server class and run it
        Server server = new Server();
        try {
            // Call the run method to start the server
            server.run();
        } catch (Exception e) {
            // Catch any general exceptions that occur during the execution of the server
            e.printStackTrace();
        }
    }
}
