package vn.kmedia._1.service;

import lombok.experimental.UtilityClass;
import vn.kmedia._1.FileMetadata;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FileService {


    public static List<FileMetadata> fileMetadataHandle(String wholeFolderPath) {
        String fileExtension = ".mp3";
        File folder = new File(wholeFolderPath);
        File[] folderArray = folder.listFiles();
        if (folderArray == null) throw new RuntimeException();
        List<String> folderName = Arrays.stream(folderArray).map(File::getAbsolutePath).collect(Collectors.toList());
        List<FileMetadata> fileMetadata = new ArrayList<>();

        for (String s : folderName) {
            File file = new File(s);
            if (file.isDirectory()) {
                for (File childFile : getChildFiles(file, fileExtension)) {
                    fileMetadata.add(new FileMetadata(childFile.getName(), childFile.getAbsolutePath(), null));
                }
            }
        }
        return fileMetadata;
    }

    private static List<File> getChildFiles(File file, String fileExtension) {
        List<File> listFile = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    listFile.addAll(getChildFiles(f, fileExtension));
                } else if (f.getName().endsWith(fileExtension)) {
                    listFile.add(f.getAbsoluteFile());
                }
            }
        }
        return listFile;
    }
}
