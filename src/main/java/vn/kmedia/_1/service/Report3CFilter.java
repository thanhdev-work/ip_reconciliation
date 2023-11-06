package vn.kmedia._1.service;

import com.opencsv.bean.CsvToBeanFilter;

import java.util.List;

public class Report3CFilter implements CsvToBeanFilter {
    private List<String> allowMobile;

    public Report3CFilter(List<String> allowMobile) {
        this.allowMobile = allowMobile;
    }

    @Override
    public boolean allowLine(String[] strings) {
        String mobile = strings[1].substring(1);
        return this.allowMobile.contains(mobile);
    }
}
