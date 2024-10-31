package com.ruben.WebtherChecker.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesUtils {
    
    public static List<String> returnFileListed(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<File> listFiles(String directoryPath) {
        List<File> fileList = new ArrayList<>();

        try {
            Files.list(Paths.get(directoryPath)).forEach(filePath -> {
                File file = filePath.toFile();
                if (file.isFile()) {
                    fileList.add(file);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }
}
