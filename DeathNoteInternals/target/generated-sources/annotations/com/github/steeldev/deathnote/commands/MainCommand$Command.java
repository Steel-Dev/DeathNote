package com.github.steeldev.deathnote.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;

import org.bukkit.entity.Player;

// This class was automatically generated by the CommandAPI
public class MainCommand$Command {

@SuppressWarnings("unchecked")
public static void register() {

    new CommandAPICommand("deathnote")
        .withArguments(
            new MultiLiteralArgument("help")
                .setListed(false)
        )
        .withPermission("deathnote.admin")
        .withAliases("dn")
        .executes((sender, args) -> {
            MainCommand.deathNote(sender);
        })
        .register();

    new CommandAPICommand("deathnote")
        .withArguments(
            new MultiLiteralArgument("reload")
                .setListed(false)
        )
        .withPermission("deathnote.admin")
        .withAliases("dn")
        .executes((sender, args) -> {
            MainCommand.reload(sender);
        })
        .register();

    new CommandAPICommand("deathnote")
        .withArguments(
            new MultiLiteralArgument("give")
                .setListed(false)
        )
        .withPermission("deathnote.admin")
        .withAliases("dn")
        .withArguments(new PlayerArgument("target"))
        .executes((sender, args) -> {
            MainCommand.give(sender, (Player) args[0]);
        })
        .register();

    new CommandAPICommand("deathnote")
        .withArguments(
            new MultiLiteralArgument("give")
                .setListed(false)
        )
        .withPermission("deathnote.admin")
        .withAliases("dn")
        .executes((sender, args) -> {
            MainCommand.give(sender);
        })
        .register();

    new CommandAPICommand("deathnote")
        .withArguments(
            new MultiLiteralArgument("kills")
                .setListed(false)
        )
        .withPermission("deathnote.admin")
        .withAliases("dn")
        .executes((sender, args) -> {
            MainCommand.kills(sender);
        })
        .register();

    new CommandAPICommand("deathnote")
        .withArguments(
            new MultiLiteralArgument("kills")
                .setListed(false)
        )
        .withPermission("deathnote.admin")
        .withAliases("dn")
        .withArguments(new PlayerArgument("player"))
        .executes((sender, args) -> {
            MainCommand.kills(sender, (Player) args[0]);
        })
        .register();

    new CommandAPICommand("deathnote")
        .withArguments(
            new MultiLiteralArgument("afflictions")
                .setListed(false)
        )
        .withPermission("deathnote.admin")
        .withAliases("dn")
        .executes((sender, args) -> {
            MainCommand.afflictins(sender);
        })
        .register();

    }

}