package commands;

import models.MusicBand;
import network.ResponseBuilder;
import manager.stateManager.CollectionManager;
import utility.Program;

import java.util.Hashtable;

public class PrintAscendingCommand extends Command {
    public PrintAscendingCommand() {
        name = "print_ascending";
        description = "print_ascending : вывести элементы коллекции в порядке возрастания";
    }

    /**
     * Метод выводит элементы коллекции в порядке возрастания.
     */
    @Override
    public void execute() {
        String username = user.getUsername();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        Hashtable<String, MusicBand> collection = CollectionManager.getInstance().getCollection();
        if (collection.isEmpty()) {
            responseBuilder.add(username, "Пустая коллекция.");
            return;
        }
        collection.values().stream().sorted().forEach(
                musicBand -> responseBuilder.add(username, musicBand.toString())
        );
    }
}
