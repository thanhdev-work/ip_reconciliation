package vn.kmedia._1;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ReportTransaction {
    @CsvBindByName(column = "mobile")
    private String mobile;
    @CsvBindByName(column = "package_id")
    private String packageId;
    @CsvBindByName(column = "commission")
    private String commission;
    @CsvBindByName(column = "request_time")
    private String requestTime;
    @CsvBindByName(column = "status")
    private String status;
}
