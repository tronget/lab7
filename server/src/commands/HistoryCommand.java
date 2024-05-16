package commands;

import network.ResponseBuilder;
import utility.Program;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        name = "history";
        description = "history : вывести последние 10 команд (без их аргументов)";
    }
    /**
     * Метод, показывающий последние 10 команд без их аргументов.
     */
    @Override
    public void execute() {
        String username = user.getUsername();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        responseBuilder.add(username, Program.getInstance().printHistory(username));
    }
}
