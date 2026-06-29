package com.limeesodaa.healthindex.service.imports;
import java.io.File;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.limeesodaa.healthindex.model.ConversionRule;
import com.limeesodaa.healthindex.repository.ConversionRepository;

public class ConversionImportService {

    private final ConversionRepository
            repository =
            new ConversionRepository();

    public void importFile(
            File file)
            throws Exception {

        repository.deleteAll();

        try (Workbook workbook =
                     WorkbookFactory.create(
                             file)) {

            Sheet sheet =
                    workbook.getSheetAt(0);

            for (int rowIndex = 1;
                 rowIndex <= sheet.getLastRowNum();
                 rowIndex++) {

                Row row =
                        sheet.getRow(
                                rowIndex);

                if (row == null) {
                    continue;
                }

                ConversionRule rule =
                        new ConversionRule(

                                row.getCell(0)
                                        .getStringCellValue(),

                                row.getCell(1)
                                        .getNumericCellValue(),

                                row.getCell(2)
                                        .getNumericCellValue()
                        );

                repository.save(rule);
            }
        }
    }
}
