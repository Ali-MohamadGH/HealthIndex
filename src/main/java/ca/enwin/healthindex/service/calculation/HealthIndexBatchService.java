package ca.enwin.healthindex.service.calculation;

import java.util.ArrayList;
import java.util.List;

import ca.enwin.healthindex.model.AssetInspection;
import ca.enwin.healthindex.model.CategoryRule;
import ca.enwin.healthindex.model.ConversionRule;
import ca.enwin.healthindex.model.HealthIndexResult;
import ca.enwin.healthindex.model.InspectionMeasurement;
import ca.enwin.healthindex.model.WeightRule;
import ca.enwin.healthindex.repository.CategoryRepository;
import ca.enwin.healthindex.repository.ConversionRepository;
import ca.enwin.healthindex.repository.HealthIndexResultRepository;
import ca.enwin.healthindex.repository.InspectionRepository;
import ca.enwin.healthindex.repository.WeightRepository;

public class HealthIndexBatchService {

    private final InspectionRepository
            inspectionRepository =
            new InspectionRepository();

    private final CategoryRepository
            categoryRepository =
            new CategoryRepository();

    private final WeightRepository
            weightRepository =
            new WeightRepository();

    private final ConversionRepository
            conversionRepository =
            new ConversionRepository();

    private final HealthIndexResultRepository
            resultRepository =
            new HealthIndexResultRepository();

    private final LatestInspectionService
            latestInspectionService =
            new LatestInspectionService();

    public List<HealthIndexResult>
    calculateAll()
            throws Exception {

        List<InspectionMeasurement>
                measurements =
                inspectionRepository.findAll();

        List<AssetInspection>
                inspections =

                latestInspectionService
                        .getLatestInspections(
                                measurements);

        List<CategoryRule>
                categoryRules =

                categoryRepository.findAll();

        List<WeightRule>
                weightRules =

                weightRepository.findAll();
for (WeightRule rule :
        weightRules) {

    
}
        List<ConversionRule>
                conversionRules =

                conversionRepository.findAll();

        HealthIndexCalculator
                calculator =

                new HealthIndexCalculator(
                        conversionRules);

        List<HealthIndexResult>
                results =
                new ArrayList<>();

        for (AssetInspection inspection
                : inspections) {

            HealthIndexResult result =

                    calculator.calculate(

                            inspection,

                            categoryRules,

                            weightRules);

            resultRepository.save(
                    result);

            results.add(result);
        }

        return results;
    }
}