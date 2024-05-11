package network.db;

import models.MusicBand;
import stateManager.CollectionManager;

import java.util.HashMap;
import java.util.Hashtable;

public class DatabaseManager {
    private static HashMap<Integer, User> usersMap;
    private static Hashtable<String, MusicBand> musicBandsTable;

    private static final QueryManager queryManager = new QueryManager();

    static {
        updateUsersTable();
        updateMusicBandsTable();
    }

    public static void updateUsersTable() {
        usersMap = queryManager.getUsersTable();
        UserManager.setUsersMap(usersMap);
    }

    public static void updateMusicBandsTable() {
        musicBandsTable = queryManager.getMusicbandTable();
        CollectionManager.getInstance().setCollection(musicBandsTable);
    }

    public static HashMap<Integer, User> getUsersMap() {
        return new HashMap<>(usersMap);
    }

    public static Hashtable<String, MusicBand> getMusicBandsTable() {
        return new Hashtable<>(musicBandsTable);
    }
}
