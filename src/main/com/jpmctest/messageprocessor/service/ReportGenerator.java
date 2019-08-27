package main.com.jpmctest.messageprocessor.service;

import main.com.jpmctest.messageprocessor.dao.Adjustment;
import main.com.jpmctest.messageprocessor.dao.Sale;
import java.util.List;

public interface ReportGenerator {
    String generateSalesReport(List<Sale> sales);
    String generateAdjustmentsReport(List<Adjustment> adjustments);
}
