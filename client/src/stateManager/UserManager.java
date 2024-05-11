package stateManager;

import builder.AbstractBuilder;
import models.User;
import shared.Request;
import utility.Program;

public class UserManager {
    private static User user;

    public static void login() {
        User inputUser = buildUser();
        Request request = new Request("login");
        request.setUser(inputUser);
        Program.getInstance().getRequestLogic().send(request);
        String response = Program.getInstance().getResponseLogic().receive();
        if (response.trim().equals("login")) {
            user = inputUser;
            System.out.println("Выполнен вход в аккаунт " + user.getUsername() + ".");
            return;
        }
        System.out.println(response);
    }

    public static void register() {
        User inputUser = buildUser();
        Request request = new Request("register");
        request.setUser(inputUser);
        Program.getInstance().getRequestLogic().send(request);
        String response = Program.getInstance().getResponseLogic().receive();
        System.out.println(response);
    }

    public static User buildUser() {
        String username = AbstractBuilder.buildString("Введите логин");
        String password = AbstractBuilder.buildString("Введите пароль");
        return new User(username, password);
    }

    public static boolean isLogin() {
        return user != null;
    }

    public static User getUser() {
        return user;
    }
}
