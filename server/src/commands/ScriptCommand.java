package commands;

import utility.ScriptExecutor;

public class ScriptCommand extends Command {
    public ScriptCommand() {
        name = "execute_script";
        description = "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }

    /**
     * Метод, выполняющий скрипт заданного файла.
     * @param filename название файла со скриптом
     */
    @Override
    public synchronized void execute(String filename) {
        ScriptExecutor scriptExecutor = ScriptExecutor.getInstance();
        scriptExecutor.setUser(user);
        ScriptExecutor.getInstance().executeScript(filename);
    }
}
