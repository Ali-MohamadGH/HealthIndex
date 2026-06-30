package com.limeesodaa.healthindex.model;

public record MeasurementScore(
        String measurementName,
        double actualValue,
        double maximumValue
        ) {

}
