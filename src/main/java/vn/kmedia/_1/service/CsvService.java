package vn.kmedia._1.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
import lombok.experimental.UtilityClass;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.kmedia._1.ReconciliationResult;

import java.io.*;
import java.util.List;

@UtilityClass
public class CsvService {

    public static <T> List<T> toListObject(String path, Class<T> bindingClass) throws FileNotFoundException {
        List<T> listObject = new CsvToBeanBuilder<T>(new FileReader(path))
                .withType(bindingClass)
                .build()
                .parse();
        return listObject;
    }

    public static <T> List<T> toListObject(String path, Class<T> bindingClass, CsvToBeanFilter filter) throws FileNotFoundException {
        List<T> listObject = new CsvToBeanBuilder<T>(new FileReader(path))
                .withType(bindingClass)
                .withFilter(filter)
                .build()
                .parse();


        return listObject;
    }

    public static void toCsv(List<ReconciliationResult> input, String path) {
        String[] header = {"brandName_hotLine", "mobile", "dtv", "package", "start_time", "end_time", "duration", "path"};
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            writer.writeNext(header);

            for (ReconciliationResult result : input) {
                String[] data = {
                        result.getBrandName(),
                        result.getMobile(),
                        result.getDtvId(),
                        result.getPackageId(),
                        result.getStartTime(),
                        result.getEndTime(),
                        result.getDuration(),
                        result.getFilePath() + "link: " + result.getRecordPath()};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void toExcel(List<ReconciliationResult> input, String path) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Reconciliation Result");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Số hotline/brandname");
        headerRow.createCell(1).setCellValue("SĐT khách hàng");
        headerRow.createCell(2).setCellValue("Mã ĐTV tư vấn");
        headerRow.createCell(3).setCellValue("Gói cước tư vấn");
        headerRow.createCell(4).setCellValue("Thời gian bắt đầu");
        headerRow.createCell(5).setCellValue("Thời gian kết thúc");
        headerRow.createCell(6).setCellValue("Thời lượng cuộc gọi (s)");
        headerRow.createCell(7).setCellValue("Đường dẫn/Tên file ghi âm");
        headerRow.createCell(8).setCellValue("Download");


        int rowNum = 1;
        for (ReconciliationResult result : input) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(result.getBrandName());
            row.createCell(1).setCellValue(result.getMobile());
            row.createCell(2).setCellValue(result.getDtvId());
            row.createCell(3).setCellValue(result.getPackageId());
            row.createCell(4).setCellValue(result.getStartTime());
            row.createCell(5).setCellValue(result.getEndTime());
            row.createCell(6).setCellValue(result.getDuration());
            row.createCell(7).setCellValue(result.getFilePath());
            Hyperlink link = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
            link.setAddress(result.getRecordPath());
            row.createCell(8).setCellValue("link");
            row.createCell(8).setHyperlink(link);
        }

        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
