package commands;

import network.ResponseBuilder;
import network.db.DmlQueryManager;
import utility.Program;

import java.sql.SQLException;

public class ClearCommand extends Command {
    public ClearCommand() {
        name = "clear";
        description = "clear : очистить коллекцию";
    }

    /**
     * Метод, очищающий коллекцию.
     */
    @Override
    public void execute() {
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        try {
            new DmlQueryManager(user).deleteMusicByUserId();
            responseBuilder.add("Объекты пользователя " + user.getUsername() + " удалены.");
        } catch (SQLException e) {
            responseBuilder.add("Ошибка при удалении объектов пользователя " + user.getUsername() + "!");
        }
    }
}
