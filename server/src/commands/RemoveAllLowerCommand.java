package commands;

import models.MusicBand;
import network.ResponseBuilder;
import network.db.DmlQueryManager;
import stateManager.CollectionManager;
import utility.MusicBandScanner;
import utility.Program;
import utility.ScriptExecutor;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

public class RemoveAllLowerCommand extends CommandWithMB {
    public RemoveAllLowerCommand() {
        name = "remove_lower";
        description = "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }

    /**
     * Метод удаляет из коллекции все элементы, меньшие, чем заданный.
     */
    @Override
    public void execute() {
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        if (ScriptExecutor.getInstance().isScriptExecution()) {
            musicBand = MusicBandScanner.scan();
            if (musicBand == null) {
                return;
            }
        }
        Hashtable<String, MusicBand> collection = CollectionManager.getInstance().getCollection();
        List<Long> removedIdList;
        Stream<MusicBand> musicBandStream = collection.values().stream();

        removedIdList = musicBandStream
                .filter(el -> el.compareTo(musicBand) < 0)
                .map(MusicBand::getID).toList();

        DmlQueryManager queryManager = new DmlQueryManager(user);
        try {
            for (long id : removedIdList) {
                queryManager.deleteMusicById(id);
            }
            responseBuilder.add("Из коллекции удалены все элементы, меньшие, чем заданный.");
        } catch (SQLException e) {
            responseBuilder.add("Ошибка при удалении объектов из базы данных!");
        }
    }
}