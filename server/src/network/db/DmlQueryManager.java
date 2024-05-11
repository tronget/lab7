package network.db;

import models.MusicBand;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DmlQueryManager extends QueryManager {
    private final User user;
    private final int userId;
    private final String INSERT_USER_QUERY = "INSERT INTO users (name, password) VALUES (?, ?)";
    private final String INSERT_MUSIC_QUERY = "INSERT INTO music_band " +
            "(user_id, obj_key, name, x_coord, y_coord, " +
            "number_of_participants, establishment_date, music_genre, " +
            "studio_name, studio_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_MUSIC_QUERY = "DELETE FROM music_band WHERE user_id = ?";
    private final String DELETE_MUSIC_BY_KEY_QUERY = DELETE_MUSIC_QUERY + " AND obj_key = ?";
    private final String DELETE_MUSIC_BY_ID_QUERY = DELETE_MUSIC_QUERY + " AND id = ?";

    private final String UPDATE_MUSIC_BY_ID = "UPDATE music_band SET name = ?," +
            "x_coord = ?, y_coord = ?, number_of_participants = ?," +
            "establishment_date = ?, music_genre = ?, studio_name = ?," +
            "studio_address = ? WHERE id = ? AND user_id = ?";

    public DmlQueryManager(@NonNull User user) {
        this.user = user;
        this.userId = new UserManager(user).getId();
    }

    public void insertUser() throws SQLException {
        if (user == null) {
            return;
        }
        PreparedStatement ps = connection.prepareStatement(INSERT_USER_QUERY);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.executeUpdate();
        DatabaseManager.updateUsersTable();
    }
    public void insertMusic(String key, MusicBand musicBand) throws SQLException {
        if (user == null) {
            return;
        }
        PreparedStatement ps = connection.prepareStatement(INSERT_MUSIC_QUERY);
        ps.setInt(1, new UserManager(user).getId());
        ps.setString(2, key);
        ps.setString(3, musicBand.getName());
        ps.setInt(4, musicBand.getCoordinates().getX());
        ps.setDouble(5, musicBand.getCoordinates().getY());
        ps.setInt(6, musicBand.getNumberOfParticipants());
        ps.setTimestamp(7, Timestamp.valueOf(musicBand.getEstablishmentDate()));
        ps.setString(8, musicBand.getGenre().toString());
        ps.setString(9, musicBand.getStudio().getName());
        ps.setString(10, musicBand.getStudio().getAddress());
        ps.executeUpdate();
        DatabaseManager.updateMusicBandsTable();
    }

    public void deleteMusicByUserId() throws SQLException {
        if (user == null) {
            return;
        }
        PreparedStatement ps = connection.prepareStatement(DELETE_MUSIC_QUERY);
        ps.setInt(1, userId);
        ps.executeUpdate();
        DatabaseManager.updateMusicBandsTable();
    }

    public void deleteMusicByKey(String key) throws SQLException {
        if (user == null) {
            return;
        }
        PreparedStatement ps = connection.prepareStatement(DELETE_MUSIC_BY_KEY_QUERY);
        ps.setInt(1, userId);
        ps.setString(2, key);
        ps.executeUpdate();
        DatabaseManager.updateMusicBandsTable();
    }

    public void deleteMusicById(long id) throws SQLException {
        if (user == null) {
            return;
        }
        PreparedStatement ps = connection.prepareStatement(DELETE_MUSIC_BY_ID_QUERY);
        ps.setInt(1, userId);
        ps.setLong(2, id);
        ps.executeUpdate();
        DatabaseManager.updateMusicBandsTable();
    }


    public void updateMusicById(long id, MusicBand musicBand) throws SQLException {
        if (user == null) {
            return;
        }
        PreparedStatement ps = connection.prepareStatement(UPDATE_MUSIC_BY_ID);
        ps.setString(1, musicBand.getName());
        ps.setInt(2, musicBand.getCoordinates().getX());
        ps.setDouble(3, musicBand.getCoordinates().getY());
        ps.setInt(4, musicBand.getNumberOfParticipants());
        ps.setTimestamp(5, Timestamp.valueOf(musicBand.getEstablishmentDate()));
        ps.setString(6, musicBand.getGenre().toString());
        ps.setString(7, musicBand.getStudio().getName());
        ps.setString(8, musicBand.getStudio().getAddress());
        ps.setLong(9, id);
        ps.setInt(10, userId);
        ps.executeUpdate();
        DatabaseManager.updateMusicBandsTable();
    }

}
