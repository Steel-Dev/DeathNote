package com.github.steeldev.deathnote.api;

import com.github.steeldev.deathnote.api.events.AfflictionRegisteredEvent;
import com.github.steeldev.deathnote.api.events.AfflictionUnregisteredEvent;
import org.bukkit.Bukkit;

import java.util.*;

/**
 * The main manager class for Afflictions.
 *
 * @see Affliction
 */
public class AfflictionManager {
    private static final Map<String, Affliction> registry = new HashMap<>();

    static Affliction defaultAffliction;

    /**
     * Mass-Register afflictions and automatically refresh
     * the afflictions book.
     *
     * @param afflictions Afflictions to register
     */
    public static void register(Affliction... afflictions) {
        for (Affliction affliction : afflictions) {
            register(affliction);
        }
    }

    /**
     * Register a single affliction
     * This will not automatically refresh the book
     * to avoid any issues/lag.
     * <p>
     * to refresh, call AfflictionManager#refreshAfflictionsList()
     *
     * @param affliction Affliction to register
     */
    public static void register(Affliction affliction) {
        AfflictionRegisteredEvent afflictionRegisteredEvent = new AfflictionRegisteredEvent(affliction);
        Bukkit.getServer().getPluginManager().callEvent(afflictionRegisteredEvent);
        if (registry.containsKey(affliction.getKey())) {
            Affliction tempAff = get(affliction.getKey());
            if (affliction.getRegisteredBy().equals(tempAff.getRegisteredBy()))
                registry.replace(affliction.getKey(), affliction);
        } else registry.put(affliction.getKey(), affliction);
    }

    /**
     * Unregister an affliction by the given key
     *
     * @param key Afflictions key
     */
    public static void unregister(String key) {
        if (isAfflictionRegistered(key)) {
            Affliction tempAffliction = get(key);
            AfflictionUnregisteredEvent afflictionUnregisteredEvent = new AfflictionUnregisteredEvent(tempAffliction);
            Bukkit.getServer().getPluginManager().callEvent(afflictionUnregisteredEvent);
            registry.remove(key);
        }
    }

    /**
     * Get an affliction by its key
     *
     * @param key Key to get
     * @return Affliction
     */
    public static Affliction get(String key) {
        return registry.get(key);
    }

    /**
     * Check if an affliction is registered
     * Automatically called by the unregister fuction.
     *
     * @param key Key to unregister
     * @return Boolean
     */
    public static boolean isAfflictionRegistered(String key) {
        return get(key) != null;
    }

    /**
     * Get an affliction by a trigger word
     *
     * @param trigger The trigger to look for
     * @return Affliction that has the inputted trigger, if none found, then null
     */
    public static Affliction getAfflictionByTriggerWord(String trigger) {
        for (Affliction affliction : getRegistered()) {
            if (affliction.getTriggers().contains(trigger))
                return affliction;
        }
        return null;
    }

    /**
     * Get a random out of all registered afflictions
     *
     * @return Random affliction
     */
    public static Affliction getRandomAffliction() {
        Random random = new Random();
        return getRegistered().get(random.nextInt(getRegistered().size()));
    }

    /**
     * Get all registered afflictions
     *
     * @return Registered afflictions
     */
    public static List<Affliction> getRegistered() {
        return new ArrayList<>(registry.values());
    }

    /**
     * Get the default affliction
     * the default is what will trigger if the
     * input affliction is invalid
     * when using the book.
     *
     * @return Default affliction
     */
    public static Affliction getDefaultAffliction() {
        return defaultAffliction;
    }

    /**
     * The default affliction is what will trigger if
     * the inputted afflicted trigger is invalid
     *
     * @param defaultAffliction The Affliction to set as the default
     */
    public static void setDefaultAffliction(Affliction defaultAffliction) {
        AfflictionManager.defaultAffliction = defaultAffliction;
    }
}
