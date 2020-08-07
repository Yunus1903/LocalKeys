package com.yunus1903.localkeys.client.misc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Collections;
import java.util.Map;

/**
 * @author Yunus1903
 * @since 07/08/2020
 */
public class KeyboardLayout
{
    private final String name;
    private final BiMap<Integer, Integer> mapping;

    public KeyboardLayout(String name, Map<Integer, Integer> mapping)
    {
        this.name = name;
        this.mapping = HashBiMap.create(mapping);
    }

    public String getName()
    {
        return name;
    }

    public Map<Integer, Integer> getMapping()
    {
        return Collections.unmodifiableMap(mapping);
    }

    public Map<Integer, Integer> getReverseMappings()
    {
        return Collections.unmodifiableMap(mapping.inverse());
    }
}
