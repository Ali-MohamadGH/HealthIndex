package ca.enwin.healthindex.model;

public record CategoryRule(

        String measurementName,

        String category,

        String categorySet,

        double maximumValue,
        double gatewayTestValue,
        boolean gatewayFail

        ) {
}