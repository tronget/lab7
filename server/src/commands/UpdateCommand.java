package commands;

import exceptions.NonexistantIdException;
import models.MusicBand;
import network.ResponseBuilder;
import network.db.DmlQueryManager;
import manager.stateManager.CollectionManager;
import network.db.UserManager;
import utility.MusicBandScanner;
import utility.Program;
import utility.ScriptExecutor;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class UpdateCommand extends CommandWithMB {
    public UpdateCommand() {
        name = "update";
        description = "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    /**
     * Метод, обновляющий элемент из коллекции по переданному ключу.
     *
     * @param id id элемента, который надо удалить
     */
    @Override
    public synchronized void execute(String id) {
        String username = user.getUsername();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        Hashtable<String, MusicBand> collection = CollectionManager.getInstance().getCollection();
        if (collection.isEmpty()) {
            responseBuilder.add(username, "Пустая коллекция.");
            return;
        }
        try {
            if (!id.chars().allMatch(Character::isDigit)) {
                throw new NumberFormatException() {
                    @Override
                    public String getMessage() {
                        return "Id должен быть числом.";
                    }
                };
            }

            String key = null;
            for (Map.Entry<String, MusicBand> entry : collection.entrySet()) {
                if (Objects.equals(entry.getValue().getID(), Long.valueOf(id))) {
                    key = entry.getKey();
                    break;
                }
            }
            if (key == null) {
                throw new NonexistantIdException(id);
            }

            if (collection.get(key).getUser_id() != new UserManager(user).getId()) {
                responseBuilder.add(username, "Вы не можете обновлять не свои объекты");
                return;
            }

            if (ScriptExecutor.getInstance().isScriptExecution()) {
                musicBand = MusicBandScanner.scan();
                if (musicBand == null) {
                    return;
                }
            }

            new DmlQueryManager(user).updateMusicById(Long.parseLong(id), musicBand);

            responseBuilder.add(username, "Элемент коллекции успешно обновлен.");

        } catch (NonexistantIdException | NumberFormatException e) {
            responseBuilder.add(username, e.getMessage());
        } catch (SQLException e) {
            responseBuilder.add(username, "Ошибка при обновлении объекта в базе данных.");
        }
    }
}
