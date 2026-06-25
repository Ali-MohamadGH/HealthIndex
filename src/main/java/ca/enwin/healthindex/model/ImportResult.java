package ca.enwin.healthindex.model;

public record ImportResult(

        int rowsRead,

        int rowsImported,

        int rowsSkipped
) {
}