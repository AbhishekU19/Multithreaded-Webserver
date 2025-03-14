import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // ExecutorService to manage client connections through thread pooling
    private final ExecutorService threadPool;

    // Constructor to initialize the thread pool with the specified size
    public Server(int poolSize) {
        // A fixed-size thread pool to handle client connections concurrently
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    /**
     * Handles the client request in a separate thread.
     * Sends a greeting message to the connected client.
     *
     * @param clientSocket The socket representing the client connection
     */
    
    public void handleClient(Socket clientSocket) {
        // Use try-with-resources to automatically close the PrintWriter
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            // Send a greeting message to the client
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
        } catch (IOException ex) {
            // Log the exception if there is an error during communication
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Server configuration
        int port = 8010;           // Port number to listen on
        int poolSize = 10;         // Size of the thread pool to handle requests concurrently

        // Initialize the server with the given thread pool size
        Server server = new Server(poolSize);

        try {
            // Create a ServerSocket to listen for client connections
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);  // Set a timeout of 70 seconds for client connections

            System.out.println("Server is listening on port " + port);

            // Continuously accept client connections in an infinite loop
            while (true) {
                // Accept an incoming client connection
                Socket clientSocket = serverSocket.accept();

                // Handle the client connection using a thread from the thread pool
                // The task to handle the client request is submitted to the pool
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (IOException ex) {
            // Log any IOException that occurs during server operation
            ex.printStackTrace();
        } finally {
            // Shut down the thread pool when the server shuts down
            server.threadPool.shutdown();
        }
    }
}
