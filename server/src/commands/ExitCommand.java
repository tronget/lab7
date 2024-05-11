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
            responseBuilder.add("Завершение работы.");
            Program.getInstance().getResponseLogic().send(Program.getInstance().getRequestLogic().getClientAddress(), responseBuilder.get());
        }
    }

    @Override
    public void execute(String arg) {
        super.execute(arg);
    }
}
