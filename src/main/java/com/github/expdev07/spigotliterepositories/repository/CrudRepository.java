package com.github.expdev07.spigotliterepositories.repository;

import com.github.expdev07.spigotliterepositories.Identifiable;

import java.util.Collection;

/**
 * Repository that covers all the basic CRUD operations (Create, Read, Update, and Delete).
 *
 * @param <ID> The id type.
 * @param <T> The object type.
 */
public interface CrudRepository<ID, T extends Identifiable<ID>> extends Repository<ID, T>
{

    /**
     * Saves the object.
     *
     * @param object The object.
     * @return The saved object.
     */
    T save(T object);

    /**
     * Saves the objects.
     *
     * @param objects The objects.
     * @return The saved objects.
     */
    Collection<T> saveAll(Collection<T> objects);

    /**
     * Finds a object by its id.
     *
     * @param id The id.
     * @return The object.
     */
    T find(ID id);

    /**
     * Retrieves all the objects.
     *
     * @return The objects.
     */
    Collection<T> findAll();

    /**
     * Deletes the object.
     *
     * @param object The object.
     * @return True if deleted.
     */
    boolean delete(T object);

}
