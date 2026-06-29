package com.limeesodaa.healthindex.service.imports;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.limeesodaa.healthindex.model.ImportResult;
import com.limeesodaa.healthindex.model.InspectionMeasurement;
import com.limeesodaa.healthindex.repository.InspectionRepository;

public class InspectionImportService {

    private final InspectionRepository repository =
            new InspectionRepository();

    private static final DateTimeFormatter
            DATE_FORMATTER =
            DateTimeFormatter.ofPattern(
                    "MM/dd/yyyy");
        
        


    
    public ImportResult importFile(
            File file,Consumer<String> logger)
            throws Exception {

        int read = 0;
        int imported = 0;
        

        try (
                Workbook workbook =
                        new XSSFWorkbook(
                                new FileInputStream(file))
        ) {

            Sheet sheet =
                    workbook.getSheetAt(0);

            boolean header = true;

            for (Row row : sheet) {

                if (header) {

                    header = false;
                    continue;
                }

                read++;

                String equipmentId =
                        ExcelUtils.getString(
                                row.getCell(0));
                                logger.accept("Processing: " + equipmentId + "\n");
                
                
                String measurement =
                        ExcelUtils.getString(
                                row.getCell(2));

                double value =
                        ExcelUtils.getDouble(
                                row.getCell(3));

                String codeGroup =
                        ExcelUtils.getString(
                                row.getCell(4))
                                .trim();

                if (equipmentId.isBlank()
                        || measurement.isBlank()) {

                    continue;
                }

                LocalDate inspectionDate =
                        getDate(
                                row.getCell(1));

                if (inspectionDate == null) {

                    

                    continue;
                }

                if ("TEMPERATURE GAUGE PRESENT"
                        .equals(measurement)
                        && value > 10) {

                    measurement =
                            "INTERNAL TEMPERATURE";
                }

                InspectionMeasurement
                        inspectionMeasurement =
                        new InspectionMeasurement(

                                equipmentId,

                                inspectionDate,

                                measurement,

                                codeGroup,

                                value
                        );

                

                repository.save(
                        inspectionMeasurement);

                imported++;
            }
        }

        return new ImportResult(
                read,
                imported,
                read - imported);
    }

    private LocalDate getDate(
            Cell cell)
            throws Exception {

        if (cell == null) {
            return null;
        }

        try {

            if (DateUtil.isCellDateFormatted(
                    cell)) {

                return cell
                        .getLocalDateTimeCellValue()
                        .toLocalDate();
            }

            String value =
                    ExcelUtils.getString(
                            cell)
                            .trim();

            if (value.isBlank()) {
                return null;
            }

            if (value.matches("\\d+")) {

                double serial =
                        Double.parseDouble(
                                value);

                return DateUtil
                        .getLocalDateTime(
                                serial)
                        .toLocalDate();
            }

            return LocalDate.parse(
                    value,
                    DATE_FORMATTER);

        } catch (Exception ex) {

            

            throw ex;
        }
    }
}