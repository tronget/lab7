package network.db;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private String username;
    private String password;
    private static HashMap<Integer, User> usersMap = DatabaseManager.getUsersMap();

    public UserManager(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public void setUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public static void setUsersMap(HashMap<Integer, User> usersMap) {
        UserManager.usersMap = new HashMap<>(usersMap);
    }

    public boolean checkUser() {
        Collection<User> users = usersMap.values();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public boolean checkLogin() {
        Collection<User> users = usersMap.values();
        return users.stream().map(User::getUsername).toList().contains(username);
    }

    public int getId() {
        if (!checkLogin()) {
            return -1;
        }
        Connection connection = new QueryManager().connection;
        int id = 0;
        for (Map.Entry<Integer, User> entry : usersMap.entrySet()) {
            id = entry.getKey();
            User user = entry.getValue();
            if (user.getUsername().equals(username)) {
                break;
            }
        }
        return id;
    }
}
