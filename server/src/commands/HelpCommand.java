package commands;

import network.ResponseBuilder;
import utility.Program;

public class HelpCommand extends Command {
    public HelpCommand() {
        name = "help";
        description = "help : вывести справку по доступным командам";
    }
    /**
     * Метод выводит справку по доступным командам.
     */
    @Override
    public void execute() {
        String username = user.getUsername();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        responseBuilder.add(username, this.description);
        responseBuilder.add(username, new InfoCommand().description);
        responseBuilder.add(username, new ShowCommand().description);
        responseBuilder.add(username, new InsertCommand().description);
        responseBuilder.add(username, new UpdateCommand().description);
        responseBuilder.add(username, new RemoveCommand().description);
        responseBuilder.add(username, new ClearCommand().description);
        responseBuilder.add(username, new ScriptCommand().description);
        responseBuilder.add(username, new ExitCommand().description);
        responseBuilder.add(username, new RemoveAllGreaterCommand().description);
        responseBuilder.add(username, new RemoveAllLowerCommand().description);
        responseBuilder.add(username, new HistoryCommand().description);
        responseBuilder.add(username, new MinByCreationDateCommand().description);
        responseBuilder.add(username, new PrintAscendingCommand().description);
        responseBuilder.add(username, new PrintUniqueParticipantsCommand().description);
        responseBuilder.add(username, new RegisterCommand().description);
        responseBuilder.add(username, new LoginCommand().description);
    }
}
