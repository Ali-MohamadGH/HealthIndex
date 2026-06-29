package com.limeesodaa.healthindex.service.imports;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.limeesodaa.healthindex.model.CategoryRule;
import com.limeesodaa.healthindex.model.ImportResult;
import com.limeesodaa.healthindex.repository.CategoryRepository;

public class CategoryImportService {

    private final CategoryRepository repository =
            new CategoryRepository();

    public ImportResult importFile(
            File file)
            throws Exception {

        repository.deleteAll();

        int read = 0;
        int imported = 0;

        try (
                Workbook workbook =
                        new XSSFWorkbook(
                                new FileInputStream(
                                        file))
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

                CategoryRule rule =
                        new CategoryRule(

                                ExcelUtils.getString(
                                        row.getCell(0)),

                                ExcelUtils.getString(
                                        row.getCell(1)),

                                ExcelUtils.getString(
                                        row.getCell(2)),

                                ExcelUtils.getDouble(
                                        row.getCell(3)),
                                ExcelUtils.getDouble(
                                        row.getCell(4)),

                                "1".equals(
                                        ExcelUtils.getString(
                                                row.getCell(5)))
                                                
                                
                                                
                        );

                repository.save(rule);

                imported++;
            }
        }

        return new ImportResult(
                read,
                imported,
                read - imported);
    }
}