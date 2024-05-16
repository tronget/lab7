package manager.stateManager;

import commands.*;
import network.ResponseBuilder;
import network.db.User;
import utility.Program;

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
            Map.entry(new RegisterCommand().getName(), new RegisterCommand()),
            Map.entry(new LoginCommand().getName(), new LoginCommand())
    );

    /**
     * Метод определяет команду для последующего исполнения.
     *
     * @param userInput строка считанная из консоли, в которой содержится команда
     */
    public static void defineCommand(String[] userInput, User user) {
        String username = user.getUsername();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        String commandName = userInput[0];
        if (commands.containsKey(commandName)) {
            if (userInput.length > 2) {
                responseBuilder.add(username, "Слишком много аргументов!");
                return;
            }

            Program.getInstance().updateHistory(user.getUsername(), commandName);

            Command command = getCommandByName(commandName);
            command.setUser(user);
            if (userInput.length == 1) {
                command.execute();
            } else {
                String arg = userInput[1];
                command.execute(arg);
            }

        } else if (commandName.isEmpty()) {
            responseBuilder.add(username, "Пустая команда!");
        } else {
            responseBuilder.add(username, "Такой команды нет!");
        }
    }

    public static Command getCommandByName(String commandName) {
        return commands.getOrDefault(commandName, null);
    }
}
