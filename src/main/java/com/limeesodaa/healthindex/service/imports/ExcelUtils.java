package com.limeesodaa.healthindex.service.imports;

import org.apache.poi.ss.usermodel.Cell;

public final class ExcelUtils {

    private ExcelUtils() {
    }

    public static String getString(
            Cell cell) {

        if (cell == null) {
            return "";
        }

        return switch (cell.getCellType()) {

            case STRING ->
                cell.getStringCellValue()
                .trim();

            case NUMERIC ->
                String.valueOf(
                (long) cell.getNumericCellValue());

            case BOOLEAN ->
                String.valueOf(
                cell.getBooleanCellValue());

            default ->
                "";
        };
    }

    public static double getDouble(
            Cell cell) {

        if (cell == null) {
            return 0;
        }

        return switch (cell.getCellType()) {

            case NUMERIC ->
                cell.getNumericCellValue();

            case STRING -> {

                try {
                    yield Double.parseDouble(
                    cell.getStringCellValue()
                    .trim());
                } catch (Exception ex) {
                    yield 0;
                }
            }

            default ->
                0;
        };
    }
}
