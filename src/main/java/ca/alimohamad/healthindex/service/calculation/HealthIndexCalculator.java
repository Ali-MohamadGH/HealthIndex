package ca.alimohamad.healthindex.service.calculation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ca.alimohamad.healthindex.model.AssetInspection;
import ca.alimohamad.healthindex.model.CategoryRule;
import ca.alimohamad.healthindex.model.ConversionRule;
import ca.alimohamad.healthindex.model.HealthIndexResult;
import ca.alimohamad.healthindex.model.InspectionMeasurement;
import ca.alimohamad.healthindex.model.WeightRule;

public class HealthIndexCalculator {

    private final CategoryCalculator categoryCalculator;

    private final GatewayFailureService gatewayFailureService;

    public HealthIndexCalculator(
            List<ConversionRule> conversionRules) {

        ConversionService conversionService
                = new ConversionService(
                        conversionRules);

        categoryCalculator
                = new CategoryCalculator(
                        conversionService);

        gatewayFailureService
                = new GatewayFailureService(
                        conversionService);

    }

    public HealthIndexResult calculate(
            AssetInspection inspection,
            List<CategoryRule> categoryRules,
            List<WeightRule> weightRules) {

        boolean debug
                = "20118162".equals(
                        inspection.equipmentId());

        List<InspectionMeasurement> latestMeasurements
                = getLatestMeasurements(
                        inspection.measurements());

        if (debug) {

            System.out.println();
            System.out.println(
                    "########################################");

            System.out.println(
                    "DEBUGGING EQUIPMENT "
                    + inspection.equipmentId());

            System.out.println(
                    "INSPECTION DATE "
                    + inspection.inspectionDate());

            System.out.println(
                    "########################################");

            System.out.println();
            System.out.println(
                    "Measurements Used:");

            latestMeasurements.forEach(m
                    -> System.out.println(
                            m.measurementName()
                            + " | "
                            + m.inspectionDate()
                            + " | "
                            + m.rawValue()));
        }

        Set<String> categories
                = categoryRules.stream()
                        .map(
                                CategoryRule::category)
                        .collect(
                                Collectors.toSet());

        Map<String, Double> categoryScores
                = new HashMap<>();

        double weightedCategoryTotal = 0;

        double availableCategoryWeight = 0;

        for (String category
                : categories) {

            double categoryScore
                    = categoryCalculator.calculate(
                            inspection.equipmentId(),
                            category,
                            categoryRules,
                            weightRules,
                            latestMeasurements);

            categoryScores.put(
                    category,
                    categoryScore);

            if (categoryScore <= 0) {
                continue;
            }

            double categoryWeight
                    = weightRules.stream()
                            .filter(weight
                                    -> category.equalsIgnoreCase(
                                    weight.category()))
                            .findFirst()
                            .map(
                                    WeightRule::categoryWeight)
                            .orElse(0.0);

            weightedCategoryTotal
                    += categoryScore
                    * categoryWeight;

            availableCategoryWeight
                    += categoryWeight;

            if (debug) {

                System.out.println();

                System.out.println(
                        "CATEGORY        = "
                        + category);

                System.out.println(
                        "CATEGORY SCORE  = "
                        + categoryScore);

                System.out.println(
                        "CATEGORY WEIGHT = "
                        + categoryWeight);
            }
        }

        double healthIndex
                = availableCategoryWeight == 0
                        ? 0
                        : weightedCategoryTotal
                        / availableCategoryWeight;

        boolean gatewayFail
                = gatewayFailureService.evaluate(
                        categoryRules,
                        latestMeasurements);

        boolean realgatewayfail;
        realgatewayfail = gatewayFail != true;

        return new HealthIndexResult(
                inspection.equipmentId(),
                inspection.inspectionDate(),
                healthIndex,
                categoryScores,
                realgatewayfail);
    }

    private List<InspectionMeasurement>
            getLatestMeasurements(
                    List<InspectionMeasurement> measurements) {

        return measurements.stream()
                .collect(
                        Collectors.toMap(
                                InspectionMeasurement::measurementName,
                                measurement
                                -> measurement,
                                (a, b)
                                -> a.inspectionDate()
                                        .isAfter(
                                                b.inspectionDate())
                                ? a
                                : b))
                .values()
                .stream()
                .toList();
    }
}
