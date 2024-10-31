package com.ruben.WebtherChecker.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerializeUtils {
    
    public static <T> boolean serializeObject(String fullPath, T object) {
        try {
            File file = new File(fullPath);
            FileOutputStream outFile = new FileOutputStream(file);
            ObjectOutputStream fileObject = new ObjectOutputStream(outFile);
            fileObject.writeObject(object);
            fileObject.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static <T> T deserializeObject(String fullPath) {
        try {
            File file = new File(fullPath);
            FileInputStream inFile = new FileInputStream(file);
            ObjectInputStream fileObject = new ObjectInputStream(inFile);
            @SuppressWarnings("unchecked")
            T object = (T) fileObject.readObject();
            fileObject.close();
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> deserializeAllFiles(String directoryPath) {
        List<T> deserializedObjects = new ArrayList<>();
        List<File> files = FilesUtils.listFiles(directoryPath);

        for (File file : files) {
            T object = deserializeObject(file.getAbsolutePath());
            if (object != null) {
                deserializedObjects.add(object);
            }
        }

        Collections.reverse(deserializedObjects);
        return deserializedObjects;
    }
}
