package network.db;

import models.MusicBand;
import org.postgresql.util.GettableHashMap;
import utility.Program;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;

public class QueryManager {
    public final String MUSIC_BANDS_QUERY = "SELECT * FROM music_bands";
    public final String USERS_QUERY = "SELECT * FROM users";
    public final String INSERT_USER_QUERY = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    private final Connection connection = Program.getInstance().getDatabaseHandler().getConnection();

    public void insertUser(String login, String password) {

    }

    public Hashtable<String, MusicBand> getMusicbandTable() {
        Hashtable<String, MusicBand> hashtable = new Hashtable<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(MUSIC_BANDS_QUERY);
            while (rs.next()) {

            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении SQL-запроса.");
        }
        return hashtable;
    }

    public HashMap<String, String> getUsersTable() {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(USERS_QUERY);
            while (rs.next()) {
                String user = rs.getString(1),
                        passwd = rs.getString(2);
                hashMap.put(user, passwd);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении SQL-запроса.");
        }
        return hashMap;
    }

}