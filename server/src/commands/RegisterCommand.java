package commands;

import network.ResponseBuilder;
import network.db.DmlQueryManager;
import network.db.UserManager;
import utility.Program;

import java.sql.SQLException;

public class RegisterCommand extends Command {
    public RegisterCommand() {
        name = "register";
        description = "register {login, password} : регистрация пользователя";
    }

    @Override
    public synchronized void execute() {
        String username = user.getUsername();
        UserManager userManager = new UserManager(user);
        if (userManager.checkLogin()) {
            ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
            responseBuilder.add(username, "Пользователь c логином " + username + " уже зарегистрирован!");
            return;
        }

        try {
            new DmlQueryManager(user).insertUser();
            Program.getInstance().getResponseBuilder().add(
                    username,
                    "Пользователь успешно зарегистрирован."
            );
        } catch (SQLException e) {
            Program.getInstance().getResponseBuilder().add(
                    username,
                    "Ошибка при Регистрации пользователя."
            );
        }
    }
}
