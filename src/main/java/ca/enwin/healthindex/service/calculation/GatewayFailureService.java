package ca.enwin.healthindex.service.calculation;

import java.util.List;

import ca.enwin.healthindex.model.CategoryRule;
import ca.enwin.healthindex.model.InspectionMeasurement;

public class GatewayFailureService {

    private final ConversionService
            conversionService;

    public GatewayFailureService(
            ConversionService conversionService) {

        this.conversionService =
                conversionService;
    }

    public boolean evaluate(

            List<CategoryRule> rules,

            List<InspectionMeasurement>
                    measurements) {

        for (CategoryRule rule :
                rules) {

            if (!rule.gatewayFail()) {
                continue;
            }

            InspectionMeasurement measurement =

                    measurements.stream()

                            .filter(m ->

                                    m.measurementName()
                                            .equalsIgnoreCase(
                                                    rule.measurementName()))

                            .findFirst()

                            .orElse(null);

            if (measurement == null) {
                continue;
            }

            double convertedValue =

                    conversionService.convert(

                            measurement.codeGroup(),

                            measurement.rawValue());

            if (convertedValue
                    <= rule.gatewayTestValue() && rule.gatewayFail()==true) {

                return false;
            }
        }

        return true;
    }
}