package ca.alimohamad.healthindex.model;

public record CategoryScore(
        String category,
        double weight,
        double score
        ) {

}
