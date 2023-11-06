package vn.kmedia._2.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class TransactionDetailReport {
    @CsvBindByPosition(position = 3)
    private String phoneNumber;
    @CsvBindByPosition(position = 11)
    private String processTransTime;
    @CsvBindByPosition(position = 12)
    private String closeTransTime;
}
