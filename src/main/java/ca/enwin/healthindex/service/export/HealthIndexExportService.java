package ca.enwin.healthindex.service.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ca.enwin.healthindex.model.HealthIndexResult;

public class HealthIndexExportService {

    public File export(

            File file,

            List<HealthIndexResult> results,
            
            Consumer<String> logger)

            throws Exception {

        Set<String> categories =

                results.stream()

                        .flatMap(result ->

                                result.categoryScores()
                                        .keySet()
                                        .stream())

                        .collect(

                                Collectors.toCollection(
                                        LinkedHashSet::new));

        try (Workbook workbook =
                     new XSSFWorkbook()) {

            Sheet sheet =

                    workbook.createSheet(
                            "Verification");

            Row header =

                    sheet.createRow(0);

            int col = 0;

            header.createCell(col++)
                    .setCellValue(
                            "Equipment Number");

            header.createCell(col++)
                    .setCellValue(
                            "Date");

            header.createCell(col++)
                    .setCellValue(
                            "Gateway Fail");

            for (String category :
                    categories) {

                header.createCell(col++)
                        .setCellValue(
                                toTitleCase(
                                        category));
            }

            header.createCell(col++)
                    .setCellValue(
                            "Health Index (%)");

            int rowNumber = 1;

            for (HealthIndexResult result :
                    results) {

                        
                logger.accept(
                        "Exporting "
                        + result.equipmentId()
                        + "...\n");

                Row row =

                        sheet.createRow(
                                rowNumber++);

                col = 0;

                row.createCell(col++)
                        .setCellValue(
                                result.equipmentId());

                row.createCell(col++)
                        .setCellValue(
                                result.inspectionDate()
                                        .toString());

                row.createCell(col++)
                        .setCellValue(
                                result.gatewayFail()
                                        );

                for (String category :
                        categories) {

                    row.createCell(col++)
                            .setCellValue(

                                    String.format("%.2f", result.categoryScores()
                                            .getOrDefault(
                                                    category,
                                                    0.0)));
                }

                row.createCell(col++)
                        .setCellValue(
                               String.format("%.2f", result.healthIndex()*100.00)+"%");
            }

            for (int i = 0;
                    i < header.getLastCellNum();
                    i++) {

                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream out =
                         new FileOutputStream(
                                 file)) {

                workbook.write(out);
            }
        }

        return file;
    }

    private String toTitleCase(
        String value) {

    if (value == null
            || value.isBlank()) {

        return "";
    }

    StringBuilder result =
            new StringBuilder();

    for (String word :
            value.trim()
                    .toLowerCase()
                    .split("\\s+")) {

        if (word.isBlank()) {
            continue;
        }

        if (result.length() > 0) {

            result.append(" ");
        }

        result.append(

                Character.toUpperCase(
                        word.charAt(0)))

                .append(
                        word.substring(1));
    }

    return result.toString();
        }
}