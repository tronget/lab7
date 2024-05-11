package commands;

import exceptions.ExistantKeyException;
import models.MusicBand;
import network.ResponseBuilder;
import network.db.DmlQueryManager;
import stateManager.CollectionManager;
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
    public void execute(String key) {
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        Hashtable<String, MusicBand> collection = CollectionManager.getInstance().getCollection();
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

            new DmlQueryManager(user).insertMusic(key, musicBand);

            responseBuilder.add("Элемент сохранен в коллекции.");

        } catch (ExistantKeyException e) {
            responseBuilder.add(e.getMessage());
        } catch (SQLException e) {
            responseBuilder.add("Ошибка при добавлении объекта в базу данных!");
        }
    }
}
