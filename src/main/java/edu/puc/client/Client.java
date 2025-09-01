package edu.puc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {
        Client client = new Client("localhost", 4000);
        try {
            client.connect();
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.print("Enter a number to calculate its factorial: ");
                int input = scanner.nextInt();
                if (input <= 0){
                    System.out.println("Exiting...");
                    client.sendData("exit");
                    break;
                }
                client.sendData(input);
                System.out.println("Factorial received from server: " + client.receiveData());
                client.receiveData();
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws UnknownHostException, IOException {
        this.socket = new Socket(this.host, this.port);
        this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void sendData(String data) {
        this.writer.println(data);
    }

    public void sendData(int data) {
        this.writer.println(data);
    }

    public String receiveData() throws IOException {
        return this.reader.readLine();
    }
}
