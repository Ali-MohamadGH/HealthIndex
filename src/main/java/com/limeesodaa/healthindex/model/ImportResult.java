package com.limeesodaa.healthindex.model;

public record ImportResult(
        int rowsRead,
        int rowsImported,
        int rowsSkipped
        ) {

}
