package network.db;

import models.Coordinates;
import models.MusicBand;
import models.MusicGenre;
import models.Studio;
import utility.Program;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;

public class QueryManager {
    private final String SELECT_MUSIC_QUERY = "SELECT * FROM music_band";
    private final String SELECT_USERS_QUERY = "SELECT * FROM users";

    protected final Connection connection = Program.getInstance().getDatabaseHandler().getConnection();


    public Hashtable<String, MusicBand> getMusicbandTable() {
        Hashtable<String, MusicBand> musicBandTable = new Hashtable<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_MUSIC_QUERY);
            while (rs.next()) {
                long id = rs.getLong("id");
                String key = rs.getString("obj_key");
                String name = rs.getString("name");
                int xCoord = rs.getInt("x_coord");
                double yCoord = rs.getDouble("y_coord");
                Date creationDate = rs.getDate("creation_date");
                int numberOfParticipants = rs.getInt("number_of_participants");
                LocalDateTime establishmentDate = rs.getTimestamp("establishment_date").toLocalDateTime();
                MusicGenre mg = MusicGenre.valueOf(rs.getString("music_genre"));
                String studioName = rs.getString("studio_name");
                String studioAddress = rs.getString("studio_address");

                Coordinates coordinates = new Coordinates(xCoord, yCoord);
                Studio studio = new Studio(studioName, studioAddress);
                MusicBand musicBand = new MusicBand(id, name, coordinates, creationDate, numberOfParticipants, establishmentDate, mg, studio);
                musicBandTable.put(key, musicBand);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении SQL-запроса.");
        }
        return musicBandTable;
    }

    public HashMap<Integer, User> getUsersTable() {
        HashMap<Integer, User> usersMap = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_USERS_QUERY);
            while (rs.next()) {
                int id = rs.getInt("id");
                String userName = rs.getString("name");
                String password = rs.getString("password");
                User user = new User(userName, password);
                usersMap.put(id, user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении SQL-запроса.");
        }
        return usersMap;
    }

}