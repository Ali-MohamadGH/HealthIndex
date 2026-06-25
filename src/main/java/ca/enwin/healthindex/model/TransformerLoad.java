package ca.enwin.healthindex.model;

import java.time.LocalDate;

public record TransformerLoad(

        String equipmentId,

        LocalDate loadDate,

        double loadingScore
) {
}