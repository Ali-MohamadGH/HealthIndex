package ca.alimohamad.healthindex.model;

public record ConversionRule(
        String codeGroup,
        double sourceValue,
        double convertedValue
        ) {

}
