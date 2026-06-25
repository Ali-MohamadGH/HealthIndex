package ca.enwin.healthindex.model;

public record CategoryScore(

        String category,

        double weight,

        double score
) {
}