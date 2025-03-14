package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    // Method to handle client-server communication
    public void run() throws UnknownHostException, IOException {
        int port = 8010; // Port on which server is listening
        InetAddress address = InetAddress.getByName("localhost"); // Address of the server (localhost for local machine)
        Socket socket = null; // Socket to establish a connection with the server
        PrintWriter toSocket = null; // PrintWriter to send data to the server
        BufferedReader fromSocket = null; // BufferedReader to read data from the server

        try {
            // Establish connection to the server using the address and port
            socket = new Socket(address, port);

            // Initialize the output and input streams for communication
            toSocket = new PrintWriter(socket.getOutputStream(), true); // PrintWriter with autoflush enabled
            fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream())); // BufferedReader to read response

            // Send a message to the server
            toSocket.println("Hello from the Client");

            // Read the response from the server
            String line = fromSocket.readLine();
            System.out.println("Response from the server: " + line); // Print server's response

            // Close the streams and socket after communication is done
            toSocket.close();
            fromSocket.close();
            socket.close();

        } catch (IOException e) {
            // Catch any IO exceptions that occur during communication and print the stack trace
            e.printStackTrace();
        } finally {
            // Ensure all resources are closed properly even in case of an error
            if (toSocket != null) {
                toSocket.close();
            }
            if (fromSocket != null) {
                fromSocket.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }

    // Main method to initiate the client operation
    public static void main(String[] args) {
        try {
            // Create an instance of the Client and run the communication
            Client client = new Client();
            client.run();
        } catch (Exception ex) {
            // Handle any exceptions that occur and print the stack trace
            ex.printStackTrace();
        }
    }
}
