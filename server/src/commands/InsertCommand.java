package commands;

import exceptions.ExistantKeyException;
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

public class InsertCommand extends CommandWithMB {
    public InsertCommand() {
        name = "insert";
        description = "insert null {element} : добавить новый элемент с заданным ключом";
    }

    /**
     * Метод добавляет новый элемент с заданным ключом.
     *
     * @param key ключ по которому элемент запишется в коллекцию
     */
    @Override
    public synchronized void execute(String key) {
        String username = user.getUsername();
        int user_id = new UserManager(user).getId();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        try {
            if (CollectionManager.getInstance().hasSuchKey(key)) {
                throw new ExistantKeyException(key);
            }
            if (ScriptExecutor.getInstance().isScriptExecution()) {
                musicBand = MusicBandScanner.scan();
                if (musicBand == null) {
                    return;
                }
            }
            musicBand.setUser_id(user_id);
            new DmlQueryManager(user).insertMusic(key, musicBand);
            responseBuilder.add(username, "Элемент сохранен в коллекции.");

        } catch (ExistantKeyException e) {
            responseBuilder.add(username, e.getMessage());
        } catch (SQLException e) {
            responseBuilder.add(username, "Ошибка при добавлении объекта в базу данных!");
        }
    }
}
