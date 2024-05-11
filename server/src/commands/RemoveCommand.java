package commands;

import exceptions.NonexistentKeyException;
import network.ResponseBuilder;
import network.db.DmlQueryManager;
import stateManager.CollectionManager;
import utility.Program;

import java.sql.SQLException;

public class RemoveCommand extends Command {
    public RemoveCommand() {
        name = "remove_key";
        description = "remove_key null : удалить элемент из коллекции по его ключу";
    }

    /**
     * Метод, удаляющий элемент из коллекции по переданному ключу.
     * @param key ключ элемента, который надо удалить
     */
    @Override
    public void execute(String key) {
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        try {
            if (!CollectionManager.getInstance().hasSuchKey(key)) {
                throw new NonexistentKeyException(key);
            }
            new DmlQueryManager(user).deleteMusicByKey(key);
            responseBuilder.add("Элемент с ключом %s удален успешно.".formatted(key));
        } catch (NonexistentKeyException e) {
            responseBuilder.add(e.getMessage());
        } catch (SQLException e) {
            responseBuilder.add("Ошибка при удалении элемента из базы данных!");
        }
    }
}
