package ru.hse.seminar12.client;

import java.io.*;

public class ReceiverTask implements Runnable {
    private final BufferedReader input;

    public ReceiverTask(BufferedReader input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String receivedMessage = receiveMessage();
                printMessage(receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printMessage(String receivedMessage) {
        System.out.printf("%s\n-> ", receivedMessage);
    }

    private String receiveMessage() throws IOException {
        return input.readLine();
    }
}
