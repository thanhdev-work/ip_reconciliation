package vn.kmedia._2.main;

import lombok.var;
import vn.kmedia._1.service.CsvService;
import vn.kmedia._2.model.RecordMissingReport;
import vn.kmedia._2.model.RecordMissingTransactionTime;
import vn.kmedia._2.model.RecordSourceReport;
import vn.kmedia._2.model.TransactionDetailReport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String RECORD_MISSING_REPORT = "C:\\Users\\TGPM\\Documents\\WorkingSpace\\dev_km_vms_ipacking\\ip_reconciliation\\src\\main\\java\\vn\\kmedia\\_2\\resource\\record_missing_report.csv";
    private static final String RECORD_SOURCE_REPORT = "C:\\Users\\TGPM\\Documents\\WorkingSpace\\dev_km_vms_ipacking\\ip_reconciliation\\src\\main\\java\\vn\\kmedia\\_2\\resource\\record_source_report.csv";
    private static final String TRANSACTION_DETAIL_REPORT = "C:\\Users\\TGPM\\Documents\\WorkingSpace\\dev_km_vms_ipacking\\ip_reconciliation\\src\\main\\java\\vn\\kmedia\\_2\\resource\\transaction_detail_report.csv";
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    private static String convertDateTime(String input) {
        LocalDateTime dateTime = LocalDateTime.parse(input, inputFormatter);
        return dateTime.format(outputFormatter);

    }

    public static void main(String[] args) throws Exception {
        List<RecordMissingReport> recordMissing = new ArrayList<>();
        List<RecordSourceReport> recordSource = new ArrayList<>();
        List<TransactionDetailReport> transactionDetail = new ArrayList<>();

        recordMissing = CsvService.toListObject(RECORD_MISSING_REPORT, RecordMissingReport.class);
        recordSource = CsvService.toListObject(RECORD_SOURCE_REPORT, RecordSourceReport.class);
        transactionDetail = CsvService.toListObject(TRANSACTION_DETAIL_REPORT, TransactionDetailReport.class);

        //Compare record missing vs transaction detail and get call time
        List<RecordMissingTransactionTime> missingRecordTransTime = new ArrayList<>();
        for (RecordMissingReport e : recordMissing) {
            for (TransactionDetailReport x : transactionDetail) {
                if (e.getPhoneNumber().equals(x.getPhoneNumber())) {
                    missingRecordTransTime.add(new RecordMissingTransactionTime(x.getPhoneNumber(), x.getCloseTransTime(), LocalDateTime.parse(x.getCloseTransTime(), inputFormatter2)));
                }
            }
        }
        List<String> ok = new ArrayList<>();
        for (RecordSourceReport e : recordSource) {
            e.setLocalDTEndTime(LocalDateTime.parse(e.getEndTime(), inputFormatter));
            for (RecordMissingTransactionTime x : missingRecordTransTime) {
                LocalDateTime temp = x.getLocalDateTime().plusMinutes(10);
                if (temp.isBefore(e.getLocalDTEndTime())) {
                    ok.add(e.getPhoneNumber());
                }
            }
        }
    }
}
