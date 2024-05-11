package shared;

import models.MusicBand;
import network.db.User;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 666L;

    private MusicBand musicBandArgument;
    private String commandName;
    private String stringArgument;
    private String username;
    private String password;

    public Request(String commandName) {
        this.commandName = commandName;
    }

    public Request(String commandName, MusicBand musicBandArgument) {
        this.commandName = commandName;
        this.musicBandArgument = musicBandArgument;
    }

    public Request(String commandName, String stringArgument) {
        this.commandName = commandName;
        this.stringArgument = stringArgument;
    }
    public Request(String commandName, String stringArgument, MusicBand musicBandArgument) {
        this.commandName = commandName;
        this.stringArgument = stringArgument;
        this.musicBandArgument = musicBandArgument;
    }

    public MusicBand getMusicBand() {
        return musicBandArgument;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBandArgument = musicBand;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
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
    public void setUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public String toString() {
        return "Request: " + commandName + " " + musicBandArgument + " " + stringArgument;
    }
}
