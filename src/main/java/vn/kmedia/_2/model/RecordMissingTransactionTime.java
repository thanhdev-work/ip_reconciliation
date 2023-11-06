package vn.kmedia._2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecordMissingTransactionTime {
    private String phoneNumber;
    private String transactionTime;
    private LocalDateTime localDateTime;
}
