package commands;

import network.ResponseBuilder;
import utility.Program;
import utility.ScriptExecutor;

public class ExitCommand extends Command {

    public ExitCommand() {
        name = "exit";
        description = "exit : завершить программу (с сохранением в файл)";
    }

    /**
     * Метод, предлагающий завершить программу.
     */
    @Override
    public void execute() {
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        if (ScriptExecutor.getInstance().isScriptExecution()) {
            String username = user.getUsername();
            responseBuilder.add(username, "Завершение работы.");
            Program.getInstance().getResponseLogic().send(
                    Program.getInstance().getRequestLogic().getClientAddress(username),
                    responseBuilder.get(username));
        }
    }

    @Override
    public void execute(String arg) {
        super.execute(arg);
    }
}
