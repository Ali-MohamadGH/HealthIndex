package com.limeesodaa.healthindex.service.calculation;

import java.sql.SQLException;
import java.util.List;

import com.limeesodaa.healthindex.model.ConversionRule;
import com.limeesodaa.healthindex.repository.ConversionRepository;

public class ConversionService {

    private final List<ConversionRule>
            conversionRules;

    public ConversionService()
            throws SQLException {

        conversionRules =
                new ConversionRepository()
                        .findAll();
    }

    public ConversionService(
            List<ConversionRule>
                    conversionRules) {

        this.conversionRules =
                conversionRules;
    }

    public double convert(

            String codeGroup,

            double rawValue) {

        return conversionRules.stream()

                .filter(rule ->

                        rule.codeGroup()
                                .equalsIgnoreCase(
                                        codeGroup)

                                && Double.compare(
                                        rule.sourceValue(),
                                        rawValue) == 0)

                .findFirst()

                .map(
                        ConversionRule::
                                convertedValue)

                .orElse(rawValue);
    }
}