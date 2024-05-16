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
        String username = user.getUsername();
        ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
        UserManager userManager = new UserManager(user);
        if (!userManager.checkLogin()) {
            responseBuilder.add(username, "Пользователь с логином " + user.getUsername() + " не зарегистрирован!");
            return;
        }
        if (!userManager.checkUser()) {
            responseBuilder.add(username, "Неправильный пароль!");
            return;
        }
        if (responseBuilder.get(username) != null) {

        }
        responseBuilder.add(username, "login");
    }
}
