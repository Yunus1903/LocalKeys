package com.yunus1903.localkeys.client.misc;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.yunus1903.localkeys.LocalKeys;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This class handles all the layout changes
 * @author Yunus1903
 * @since 07/08/2020
 */
public class KeyboardLayoutHandler
{
    private static final String PATH = "keyboard_layouts";
    private static final String SAVE_PATH = "keyboard_layout.txt";

    private final Minecraft mc;
    private final File saveFile;
    private final List<JsonObject> jsons = new ArrayList<>();
    private final List<KeyboardLayout> layouts = new ArrayList<>();

    public KeyboardLayout currentLayout;

    /**
     * Constructor for the {@link KeyboardLayoutHandler handler} instance
     * @param mc A instance of {@link Minecraft}
     */
    public KeyboardLayoutHandler(Minecraft mc)
    {
        this.mc = mc;
        saveFile = new File(mc.gameDir, SAVE_PATH);

        try
        {
            readJsons();
        }
        catch (IOException e)
        {
            LocalKeys.LOGGER.error("Exception reading json", e);
        }

        parseLayouts();
        loadCurrentLayout();
    }

    /**
     * Reads {@link JsonObject JSON objects} from the {@code .json} files in the game's resources and stores them into {@link KeyboardLayoutHandler#jsons}
     * @throws IOException
     */
    private void readJsons() throws IOException
    {
        IResourceManager resourceManager = mc.getResourceManager();
        for (ResourceLocation rl : resourceManager.getAllResourceLocations(PATH, filter -> filter.endsWith(".json")))
        {
            IResource resource = resourceManager.getResource(rl);
            jsons.add(new Gson().fromJson(new JsonReader(new InputStreamReader(resource.getInputStream())), JsonObject.class));
        }
    }

    /**
     * Parses the {@link KeyboardLayoutHandler#jsons JSON objects} in {@link KeyboardLayoutHandler#jsons} into {@link KeyboardLayoutHandler#layouts keyboard layouts}
     */
    private void parseLayouts()
    {
        for (JsonObject json : jsons)
        {
            Map<Integer, Integer> mapping = new HashMap<>();
            json.get("mapping").getAsJsonObject().entrySet().forEach(map ->
                    mapping.put(Integer.parseInt(map.getKey()), map.getValue().getAsInt()));
            layouts.add(new KeyboardLayout(json.get("name").getAsString(), mapping));
        }

        layouts.sort((o1, o2) ->
        {
            if (o1.equals(o2)) return 0;
            if (o1.getName().equals("QWERTY")) return -1;
            if (o2.getName().equals("QWERTY")) return 1;
            return o1.getName().compareTo(o2.getName());
        });
    }

    /**
     * @return The a list of loaded {@link KeyboardLayout layouts}
     */
    public List<KeyboardLayout> getLayouts()
    {
        return Collections.unmodifiableList(layouts);
    }

    /**
     * Loads the {@link KeyboardLayoutHandler#currentLayout current layout} from a local {@link KeyboardLayoutHandler#SAVE_PATH file}
     */
    private void loadCurrentLayout()
    {
        currentLayout = layouts.get(0);
        if (!saveFile.exists())
        {
            remapKeys(currentLayout);
            return;
        }

        try (BufferedReader bufferedReader = Files.newReader(saveFile, Charsets.UTF_8))
        {
            bufferedReader.lines().findFirst().ifPresent(line ->
                    layouts.stream().filter(layout -> line.equals(layout.getName().toLowerCase())).findFirst().ifPresent(layout -> currentLayout = layout));
            remapKeys(currentLayout);
        }
        catch (FileNotFoundException e)
        {
            LocalKeys.LOGGER.error(SAVE_PATH + " file not found!", e);
        }
        catch (IOException e)
        {
            LocalKeys.LOGGER.error("Exception reading file!", e);
        }
    }

    /**
     * Remaps the in-game {@link KeyBinding KeyBindings} according to the layout
     * @param layout The {@link KeyboardLayout layout} to which the game {@link KeyBinding bindings} should be mapped to
     */
    public void remapKeys(KeyboardLayout layout)
    {
        GameSettings gameSettings = mc.gameSettings;
        for (KeyBinding key : gameSettings.keyBindings)
        {
            if (key.getKey().getType() == InputMappings.Type.KEYSYM)
            {
                if (layout.equals(layouts.get(0)))
                {
                    getDefaultKey(key.getKey().getKeyCode())
                            .ifPresent(mappedKey -> gameSettings.setKeyBindingCode(key, InputMappings.Type.KEYSYM.getOrMakeInput(mappedKey)));
                }
                else
                {
                    getMappedKey(layout, key.getKey().getKeyCode())
                            .ifPresent(mappedKey -> gameSettings.setKeyBindingCode(key, InputMappings.Type.KEYSYM.getOrMakeInput(mappedKey)));
                }
                KeyBinding.resetKeyBindingArrayAndHash();
            }
        }
        currentLayout = layout;
        saveCurrentLayout();
    }

    /**
     * Saves the {@link KeyboardLayoutHandler#currentLayout current layout} to a local {@link KeyboardLayoutHandler#SAVE_PATH file}
     */
    private void saveCurrentLayout()
    {
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(saveFile), StandardCharsets.UTF_8)))
        {
            printWriter.print(currentLayout.getName().toLowerCase());
        }
        catch (FileNotFoundException e)
        {
            LocalKeys.LOGGER.error(SAVE_PATH + " file not found!", e);
        }
    }

    /**
     * Maps the provided key code according to the provided {@link KeyboardLayout layout}
     * @param layout The {@link KeyboardLayout layout} to map to
     * @param key The GLFW key code of the key
     * @return A {@link Optional optional} with the mapped key code
     */
    private Optional<Integer> getMappedKey(KeyboardLayout layout, int key)
    {
        return Optional.ofNullable(layout.getMapping().get(getDefaultKey(key).orElse(layouts.get(0).getMapping().get(key))));
    }

    /**
     * Gets the original unmapped key code of the provided mapped key code
     * @param key The GLFW key code of the key
     * @return A {@link Optional optional} with the key code of the default mapping of this key
     */
    private Optional<Integer> getDefaultKey(int key)
    {
        Map<Integer, Integer> map = currentLayout.getReverseMappings();
        return Optional.ofNullable(map.get(key));
    }
}
