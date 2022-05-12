package com.seailz.playtime.spigot.core.util;

import com.seailz.playtime.spigot.core.util.profile.Profile;
import games.negative.framework.util.cache.ObjectCache;

public class Cache extends ObjectCache<Profile> {
    public Cache(String path, Class<Profile[]> clazz) {
        super(path, clazz);
    }
}
