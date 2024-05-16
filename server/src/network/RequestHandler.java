package network;

import commands.Command;
import commands.CommandWithMB;
import models.MusicBand;
import network.db.User;
import shared.Request;
import manager.stateManager.CommandsManager;
import utility.HashSHA512;
import utility.Program;

public class RequestHandler {
    private String commandName;
    private MusicBand musicBand;
    private String argument;
    private String username;
    private String password;

    public RequestHandler(Request request) {
        this.commandName = request.getCommandName();
        this.musicBand = request.getMusicBand();
        this.argument = request.getStringArg();
        this.username = request.getUsername();
        this.password = HashSHA512.hashString(request.getPassword());
    }

    public void handle() {
        Command command = CommandsManager.getCommandByName(commandName);
        if (command == null) {
            Program.getInstance().getResponseBuilder().add(username, "Неизвестная команда!");
            return;
        }
        User user = new User(username, password);
        command.setUser(user);

        if (command.getName().equals("login") || command.getName().equals("register")) {
            command.execute();
            return;
        }


        Program.getInstance().updateHistory(username, commandName);


        if (musicBand != null) {
            ((CommandWithMB) command).setMusicBand(musicBand);
        }
        if (argument != null) {
            command.execute(argument);
        } else {
            command.execute();
        }
    }


}
