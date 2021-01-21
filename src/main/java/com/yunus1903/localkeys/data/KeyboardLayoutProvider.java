package com.yunus1903.localkeys.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.yunus1903.localkeys.LocalKeys;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Mod {@link DataGenerator datagenerator} {@link net.minecraft.data.IDataProvider provider} for {@link com.yunus1903.localkeys.client.misc.KeyboardLayout keyboard layout} mappings
 * @author Yunus1903
 * @since 07/08/2020
 */
public class KeyboardLayoutProvider implements IDataProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    private final List<Integer> defaultKeys = new ArrayList<>();
    private final Map<String, Map<Integer, Integer>> layouts = new HashMap<>();

    public KeyboardLayoutProvider(DataGenerator generatorIn)
    {
        this.generator = generatorIn;
    }

    protected void registerMappings()
    {
        setDefault(GLFW_KEY_UNKNOWN, GLFW_KEY_SPACE, GLFW_KEY_APOSTROPHE, GLFW_KEY_COMMA, GLFW_KEY_MINUS,
                GLFW_KEY_PERIOD, GLFW_KEY_SLASH, GLFW_KEY_0, GLFW_KEY_1, GLFW_KEY_2, GLFW_KEY_3,
                GLFW_KEY_4, GLFW_KEY_5, GLFW_KEY_6, GLFW_KEY_7, GLFW_KEY_8,
                GLFW_KEY_9, GLFW_KEY_SEMICOLON, GLFW_KEY_EQUAL, GLFW_KEY_A, GLFW_KEY_B,
                GLFW_KEY_C, GLFW_KEY_D, GLFW_KEY_E, GLFW_KEY_F, GLFW_KEY_G,
                GLFW_KEY_H, GLFW_KEY_I, GLFW_KEY_J, GLFW_KEY_K, GLFW_KEY_L,
                GLFW_KEY_M, GLFW_KEY_N, GLFW_KEY_O, GLFW_KEY_P, GLFW_KEY_Q,
                GLFW_KEY_R, GLFW_KEY_S, GLFW_KEY_T, GLFW_KEY_U, GLFW_KEY_V,
                GLFW_KEY_W, GLFW_KEY_X, GLFW_KEY_Y, GLFW_KEY_Z, GLFW_KEY_LEFT_BRACKET,
                GLFW_KEY_BACKSLASH, GLFW_KEY_RIGHT_BRACKET, GLFW_KEY_GRAVE_ACCENT, GLFW_KEY_WORLD_1, GLFW_KEY_WORLD_2,
                GLFW_KEY_ESCAPE, GLFW_KEY_ENTER, GLFW_KEY_TAB, GLFW_KEY_BACKSPACE, GLFW_KEY_INSERT,
                GLFW_KEY_DELETE, GLFW_KEY_RIGHT, GLFW_KEY_LEFT, GLFW_KEY_DOWN, GLFW_KEY_UP,
                GLFW_KEY_PAGE_UP, GLFW_KEY_PAGE_DOWN, GLFW_KEY_HOME, GLFW_KEY_END, GLFW_KEY_CAPS_LOCK,
                GLFW_KEY_SCROLL_LOCK, GLFW_KEY_NUM_LOCK, GLFW_KEY_PRINT_SCREEN, GLFW_KEY_PAUSE, GLFW_KEY_F1,
                GLFW_KEY_F2, GLFW_KEY_F3, GLFW_KEY_F4, GLFW_KEY_F5, GLFW_KEY_F6,
                GLFW_KEY_F7, GLFW_KEY_F8, GLFW_KEY_F9, GLFW_KEY_F10, GLFW_KEY_F11,
                GLFW_KEY_F12, GLFW_KEY_F13, GLFW_KEY_F14, GLFW_KEY_F15, GLFW_KEY_F16,
                GLFW_KEY_F17, GLFW_KEY_F18, GLFW_KEY_F19, GLFW_KEY_F20, GLFW_KEY_F21,
                GLFW_KEY_F22, GLFW_KEY_F23, GLFW_KEY_F24, GLFW_KEY_F25, GLFW_KEY_KP_0,
                GLFW_KEY_KP_1, GLFW_KEY_KP_2, GLFW_KEY_KP_3, GLFW_KEY_KP_4, GLFW_KEY_KP_5,
                GLFW_KEY_KP_6, GLFW_KEY_KP_7, GLFW_KEY_KP_8, GLFW_KEY_KP_9, GLFW_KEY_KP_DECIMAL,
                GLFW_KEY_KP_DIVIDE, GLFW_KEY_KP_MULTIPLY, GLFW_KEY_KP_SUBTRACT, GLFW_KEY_KP_ADD, GLFW_KEY_KP_ENTER,
                GLFW_KEY_KP_EQUAL, GLFW_KEY_LEFT_SHIFT, GLFW_KEY_LEFT_CONTROL, GLFW_KEY_LEFT_ALT, GLFW_KEY_LEFT_SUPER,
                GLFW_KEY_RIGHT_SHIFT, GLFW_KEY_RIGHT_CONTROL, GLFW_KEY_RIGHT_ALT, GLFW_KEY_RIGHT_SUPER, GLFW_KEY_MENU
        );

        add("QWERTZ", GLFW_KEY_A, GLFW_KEY_A);
        add("QWERTZ", GLFW_KEY_B, GLFW_KEY_B);
        add("QWERTZ", GLFW_KEY_C, GLFW_KEY_C);
        add("QWERTZ", GLFW_KEY_D, GLFW_KEY_D);
        add("QWERTZ", GLFW_KEY_E, GLFW_KEY_E);
        add("QWERTZ", GLFW_KEY_F, GLFW_KEY_F);
        add("QWERTZ", GLFW_KEY_G, GLFW_KEY_G);
        add("QWERTZ", GLFW_KEY_H, GLFW_KEY_H);
        add("QWERTZ", GLFW_KEY_I, GLFW_KEY_I);
        add("QWERTZ", GLFW_KEY_J, GLFW_KEY_J);
        add("QWERTZ", GLFW_KEY_K, GLFW_KEY_K);
        add("QWERTZ", GLFW_KEY_L, GLFW_KEY_L);
        add("QWERTZ", GLFW_KEY_M, GLFW_KEY_M);
        add("QWERTZ", GLFW_KEY_N, GLFW_KEY_N);
        add("QWERTZ", GLFW_KEY_O, GLFW_KEY_O);
        add("QWERTZ", GLFW_KEY_P, GLFW_KEY_P);
        add("QWERTZ", GLFW_KEY_Q, GLFW_KEY_Q);
        add("QWERTZ", GLFW_KEY_R, GLFW_KEY_R);
        add("QWERTZ", GLFW_KEY_S, GLFW_KEY_S);
        add("QWERTZ", GLFW_KEY_T, GLFW_KEY_T);
        add("QWERTZ", GLFW_KEY_U, GLFW_KEY_U);
        add("QWERTZ", GLFW_KEY_V, GLFW_KEY_V);
        add("QWERTZ", GLFW_KEY_W, GLFW_KEY_W);
        add("QWERTZ", GLFW_KEY_X, GLFW_KEY_X);
        add("QWERTZ", GLFW_KEY_Y, GLFW_KEY_Z);
        add("QWERTZ", GLFW_KEY_Z, GLFW_KEY_Y);

        add("AZERTY", GLFW_KEY_A, GLFW_KEY_Q);
        add("AZERTY", GLFW_KEY_B, GLFW_KEY_B);
        add("AZERTY", GLFW_KEY_C, GLFW_KEY_C);
        add("AZERTY", GLFW_KEY_D, GLFW_KEY_D);
        add("AZERTY", GLFW_KEY_E, GLFW_KEY_E);
        add("AZERTY", GLFW_KEY_F, GLFW_KEY_F);
        add("AZERTY", GLFW_KEY_G, GLFW_KEY_G);
        add("AZERTY", GLFW_KEY_H, GLFW_KEY_H);
        add("AZERTY", GLFW_KEY_I, GLFW_KEY_I);
        add("AZERTY", GLFW_KEY_J, GLFW_KEY_J);
        add("AZERTY", GLFW_KEY_K, GLFW_KEY_K);
        add("AZERTY", GLFW_KEY_L, GLFW_KEY_L);
        add("AZERTY", GLFW_KEY_M, GLFW_KEY_COMMA);
        add("AZERTY", GLFW_KEY_N, GLFW_KEY_N);
        add("AZERTY", GLFW_KEY_O, GLFW_KEY_O);
        add("AZERTY", GLFW_KEY_P, GLFW_KEY_P);
        add("AZERTY", GLFW_KEY_Q, GLFW_KEY_A);
        add("AZERTY", GLFW_KEY_R, GLFW_KEY_R);
        add("AZERTY", GLFW_KEY_S, GLFW_KEY_S);
        add("AZERTY", GLFW_KEY_T, GLFW_KEY_T);
        add("AZERTY", GLFW_KEY_U, GLFW_KEY_U);
        add("AZERTY", GLFW_KEY_V, GLFW_KEY_V);
        add("AZERTY", GLFW_KEY_W, GLFW_KEY_Z);
        add("AZERTY", GLFW_KEY_X, GLFW_KEY_X);
        add("AZERTY", GLFW_KEY_Y, GLFW_KEY_Y);
        add("AZERTY", GLFW_KEY_Z, GLFW_KEY_W);
        add("AZERTY", GLFW_KEY_SEMICOLON, GLFW_KEY_M);
        add("AZERTY", GLFW_KEY_PERIOD, GLFW_KEY_SEMICOLON);

    }

    public void setDefault(int... keys)
    {
        for (int key : keys)
        {
            defaultKeys.add(key);
        }
        addQWERTY(keys);
    }

    public void addQWERTY(int... keys)
    {
        for (int key : keys)
        {
            add("QWERTY", key, key);
        }
    }

    public void add(String layout, int key, int mappedKey)
    {
        if (!layouts.containsKey(layout)) layouts.put(layout, new HashMap<>());
        layouts.get(layout).put(key, mappedKey);
    }

    @Override
    public void act(DirectoryCache cache) throws IOException
    {
        Path path = generator.getOutputFolder();

        registerMappings();

        Map<String, JsonObject> files = new HashMap<>();

        for (Map.Entry<String, Map<Integer, Integer>> layout : layouts.entrySet())
        {
            for (int key : defaultKeys)
            {
                if (!layout.getValue().containsKey(key) && !layout.getValue().containsValue(key)) layout.getValue().put(key, key);
            }

            JsonObject json = new JsonObject();
            json.addProperty("name", layout.getKey());
            JsonObject mapping = new JsonObject();
            for (Map.Entry<Integer, Integer> keyMap : layout.getValue().entrySet())
            {
                mapping.addProperty(keyMap.getKey().toString(), keyMap.getValue());
            }
            json.add("mapping", mapping);
            files.put(layout.getKey().toLowerCase(), json);
        }

        for (Map.Entry<String, JsonObject> file : files.entrySet())
        {
            Path filePath = path.resolve("assets/" + LocalKeys.MOD_ID + "/keyboard_layouts/" + file.getKey() + ".json");

            try
            {
                IDataProvider.save(GSON, cache, file.getValue(), filePath);
            }
            catch (IOException e)
            {
                LocalKeys.LOGGER.error("Couldn't save keyboard_layouts file {}", filePath, e);
            }
        }
    }

    @Override
    public String getName()
    {
        return "Keyboard Layouts";
    }
}