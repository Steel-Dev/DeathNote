package com.github.steeldev.deathnote.util;

import com.github.steeldev.deathnote.DeathNote;
import com.github.steeldev.deathnote.api.Affliction;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.chat.TextComponent.fromLegacyText;

public class Util {
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
    private static final String PREFIX = "&7[&8DeathNote&7]&r ";
    public static Random rand = new Random();
    public static String[] version;
    public static NamespacedKey deathNoteKey;
    public static NamespacedKey bookUsesKey;
    static DeathNote main = DeathNote.getInstance();
    static Map<Player, Affliction> afflicted = new HashMap<>();

    public static DeathNote getMain() {
        if (main == null) main = DeathNote.getInstance();
        return main;
    }

    public static String colorize(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            final net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = string.substring(0, matcher.start());
            final String after = string.substring(matcher.end());
            string = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean chanceOf(int chance) {
        return rand.nextInt(100) < chance;
    }

    public static boolean chanceOf(float chance) {
        return rand.nextFloat() < chance;
    }

    public static void log(String log) {
        Bukkit.getConsoleSender().sendMessage(colorize(PREFIX + log));
    }

    // Credit to : CoKoC on spigotmc for this snippet (https://www.spigotmc.org/threads/how-to-rotate-mobs-around-one-location.80498/)
    public static Location getLocationAroundCircle(Location center, double radius, double angleInRadian) {
        double x = center.getX() + radius * Math.cos(angleInRadian);
        double z = center.getZ() + radius * Math.sin(angleInRadian);
        double y = center.getY();

        Location loc = new Location(center.getWorld(), x, y, z);
        Vector difference = center.toVector().clone().subtract(loc.toVector()); // this sets the returned location's direction toward the center of the circle
        loc.setDirection(difference);

        return loc;
    }

    private static void send(CommandSender receiver, String format, Object... objects) {
        receiver.sendMessage(colorize(String.format(format, objects)));
    }

    public static void log(String format, Object... objects) {
        Bukkit.getConsoleSender().sendMessage(colorize(PREFIX + String.format(format, objects)));
    }

    public static void sendMessage(CommandSender receiver, String format, Object... objects) {
        if (receiver == null || receiver instanceof ConsoleCommandSender) {
            log(format, objects);
        } else {
            send(receiver, format, objects);
        }
    }

    public static void sendActionBar(Player receiver, String format, Object... objects) {
        if (receiver == null) return;
        receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, fromLegacyText(colorize(String.format(format, objects))));
    }

    public static void sendTitle(Player receiver, String title, String format, Object... objects) {
        if (receiver == null) return;
        receiver.sendTitle(colorize(title), colorize(String.format(format, objects)));
    }

    public static void broadcast(String format, Object... params) {
        getMain().getServer().broadcastMessage(colorize(String.format(format, params)));
    }

    public static void unregisterEvents(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public static void registerEvent(Listener listener) {
        getMain().getServer().getPluginManager().registerEvents(listener, main);
    }

    public static void registerCommand(String command, CommandExecutor commandExecutor) {
        getMain().getCommand(command).setExecutor(commandExecutor);
    }

    public static boolean isAfflicted(Player player) {
        return afflicted.containsKey(player);
    }

    public static void setAfflicted(Player player, Affliction affliction) {
        if (affliction != null) afflicted.put(player, affliction);
        else afflicted.remove(player);
    }

    public static Affliction getPlayerAffliction(Player player) {
        return afflicted.get(player);
    }

    public static boolean isDeathNote(ItemStack item) {
        if (!item.getType().equals(Material.WRITABLE_BOOK)) return false;
        if (item.getItemMeta() == null) return false;
        if (!item.getItemMeta().getPersistentDataContainer().has(deathNoteKey, PersistentDataType.STRING)) return false;
        return item.getItemMeta().getPersistentDataContainer().get(deathNoteKey, PersistentDataType.STRING).equals("death_note");
    }

    // Thanks to ShaneBee for providing these functions

    /**
     * Check if server is running a minimum Minecraft version
     *
     * @param major Major version to check (Most likely just going to be 1)
     * @param minor Minor version to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor) {
        return isRunningMinecraft(major, minor, 0);
    }

    /**
     * Check if server is running a minimum Minecraft version
     *
     * @param major    Major version to check (Most likely just going to be 1)
     * @param minor    Minor version to check
     * @param revision Revision to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor, int revision) {
        if (version == null) version = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.");
        int maj = Integer.parseInt(version[0]);
        int min = Integer.parseInt(version[1]);
        int rev;
        try {
            rev = Integer.parseInt(version[2]);
        } catch (Exception ignore) {
            rev = 0;
        }
        return maj > major || min > minor || (min == minor && rev >= revision);
    }

    public static String getVersionString() {
        return version[0] + "." + version[1] + "." + version[2];
    }
}
