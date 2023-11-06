package vn.kmedia._2.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class RecordMissingReport {
    @CsvBindByPosition(position = 2)
    private String phoneNumber;
}
