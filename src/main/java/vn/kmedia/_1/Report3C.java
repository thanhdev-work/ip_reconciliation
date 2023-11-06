package vn.kmedia._1;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Report3C {
    @CsvBindByPosition(position = 1)
    private String mobile;
    @CsvBindByPosition(position = 5)
    private String hotLineAndBrandName;
    @CsvBindByPosition(position = 3)
    private String dtvId;
    @CsvBindByPosition(position = 6)
    private String startTime;
    @CsvBindByPosition(position = 7)
    private String endTime;
    @CsvBindByPosition(position = 10)
    private String duration;
    @CsvBindByPosition(position = 19)
    private String recordUrl;

    @Override
    public String toString() {
        return "Report3C{" +
                "mobile='" + mobile + '\'' +
                ", hotLineAndBrandName='" + hotLineAndBrandName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", duration='" + duration + '\'' +
                ", recordUrl='" + recordUrl + '\'' +
                '}';
    }
}
