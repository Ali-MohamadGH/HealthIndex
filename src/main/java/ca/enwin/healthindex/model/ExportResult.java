package ca.enwin.healthindex.model;

public record ExportResult(

        int recordsExported,

        String filePath
) {
}