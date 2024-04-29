package stateManager;

import commands.*;
import models.MusicBand;
import shared.Request;
import utility.ConsoleWriter;
import utility.MusicBandScanner;
import utility.Program;

import java.util.Arrays;
import java.util.Map;


/**
 * Класс, отвечающий за определение команд и дальнейшее их исполнение.
 */
public class CommandsManager {
    private static final Map<String, Command> commands = Map.ofEntries(
            Map.entry(new HelpCommand().getName(), new HelpCommand()),
            Map.entry(new InfoCommand().getName(), new InfoCommand()),
            Map.entry(new ShowCommand().getName(), new ShowCommand()),
            Map.entry(new InsertCommand().getName(), new InsertCommand()),
            Map.entry(new UpdateCommand().getName(), new UpdateCommand()),
            Map.entry(new RemoveCommand().getName(), new RemoveCommand()),
            Map.entry(new ClearCommand().getName(), new ClearCommand()),
            Map.entry(new ScriptCommand().getName(), new ScriptCommand()),
            Map.entry(new ExitCommand().getName(), new ExitCommand()),
            Map.entry(new RemoveAllGreaterCommand().getName(), new RemoveAllGreaterCommand()),
            Map.entry(new RemoveAllLowerCommand().getName(), new RemoveAllLowerCommand()),
            Map.entry(new HistoryCommand().getName(), new HistoryCommand()),
            Map.entry(new MinByCreationDateCommand().getName(), new MinByCreationDateCommand()),
            Map.entry(new PrintAscendingCommand().getName(), new PrintAscendingCommand()),
            Map.entry(new PrintUniqueParticipantsCommand().getName(), new PrintUniqueParticipantsCommand()),
            Map.entry(new RegisterCommand().getName(), new RegisterCommand())
    );

    /**
     * Метод определяет команду для последующего исполнения.
     *
     * @param userInput строка считанная из консоли, в которой содержится команда
     */
    public static void defineCommand(String[] userInput) {
        String commandName = userInput[0];
        if (commands.containsKey(commandName)) {
            if (userInput.length > 4) {
                ConsoleWriter.getInstance().alert("Слишком много аргументов!");
                return;
            }
            if (userInput.length < 3) {
                ConsoleWriter.getInstance().alert("Команда отправлена без логина и пароля!");
                return;
            }
            if (commandName.equals(new ExitCommand().getName())) {
                new ExitCommand().execute();
                return;
            }
            String stringArg = null;
            MusicBand musicBandArg = null;
            String username = userInput[userInput.length - 2], password = userInput[userInput.length - 1];
            if (userInput.length == 4) {
                stringArg = userInput[1];
            }
            if (Arrays.asList(
                    new InsertCommand().getName(),
                    new UpdateCommand().getName(),
                    new RemoveAllGreaterCommand().getName(),
                    new RemoveAllLowerCommand().getName()
            ).contains(commandName)) {
                musicBandArg = MusicBandScanner.scan();
            }
            sendCommand(commandName, stringArg, musicBandArg, username, password);
            try {
                System.out.println(getResponse().trim());
            } catch (NullPointerException ignored) {}

        } else if (commandName.isEmpty()) {
            ConsoleWriter.getInstance().alert("Пустая команда!");
        } else {
            ConsoleWriter.getInstance().alert("Такой команды нет!");
        }
    }

    private static void sendCommand(String commandName, String arg, MusicBand musicBand, String username, String password) {
        Command command = commands.get(commandName);
        Request request = new Request(command, arg, musicBand);
        request.setUsername(username);
        request.setPassword(password);
        Program.getInstance().getRequestLogic().send(request);
    }

    private static String getResponse() {
        return Program.getInstance().getResponseLogic().receive();
    }
}
