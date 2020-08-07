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
 * @author Yunus1903
 * @since 07/08/2020
 */
public class KeyboardLayoutHandler
{
    private static final String PATH = "keyboard_layouts";
    private static final String SAVE_PATH = "keyboard_layout.txt";

    private final File saveFile;
    private final List<JsonObject> jsons = new ArrayList<>();
    private final List<KeyboardLayout> layouts = new ArrayList<>();

    public KeyboardLayout currentLayout;

    public KeyboardLayoutHandler()
    {
        saveFile = new File(Minecraft.getInstance().gameDir, SAVE_PATH);

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

    private void readJsons() throws IOException
    {
        IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        for (ResourceLocation rl : resourceManager.getAllResourceLocations(PATH, filter -> filter.endsWith(".json")))
        {
            IResource resource = resourceManager.getResource(rl);
            jsons.add(new Gson().fromJson(new JsonReader(new InputStreamReader(resource.getInputStream())), JsonObject.class));
        }
    }

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

    public List<KeyboardLayout> getLayouts()
    {
        return Collections.unmodifiableList(layouts);
    }

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

    public void remapKeys(KeyboardLayout layout)
    {
        GameSettings gameSettings = Minecraft.getInstance().gameSettings;
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

    private Optional<Integer> getMappedKey(KeyboardLayout layout, int key)
    {
        return Optional.ofNullable(layout.getMapping().get(getDefaultKey(key).orElse(layouts.get(0).getMapping().get(key))));
    }

    private Optional<Integer> getDefaultKey(int key)
    {
        Map<Integer, Integer> map = currentLayout.getReverseMappings();
        return Optional.ofNullable(map.get(key));
    }
}
