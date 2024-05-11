package commands;

import exceptions.ExtraArgumentException;
import exceptions.NecessaryArgumentException;
import network.ResponseBuilder;
import network.db.User;
import utility.Program;

public abstract class Command {
    protected String name;
    protected String description;
    protected User user;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void execute() {
        try {
            throw new NecessaryArgumentException(name);
        } catch (NecessaryArgumentException e) {
            ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
            responseBuilder.add(e.getMessage());
        }
    };

    public void execute(String arg) {
        try {
            throw new ExtraArgumentException(name);
        } catch (ExtraArgumentException e) {
            ResponseBuilder responseBuilder = Program.getInstance().getResponseBuilder();
            responseBuilder.add(e.getMessage());
        }
    }
}
