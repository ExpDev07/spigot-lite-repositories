package com.github.expdev07.spigotliterepositories;

/**
 * An object that is identifiable by an unique id.
 *
 * @param <ID>
 */
public interface Identifiable<ID>
{

    /**
     * Gets the id.
     *
     * @return The id.
     */
    ID getId();

    /**
     * Sets the id.
     *
     * @param id The id.
     */
    void setId(ID id);

}
