package shared;

import commands.Command;
import models.MusicBand;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 666L;

    private MusicBand musicBandArgument;
    private Command command;
    private String stringArgument;
    private String username;
    private String password;

    public Request(Command command) {
        this.command = command;
    }

    public Request(Command command, MusicBand musicBandArgument) {
        this.command = command;
        this.musicBandArgument = musicBandArgument;
    }

    public Request(Command command, String stringArgument) {
        this.command = command;
        this.stringArgument = stringArgument;
    }
    public Request(Command command, String stringArgument, MusicBand musicBandArgument) {
        this.command = command;
        this.stringArgument = stringArgument;
        this.musicBandArgument = musicBandArgument;
    }

    public MusicBand getMusicBand() {
        return musicBandArgument;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBandArgument = musicBand;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getStringArg() {
        return stringArgument;
    }

    public void setStringArg(String argument) {
        this.stringArgument = argument;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Request: " + command + " " + musicBandArgument + " " + stringArgument;
    }
}
