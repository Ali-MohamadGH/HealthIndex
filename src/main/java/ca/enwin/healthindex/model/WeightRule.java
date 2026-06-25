package ca.enwin.healthindex.model;

public record WeightRule(

        String category,

        String categorySet,

        double categoryWeight,

        double setWeight
) {
}