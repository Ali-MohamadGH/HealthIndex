package ca.enwin.healthindex.model;

public record MeasurementScore(

        String measurementName,

        double actualValue,

        double maximumValue
) {
}