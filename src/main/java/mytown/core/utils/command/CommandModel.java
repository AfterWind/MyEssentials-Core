package mytown.core.utils.command;

import net.minecraft.command.ICommandSender;
import scala.actors.threadpool.Arrays;

import java.lang.reflect.Method;
import java.util.List;

public class CommandModel extends CmdBase {
    private final Command cmd;
    private final Method method;

    private List commandAliasCache = null;

    public CommandModel(Command cmd, Method method) {
        this.cmd = cmd;
        this.method = method;
    }

    @Override
    public List getCommandAliases() {
        if (commandAliasCache == null) {
            commandAliasCache = Arrays.asList(cmd.alias());
        }
        return commandAliasCache;
    }

    @Override
    public String getPermissionNode() {
        return cmd.permission();
    }

    @Override
    public boolean canConsoleUseCommand() {
        return cmd.console();
    }

    @Override
    public boolean canRConUseCommand() {
        return cmd.rcon();
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return cmd.commandblocks();
    }

    @Override
    public String getCommandName() {
        return cmd.name();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + cmd.name() + " " + cmd.syntax();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            method.invoke(null, sender, Arrays.asList(args));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}