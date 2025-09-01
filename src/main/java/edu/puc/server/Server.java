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

/**
 *
 * @author root
 */
public class Server {
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;

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
            return clientSocket;
        } catch (IOException e) {
            System.err.println("Error accepting client connection: " + e.getMessage());
        }
        return null;
    }

    public String receiveData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.acceptClient().getInputStream()));
        return reader.readLine();
    }

    public void sendData(String data) throws IOException{
        PrintWriter writer = new PrintWriter(this.clientSocket.getOutputStream(), true);
    }
}
