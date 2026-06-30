package ca.alimohamad.healthindex.model;

public record WeightRule(
        String category,
        String categorySet,
        double categoryWeight,
        double setWeight
        ) {

}
