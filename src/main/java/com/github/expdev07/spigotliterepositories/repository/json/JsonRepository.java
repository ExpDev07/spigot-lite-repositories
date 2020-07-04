package com.github.expdev07.spigotliterepositories.repository.json;

import com.github.expdev07.spigotliterepositories.Identifiable;
import com.github.expdev07.spigotliterepositories.repository.CrudRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CRUD repository that interacts with a JSON files. Each object will have its own file associated with its unique id.
 *
 * @param <ID> The id type.
 * @param <T> The type.
 */
@AllArgsConstructor
public class JsonRepository<ID, T extends Identifiable<ID>> implements CrudRepository<ID, T>
{

    /**
     * The plugin instance.
     */
    private Plugin plugin;

    /**
     * The class type.
     */
    private Class<T> type;

    /**
     * The storage file path.
     */
    private String path;

    /**
     * The GSON instance.
     */
    protected Gson gson;

    /**
     * Constructs a new {@link JsonRepository}.
     *
     * @param plugin The plugin.
     * @param type The type.
     * @param path The path.
     */
    public JsonRepository(Plugin plugin, Class<T> type, String path)
    {
        this(plugin, type, path, new GsonBuilder().setPrettyPrinting().create());
    }

    /**
     * Gets the storage file for the provided id. Will create dirs/files if necessary.
     *
     * @param id The id.
     * @return The file.
     */
    protected File getFile(ID id)
    {
        final File file = new File(
                this.plugin.getDataFolder() + File.separator + this.path, id.toString() + ".json"
        );

        file.getParentFile().mkdirs();
        return file;
    }

    /**
     * Gets the storage file for the provided object.
     *
     * @param object The object.
     * @return The file.
     */
    protected File getFile(T object)
    {
        return this.getFile(object.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T object)
    {
        // Write.
        try (Writer writer = new FileWriter(this.getFile(object))) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            return null;
        }
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> saveAll(Collection<T> objects)
    {
        List<T> saved = new ArrayList<>();
        objects.forEach(object -> saved.add(this.save(object)));
        return saved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(ID id)
    {
        // Read.
        try {
            return gson.fromJson(new FileReader(this.getFile(id)), type);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> findAll()
    {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(T object)
    {
        return this.getFile(object).delete();
    }

}
