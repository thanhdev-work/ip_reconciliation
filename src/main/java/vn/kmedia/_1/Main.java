package vn.kmedia._1;

import vn.kmedia._1.service.CsvService;
import vn.kmedia._1.service.FileService;
import vn.kmedia._1.service.Report3CFilter;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String TRANSACTION_PATH = "C:\\Users\\TGPM\\Documents\\Working Space\\dev_km_etourist\\ip_reconciliation\\src\\main\\resources\\asset\\transaction.csv";
    private static final String REPORT3C_PATH = "C:\\Users\\TGPM\\Documents\\Working Space\\dev_km_etourist\\ip_reconciliation\\src\\main\\resources\\asset\\Ipacking_callout Thang 7.csv";
    private static final String RECORD_PATH = "C:\\Users\\TGPM\\Documents\\Working Space\\dev_km_etourist\\ip_reconciliation\\src\\main\\resources\\asset\\ip_voice_files";
    private static final String TARGET_PATH = "C:\\Users\\TGPM\\Documents\\Working Space\\dev_km_etourist\\ip_reconciliation\\src\\main\\resources\\asset\\reconciliation_result.xlsx";
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static String convertDateTime(String input) {
        LocalDateTime dateTime = LocalDateTime.parse(input, inputFormatter);
        return dateTime.format(outputFormatter);

    }

    public static void main(String[] args) throws FileNotFoundException {


        List<ReportTransaction> reportTransactions = CsvService.toListObject(TRANSACTION_PATH, ReportTransaction.class);
        List<String> allowMobile = reportTransactions
                .stream()
                .map(ReportTransaction::getMobile)
                .collect(Collectors.toList());

        List<Report3C> report3Cs = CsvService.toListObject(REPORT3C_PATH, Report3C.class, new Report3CFilter(allowMobile))
                .stream()
                .sorted(Comparator.comparing(Report3C::getMobile))
                .collect(Collectors.toList());

        List<FileMetadata> fileMetadata = FileService.fileMetadataHandle(RECORD_PATH);

        Map<String, List<FileMetadata>> map1 = new HashMap<>();
        Map<String, List<Report3C>> map2 = new HashMap<>();
        reportTransactions.forEach(e -> {
            String mobile = e.getMobile();
            map1.put(mobile,
                    fileMetadata.stream()
                            .filter(r -> r.getMobile().substring(1).equals(mobile))
                            .collect(Collectors.toList()));
        });
        map1.entrySet().forEach(e -> {
            FileMetadata fileMetadata1 = !e.getValue().isEmpty() ? e.getValue().get(0) : new FileMetadata();
            String dateToMap = fileMetadata1.getFileName() != null ? fileMetadata1.getFileName().split("_")[1].split("\\.")[0] : "#####";
            map2.put(e.getKey(), report3Cs.stream().filter(y -> convertDateTime(y.getStartTime()).equals(dateToMap)).collect(Collectors.toList()));
        });
        List<ReconciliationResult> reconciliationResults = new ArrayList<>();

        for (ReportTransaction e : reportTransactions) {
            try {
                List<FileMetadata> a = map1.get(e.getMobile());
                List<Report3C> b = map2.get(e.getMobile());

                ReconciliationResult result = new ReconciliationResult();
                result.setBrandName(b.get(0).getHotLineAndBrandName() + "");
                result.setMobile("0" + e.getMobile());
                result.setDtvId(b.get(0).getDtvId());
                result.setPackageId(e.getPackageId());
                result.setStartTime(b.get(0).getStartTime());
                result.setEndTime(b.get(0).getEndTime());
                result.setDuration(b.get(0).getDuration());
                result.setRecordPath(b.get(0).getRecordUrl());
                result.setFilePath(a.get(0).getFilePath().replace("C:\\Users\\TGPM\\Documents\\Working Space\\dev_km_etourist\\ip_reconciliation\\src\\main\\resources\\asset\\", ""));
                reconciliationResults.add(result);
            } catch (Exception exception) {
                System.out.println(e.getMobile() + "--------errr");
            }
        }

        CsvService.toExcel(reconciliationResults, TARGET_PATH);
    }
}