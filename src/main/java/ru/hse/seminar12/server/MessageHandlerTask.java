package ru.hse.seminar12.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageHandlerTask implements Runnable {
    private final Socket clientSocket;
    private final BufferedReader input;
    private String nickname;

    public MessageHandlerTask(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

        this.input = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String nickname = receiveNickname();
            registerClient();

            while (true) {
                receiveAndProceedMessage(nickname);
            }

        } catch (IOException ignored) {
        } finally {
            Server.CLIENT_SOCKETS.remove(clientSocket);
            System.out.println("[LEAVED] client: " + nickname);
        }
    }

    private void receiveAndProceedMessage(String nickname) throws IOException {
        String message = input.readLine();
        String formattedMessage = String.format("%s: %s\n", nickname, message);
        System.out.printf("[MESSAGE] from %s", formattedMessage);

        for (PrintWriter socketOutput : Server.CLIENT_SOCKETS.values()) {
            socketOutput.write(formattedMessage);
            socketOutput.flush();
        }
    }

    private void registerClient() throws IOException {
        PrintWriter output = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream()), true);
        Server.CLIENT_SOCKETS.put(clientSocket, output);
    }

    private String receiveNickname() throws IOException {
        String nickname = input.readLine();
        this.nickname = nickname;
        System.out.println("[NEW] client: " + nickname);
        return nickname;
    }
}
