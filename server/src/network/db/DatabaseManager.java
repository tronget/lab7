package network.db;

import models.MusicBand;

import java.util.HashMap;
import java.util.Hashtable;

public class DatabaseManager {
    private static HashMap<String, String> usersTable;
    private static Hashtable<String, MusicBand> musicBandsTable;

    static {
        QueryManager queryManager = new QueryManager();
        usersTable = queryManager.getUsersTable();
        musicBandsTable = queryManager.getMusicbandTable();
    }

    public static HashMap<String, String> getUsersTable() {
        return new HashMap<>(usersTable);
    }

    public static Hashtable<String, MusicBand> getMusicBandsTable() {
        return new Hashtable<>(musicBandsTable);
    }
}
