package com.limeesodaa.healthindex.model;

public record ConversionRule(
        String codeGroup,
        double sourceValue,
        double convertedValue
        ) {

}
