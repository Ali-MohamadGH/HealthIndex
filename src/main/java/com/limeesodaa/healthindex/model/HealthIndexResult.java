package com.limeesodaa.healthindex.model;

import java.time.LocalDate;
import java.util.Map;

public record HealthIndexResult(
        String equipmentId,
        LocalDate inspectionDate,
        double healthIndex,
        Map<String, Double> categoryScores,
        boolean gatewayFail) {

}
