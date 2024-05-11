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
    public void execute() {
        UserManager userManager = new UserManager(user);
        if (userManager.checkLogin()) {
            ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
            responseBuilder.add("Пользователь c логином " + user.getUsername() + " уже зарегистрирован!");
            return;
        }

        try {
            new DmlQueryManager(user).insertUser();
            Program.getInstance().getResponseBuilder().add(
                    "Пользователь успешно зарегистрирован."
            );
        } catch (SQLException e) {
            Program.getInstance().getResponseBuilder().add(
                    "Ошибка при Регистрации пользователя."
            );
        }
    }
}
