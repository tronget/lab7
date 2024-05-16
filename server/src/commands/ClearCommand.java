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
        String username = user.getUsername();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        try {
            new DmlQueryManager(user).deleteMusicByUserId();
            responseBuilder.add(username, "Объекты пользователя " + username + " удалены.");
        } catch (SQLException e) {
            responseBuilder.add(username, "Ошибка при удалении объектов пользователя " + username + "!");
        }
    }
}
