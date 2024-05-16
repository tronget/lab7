package commands;

import exceptions.NonexistentKeyException;
import models.MusicBand;
import network.ResponseBuilder;
import network.db.DmlQueryManager;
import manager.stateManager.CollectionManager;
import network.db.UserManager;
import utility.Program;

import java.sql.SQLException;
import java.util.Hashtable;

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
        String username = user.getUsername();
        Hashtable<String, MusicBand> collection = CollectionManager.getInstance().getCollection();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        try {
            if (!CollectionManager.getInstance().hasSuchKey(key)) {
                throw new NonexistentKeyException(key);
            }
            if (collection.get(key).getUser_id() != new UserManager(user).getId()) {
                responseBuilder.add(username, "Вы не можете удалить объект, который вы не создавали.");
                return;
            }
            new DmlQueryManager(user).deleteMusicByKey(key);
            responseBuilder.add(username, "Элемент с ключом %s удален успешно.".formatted(key));
        } catch (NonexistentKeyException e) {
            responseBuilder.add(username, e.getMessage());
        } catch (SQLException e) {
            responseBuilder.add(username, "Ошибка при удалении элемента из базы данных!");
        }
    }
}
