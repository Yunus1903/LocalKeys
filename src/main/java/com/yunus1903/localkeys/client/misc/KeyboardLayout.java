package com.yunus1903.localkeys.client.misc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Collections;
import java.util.Map;

/**
 * This class defines a keyboard layout with mappings which are derived from te default GLFW layout (QWERTY)
 * @author Yunus1903
 * @since 07/08/2020
 */
public class KeyboardLayout
{
    private final String name;
    private final BiMap<Integer, Integer> mapping;

    /**
     * Constructor for the {@link KeyboardLayout} instance
     * @param name Unique name of the {@link KeyboardLayout layout} (this is used to visualize the layout in-game and to save locally)
     * @param mapping The QWERTY derived mapping of this {@link KeyboardLayout layout}
     */
    public KeyboardLayout(String name, Map<Integer, Integer> mapping)
    {
        this.name = name;
        this.mapping = HashBiMap.create(mapping);
    }

    /**
     * @return The name of this {@link KeyboardLayout layout}
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The mappings of this {@link KeyboardLayout layout}
     */
    public Map<Integer, Integer> getMapping()
    {
        return Collections.unmodifiableMap(mapping);
    }

    /**
     * @return The inverted mappings of this {@link KeyboardLayout layout}
     */
    public Map<Integer, Integer> getReverseMappings()
    {
        return Collections.unmodifiableMap(mapping.inverse());
    }
}