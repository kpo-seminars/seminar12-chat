package ru.hse.seminar12.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static Map<Socket, PrintWriter> CLIENT_SOCKETS = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(9000)) {
            while (true) {
                acceptClient(serverSocket);
            }
        }
    }

    private static void acceptClient(ServerSocket serverSocket) {
        try {

            Socket clientSocket = serverSocket.accept();
            startMessageHandler(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startMessageHandler(Socket clientSocket) throws IOException {
        new Thread(new MessageHandlerTask(clientSocket)).start();
    }
}
