package vn.kmedia._2.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordSourceReport {
    @CsvBindByPosition(position = 1)
    private String phoneNumber;
    @CsvBindByPosition(position = 6)
    private String startTime;
    @CsvBindByPosition(position = 7)
    private String endTime;
    private LocalDateTime localDTEndTime;
    @CsvBindByPosition(position = 19)
    private String recordUrl;
}
