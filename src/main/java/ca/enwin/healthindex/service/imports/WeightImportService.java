package ca.enwin.healthindex.service.imports;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ca.enwin.healthindex.model.ImportResult;
import ca.enwin.healthindex.model.WeightRule;
import ca.enwin.healthindex.repository.WeightRepository;

public class WeightImportService {

    private final WeightRepository repository =
            new WeightRepository();

    public ImportResult importFile(
            File file)
            throws Exception {

        repository.deleteAll();

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

                WeightRule rule =
                        new WeightRule(

                                ExcelUtils.getString(
                                        row.getCell(0)),

                                ExcelUtils.getString(
                                        row.getCell(1)),

                                ExcelUtils.getDouble(
                                        row.getCell(2)),

                                ExcelUtils.getDouble(
                                        row.getCell(3))
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
