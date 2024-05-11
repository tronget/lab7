package commands;

import network.ResponseBuilder;
import network.db.UserManager;
import utility.Program;

public class LoginCommand extends Command {
    public LoginCommand() {
        name = "login";
        description = "login : авторизация";
    }
    @Override
    public void execute() {
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        UserManager userManager = new UserManager(user);
        if (!userManager.checkLogin()) {
            responseBuilder.add("Пользователь с логином " + user.getUsername() + " не зарегистрирован!");
            return;
        }
        if (!userManager.checkUser()) {
            responseBuilder.add("Неправильный пароль!");
            return;
        }
        responseBuilder.add("login");
    }
}
