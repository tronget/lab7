package commands;

import utility.Program;

import java.util.Scanner;

public class ExitCommand {
    /**
     * Метод, предлагающий завершить программу.
     */
    public static void execute() {
        System.out.print("Уверены? Последние изменения не сохранятся y/n: ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        while (!(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n"))) {
            System.out.print("Уверены? Последние изменения не сохранятся y/n: ");
            answer = scanner.next();
        }
        if (answer.equalsIgnoreCase("y")) {
            Program.getInstance().stop();
        } else {
            System.out.println("Отмена завершения работы.");
        }
    }
}
