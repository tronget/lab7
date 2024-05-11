package commands;

import exceptions.NonexistantIdException;
import models.MusicBand;
import network.ResponseBuilder;
import network.db.DmlQueryManager;
import stateManager.CollectionManager;
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
    public void execute(String id) {
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        Hashtable<String, MusicBand> collection = CollectionManager.getInstance().getCollection();
        if (collection.isEmpty()) {
            responseBuilder.add("Пустая коллекция.");
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

            if (ScriptExecutor.getInstance().isScriptExecution()) {
                musicBand = MusicBandScanner.scan();
                if (musicBand == null) {
                    return;
                }
            }

            new DmlQueryManager(user).updateMusicById(Long.parseLong(id), musicBand);

            responseBuilder.add("Элемент коллекции успешно обновлен.");

        } catch (NonexistantIdException | NumberFormatException e) {
            responseBuilder.add(e.getMessage());
        } catch (SQLException e) {
            responseBuilder.add("Ошибка при обновлении объекта в базе данных.");
        }
    }
}
