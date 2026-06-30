package ca.alimohamad.healthindex.service.calculation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.alimohamad.healthindex.model.CategoryRule;
import ca.alimohamad.healthindex.model.InspectionMeasurement;
import ca.alimohamad.healthindex.model.WeightRule;

public class CategoryCalculator {

    private final CategorySetCalculator setCalculator;

    public CategoryCalculator(
            ConversionService conversionService) {

        setCalculator
                = new CategorySetCalculator(
                        conversionService);
    }

    public double calculate(
            String equipmentId,
            String category,
            List<CategoryRule> categoryRules,
            List<WeightRule> weightRules,
            List<InspectionMeasurement> measurements) {

        boolean debug
                = "20118162".equals(
                        equipmentId);

        Map<String, List<CategoryRule>> categorySets
                = categoryRules.stream()
                        .filter(rule
                                -> category.equalsIgnoreCase(
                                rule.category()))
                        .collect(
                                Collectors.groupingBy(
                                        CategoryRule::categorySet));

        double weightedScoreTotal = 0;

        double availableWeightTotal = 0;

        if (debug) {

            System.out.println();
            System.out.println(
                    "======================================");

            System.out.println(
                    "EQUIPMENT = "
                    + equipmentId);

            System.out.println(
                    "CATEGORY  = "
                    + category);

            System.out.println(
                    "======================================");
        }

        for (var entry
                : categorySets.entrySet()) {

            String categorySet
                    = entry.getKey();

            List<CategoryRule> setRules
                    = entry.getValue();

            boolean hasMeasurement
                    = setRules.stream()
                            .anyMatch(rule
                                    -> measurements.stream()
                                    .anyMatch(m
                                            -> m.measurementName()
                                            .equalsIgnoreCase(
                                                    rule.measurementName())));

            if (!hasMeasurement) {

                if (debug) {

                    System.out.println(
                            categorySet
                            + " -> NO MEASUREMENTS");
                }

                continue;
            }

            double setScore
                    = setCalculator.calculate(
                            equipmentId,
                            setRules,
                            measurements);

            double setWeight
                    = weightRules.stream()
                            .filter(weight
                                    -> category.equalsIgnoreCase(
                                    weight.category())

                            && categorySet.equalsIgnoreCase(
                                    weight.categorySet()))
                            .findFirst()
                            .map(
                                    WeightRule::setWeight)
                            .orElse(0.0);

            weightedScoreTotal
                    += setScore * setWeight;

            availableWeightTotal
                    += setWeight;

            if (debug) {

                System.out.println();

                System.out.println(
                        "SET      = "
                        + categorySet);

                System.out.println(
                        "SCORE    = "
                        + setScore);

                System.out.println(
                        "WEIGHT   = "
                        + setWeight);
            }
        }

        double categoryScore
                = availableWeightTotal == 0
                        ? 0
                        : weightedScoreTotal
                        / availableWeightTotal;

        if (debug) {

            System.out.println();

            System.out.println(
                    "CATEGORY SCORE = "
                    + categoryScore);

            System.out.println(
                    "WEIGHTED TOTAL = "
                    + weightedScoreTotal);

            System.out.println(
                    "AVAILABLE WT   = "
                    + availableWeightTotal);

            System.out.println(
                    "======================================");
        }

        return categoryScore;
    }
}
