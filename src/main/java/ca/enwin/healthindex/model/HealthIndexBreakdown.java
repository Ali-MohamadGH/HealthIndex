package ca.enwin.healthindex.model;

import java.util.List;

public record HealthIndexBreakdown(

        List<CategorySetScore> setScores,

        List<CategoryScore> categoryScores,

        double finalHealthIndex
) {
}
