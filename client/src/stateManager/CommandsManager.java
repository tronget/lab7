package stateManager;

import commands.ExitCommand;
import models.MusicBand;
import models.User;
import shared.Request;
import utility.ConsoleWriter;
import utility.MusicBandScanner;
import utility.Program;

import java.util.Arrays;
import java.util.List;


/**
 * Класс, отвечающий за определение команд и дальнейшее их исполнение.
 */
public class CommandsManager {
    private static final List<String> commands = Arrays.asList(
            "help",
            "info",
            "show",
            "insert",
            "update",
            "remove_key",
            "clear",
            "execute_script",
            "exit",
            "remove_greater",
            "remove_lower",
            "history",
            "min_by_creation_date",
            "print_ascending",
            "print_unique_number_of_participants",
            "login",
            "register"
    );

    /**
     * Метод определяет команду для последующего исполнения.
     *
     * @param userInput строка считанная из консоли, в которой содержится команда
     */
    public static void defineCommand(String[] userInput) {
        String commandName = userInput[0];

        if (commandName.equals("exit")) {
            ExitCommand.execute();
            return;
        }

        if (commandName.equals("login") && userInput.length == 1) {
            UserManager.login();
            return;
        }

        if (commandName.equals("register") && userInput.length == 1) {
            UserManager.register();
            return;
        }

        if (!UserManager.isLogin()) {
            ConsoleWriter.getInstance().alert("Вам необходимо залогиниться,\nдля этого напишите команду login или register.");
            return;
        }



        if (commands.contains(commandName)) {
            if (userInput.length > 2) {
                ConsoleWriter.getInstance().alert("Слишком много аргументов!");
                return;
            }
            String stringArg = userInput.length == 2 ? userInput[1] : null;
            MusicBand musicBandArg = null;
            User user = UserManager.getUser();
            String username = user.getUsername(),
                    password = user.getPassword();
            if (Arrays.asList(
                    "insert", "update"
                ).contains(commandName) && userInput.length == 2 ||
                    Arrays.asList(
                            "remove_greater", "remove_lower"
                    ).contains(commandName) && userInput.length == 1) {
                musicBandArg = MusicBandScanner.scan();
            }

            sendCommand(commandName, stringArg, musicBandArg, username, password);

            try {
                System.out.println(getResponse().trim());
            } catch (NullPointerException ignored) {
            }

        } else if (commandName.isEmpty()) {
            ConsoleWriter.getInstance().alert("Пустая команда!");
        } else {
            ConsoleWriter.getInstance().alert("Такой команды нет!");
        }
    }

    private static void sendCommand(String commandName, String arg, MusicBand musicBand, String username, String password) {
        Request request = new Request(commandName, arg, musicBand);
        request.setUsername(username);
        request.setPassword(password);
        Program.getInstance().getRequestLogic().send(request);
    }

    private static String getResponse() {
        return Program.getInstance().getResponseLogic().receive();
    }
}
