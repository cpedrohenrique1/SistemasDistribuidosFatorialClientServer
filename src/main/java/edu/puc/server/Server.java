/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.puc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.puc.utils.Calculator;

/**
 *
 * @author root
 */
public class Server {
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args){
        Server server = new Server(4000);
        try {
            server.acceptClient();
            String line = server.receiveData();
            System.out.println("Received from client: " + line);
            server.sendData(Integer.toString(Calculator.fatorial(Integer.parseInt(line))));
        }catch (IOException e) {
            System.err.println("Error during communication: " + e.getMessage());
        } finally {
            server.stopServer();
        }
    }

    public Server(int port){
        this.port = port;
        this.startServer();
    }

    public void startServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.print("Server started on port " + this.port);
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public Socket acceptClient() {
        try {
            System.out.println("Waiting for client connection...");
            this.clientSocket = this.serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            this.reader = new BufferedReader(new InputStreamReader(this.acceptClient().getInputStream()));
            this.writer = new PrintWriter(this.clientSocket.getOutputStream(), true); 
            return clientSocket;
        } catch (IOException e) {
            System.err.println("Error accepting client connection: " + e.getMessage());
        }
        return null;
    }

    public String receiveData() throws IOException {
        return this.reader.readLine();
    }

    public void sendData(String data){
        this.writer.println(data);
    }

    public void stopServer(){
        try {
            if (this.clientSocket != null && !this.clientSocket.isClosed()) {
                this.clientSocket.close();
            }
            if (this.serverSocket != null && !this.serverSocket.isClosed()) {
                this.serverSocket.close();
            }
            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }
}
