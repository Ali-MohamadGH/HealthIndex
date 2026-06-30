package ca.alimohamad.healthindex.service.calculation;

import java.util.List;

import ca.alimohamad.healthindex.model.CategoryRule;
import ca.alimohamad.healthindex.model.InspectionMeasurement;

public class CategorySetCalculator {

    private final ConversionService conversionService;

    public CategorySetCalculator(
            ConversionService conversionService) {

        this.conversionService
                = conversionService;
    }

    public double calculate(
            String equipmentId,
            List<CategoryRule> rules,
            List<InspectionMeasurement> measurements) {

        boolean debug
                = "20118162".equals(
                        equipmentId);

        double actualTotal = 0;

        double maximumTotal = 0;

        if (debug) {

            System.out.println();
            System.out.println(
                    "#################################");

            System.out.println(
                    "SET START");

            System.out.println(
                    "CATEGORY     = "
                    + rules.get(0).category());

            System.out.println(
                    "CATEGORY SET = "
                    + rules.get(0).categorySet());

            System.out.println(
                    "#################################");
        }

        for (CategoryRule rule
                : rules) {

            InspectionMeasurement measurement
                    = measurements.stream()
                            .filter(m
                                    -> m.measurementName()
                                    .equalsIgnoreCase(
                                            rule.measurementName()))
                            .findFirst()
                            .orElse(null);

            if (measurement == null) {

                if (debug) {

                    System.out.println(
                            "MISSING: "
                            + rule.measurementName());
                }

                continue;
            }

            double convertedValue
                    = conversionService.convert(
                            measurement.codeGroup(),
                            measurement.rawValue());

            if (measurement.rawValue() == -1 || measurement.rawValue() > 5) {
                continue;
            }

            actualTotal
                    += convertedValue;

            maximumTotal
                    += rule.maximumValue();

            if (debug) {

                System.out.println();

                System.out.println(
                        "Measurement : "
                        + measurement.measurementName());

                System.out.println(
                        "Raw Value   : "
                        + measurement.rawValue());

                System.out.println(
                        "Code Group  : "
                        + measurement.codeGroup());

                System.out.println(
                        "Converted   : "
                        + convertedValue);

                System.out.println(
                        "Maximum     : "
                        + rule.maximumValue());

                System.out.println(
                        "Actual Tot  : "
                        + actualTotal);

                System.out.println(
                        "Max Tot     : "
                        + maximumTotal);

                System.out.println(
                        "-----------------------");
            }
        }

        double setScore
                = maximumTotal == 0
                        ? 0
                        : actualTotal
                        / maximumTotal;

        if (debug) {

            System.out.println();

            System.out.println(
                    "FINAL ACTUAL TOTAL = "
                    + actualTotal);

            System.out.println(
                    "FINAL MAX TOTAL    = "
                    + maximumTotal);

            System.out.println(
                    "FINAL SET SCORE    = "
                    + setScore);

            System.out.println(
                    "#################################");
        }

        return setScore;
    }
}
