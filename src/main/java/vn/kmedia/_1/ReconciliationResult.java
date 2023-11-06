package vn.kmedia._1;

import lombok.Data;

@Data
public class ReconciliationResult {
    private String brandName;
    private String mobile;
    private String dtvId;
    private String packageId;
    private String startTime;
    private String endTime;
    private String duration;
    private String recordPath;
    private String filePath;

    @Override
    public String toString() {
        return "ReconciliationResult{" +
                "brandName='" + brandName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dtvId='" + dtvId + '\'' +
                ", packageId='" + packageId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", duration='" + duration + '\'' +
                ", recordPath='" + recordPath + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
