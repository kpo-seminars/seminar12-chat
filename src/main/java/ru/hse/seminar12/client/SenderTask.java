package ru.hse.seminar12.client;

import java.io.PrintWriter;
import java.util.Scanner;

public class SenderTask implements Runnable {
    private final PrintWriter output;

    public SenderTask(PrintWriter output) {
        this.output = output;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String messageToSend = readConsole(scanner);
            send(messageToSend);
        }
    }

    private void send(String messageToSend) {
        output.write(messageToSend + "\n");
        output.flush();
    }

    private static String readConsole(Scanner scanner) {
        System.out.print("-> ");
        String messageToSend = scanner.nextLine();
        return messageToSend;
    }
}
