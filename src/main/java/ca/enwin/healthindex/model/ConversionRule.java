package ca.enwin.healthindex.model;

public record ConversionRule(

        String codeGroup,

        double sourceValue,

        double convertedValue
) {}