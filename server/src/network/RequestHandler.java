package network;

import commands.Command;
import commands.CommandWithMB;
import models.MusicBand;
import shared.Request;
import utility.Program;

public class RequestHandler {
    private Command command;
    private MusicBand musicBand;
    private String argument;
    private String username;
    private String password;

    public RequestHandler(Request request) {
        this.command = request.getCommand();
        this.musicBand = request.getMusicBand();
        this.argument = request.getStringArg();
        this.username = request.getUsername();
        this.password = request.getPassword();
    }

    public void handle() {
        Program.getInstance().updateHistory(command.getDescription());
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
