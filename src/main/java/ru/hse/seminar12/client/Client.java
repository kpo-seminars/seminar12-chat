package ru.hse.seminar12.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9000)) {

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            greet(output);
            startThreads(input, output);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startThreads(BufferedReader input, PrintWriter output) throws InterruptedException {
        Thread sender = new Thread(new SenderTask(output));
        Thread receiver = new Thread(new ReceiverTask(input));

        sender.start();
        receiver.start();

        sender.join();
        receiver.join();
    }

    private static void greet(PrintWriter output) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String nickname = scanner.nextLine();
        output.write(nickname + "\n");
        output.flush();
    }
}