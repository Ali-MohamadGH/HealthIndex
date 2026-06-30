package ca.alimohamad.healthindex.service.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ca.alimohamad.healthindex.model.AssetInspection;
import ca.alimohamad.healthindex.model.CategoryRule;
import ca.alimohamad.healthindex.model.ConversionRule;
import ca.alimohamad.healthindex.model.HealthIndexResult;
import ca.alimohamad.healthindex.model.InspectionMeasurement;
import ca.alimohamad.healthindex.model.WeightRule;
import ca.alimohamad.healthindex.repository.CategoryRepository;
import ca.alimohamad.healthindex.repository.ConversionRepository;
import ca.alimohamad.healthindex.repository.HealthIndexResultRepository;
import ca.alimohamad.healthindex.repository.InspectionRepository;
import ca.alimohamad.healthindex.repository.WeightRepository;

public class HealthIndexBatchService {

    private final InspectionRepository inspectionRepository
            = new InspectionRepository();

    private final CategoryRepository categoryRepository
            = new CategoryRepository();

    private final WeightRepository weightRepository
            = new WeightRepository();

    private final ConversionRepository conversionRepository
            = new ConversionRepository();

    private final HealthIndexResultRepository resultRepository
            = new HealthIndexResultRepository();

    private final LatestInspectionService latestInspectionService
            = new LatestInspectionService();

    public List<HealthIndexResult>
            calculateAll(Consumer<String> logger)
            throws Exception {

        List<InspectionMeasurement> measurements
                = inspectionRepository.findAll();

        List<AssetInspection> inspections
                = latestInspectionService
                        .getLatestInspections(
                                measurements);

        List<CategoryRule> categoryRules
                = categoryRepository.findAll();

        List<WeightRule> weightRules
                = weightRepository.findAll();

        List<ConversionRule> conversionRules
                = conversionRepository.findAll();

        HealthIndexCalculator calculator
                = new HealthIndexCalculator(
                        conversionRules);

        List<HealthIndexResult> results
                = new ArrayList<>();

        for (AssetInspection inspection
                : inspections) {

            HealthIndexResult result
                    = calculator.calculate(
                            inspection,
                            categoryRules,
                            weightRules);

            resultRepository.save(
                    result);

            logger.accept(
                    "Calculating "
                    + inspection.equipmentId()
                    + "...\n");

            results.add(result);
        }

        return results;
    }
}
