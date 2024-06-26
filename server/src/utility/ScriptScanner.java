package utility;

import commands.ExitCommand;
import commands.LoginCommand;
import commands.RegisterCommand;
import commands.ScriptCommand;
import manager.stateManager.CommandsManager;
import network.db.User;

import java.io.File;
import java.util.Scanner;

/**
 * Класс, позволяющий сканировать данные из скрипта.
 */
public class ScriptScanner {
    private final Scanner scanner;
    private User user;

    public ScriptScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Метод считывает строку, используя сканер, определенный в конструкторе.
     * Затем определяет команду, которая будет выполняться.
     */
    public void scan() {
        ScriptExecutor scriptExecutor = ScriptExecutor.getInstance();
        String[] userInput = scanner.nextLine().trim().split("\\s+");
        if (Program.getInstance().isWorkingStatus()) {
            if (userInput[0].equals(new ScriptCommand().getName()) && userInput.length == 2) {
                File file = new File(userInput[1]);
                // Если такого файла еще не было,
                // то сохранить оставшийся текст из скрипта в resursionScripts;
                if (!scriptExecutor.getFiles().contains(file)) {
                    StringBuilder scriptText = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        scriptText.append(scanner.nextLine()).append("\n");
                    }
                    scriptExecutor.updateRecursionScripts(scriptText.toString());
                }
            } else if (userInput[0].equals(new ExitCommand().getName()) ||
                    userInput[0].equals(new LoginCommand().getName()) ||
                    userInput[0].equals(new RegisterCommand().getName())) {
                Program.getInstance().getResponseBuilder().add(
                        user.getUsername(),
                        "Команда " + userInput[0] + " не выполняется в скрипте!"
                );
                return;
            }
            CommandsManager.defineCommand(userInput, user);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
