package ca.enwin.healthindex.service.imports;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ca.enwin.healthindex.model.ImportResult;
import ca.enwin.healthindex.model.TransformerLoad;
import ca.enwin.healthindex.repository.TransformerLoadRepository;

public class TransformerLoadImportService {

    private final TransformerLoadRepository repository =
            new TransformerLoadRepository();

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

                String equipment =
                        ExcelUtils.getString(
                                row.getCell(0));

                double score =
                        ExcelUtils.getDouble(
                                row.getCell(2));

                String dateText =
                        ExcelUtils.getString(
                                row.getCell(3));

                TransformerLoad load =
                        new TransformerLoad(

                                equipment,

                                LocalDate.parse(
                                        dateText),

                                score
                        );

                repository.save(load);

                imported++;
            }
        }

        return new ImportResult(
                read,
                imported,
                read - imported);
    }
}