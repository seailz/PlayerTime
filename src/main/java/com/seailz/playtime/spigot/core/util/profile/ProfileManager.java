package com.seailz.playtime.spigot.core.util.profile;

import games.negative.framework.util.cache.ObjectCache;

public class ProfileManager extends ObjectCache<Profile> {
    public ProfileManager(String path, Class<Profile[]> clazz) {
        super(path, clazz);
    }
}
