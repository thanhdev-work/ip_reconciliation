package vn.kmedia._1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileMetadata {
    private String mobile;
    private String fileName;
    private String filePath;
    private String downUrl;

    public FileMetadata(String fileName, String filePath, String downUrl) {
        this.mobile = fileName.split("_")[0].replaceFirst(fileName.substring(0, 2), "0");
        this.fileName = fileName;
        this.filePath = filePath;
        this.downUrl = downUrl;
    }
}
