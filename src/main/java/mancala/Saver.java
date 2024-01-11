package mancala;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Saver {

    /**
     * Save an object to the location provided.
     *
     * @param toSave The object to save.
     * @param filename The location to save the object.
     */
    public void saveObject(final Serializable toSave, final String filename) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOut.writeObject(toSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the object and return it.
     *
     * @param filename The location to load the object from.
     * @return The loaded object.
     */
    public Serializable loadObject(final String filename) {
        Serializable toReturn;
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filename))) {
            toReturn = (Serializable) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            toReturn = null;
        }
        return toReturn;
    }
}
