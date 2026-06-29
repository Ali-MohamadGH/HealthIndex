package com.limeesodaa.healthindex.service.calculation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.limeesodaa.healthindex.model.AssetInspection;
import com.limeesodaa.healthindex.model.InspectionMeasurement;

public class LatestInspectionService {

    public List<AssetInspection> getLatestInspections(
            List<InspectionMeasurement> measurements) {

        Map<String,
                Map<LocalDate,
                        List<InspectionMeasurement>>>
                grouped =

                measurements.stream()

                        .collect(
                                Collectors.groupingBy(
                                        InspectionMeasurement::equipmentId,

                                        Collectors.groupingBy(
                                                InspectionMeasurement::inspectionDate
                                        )));

        List<AssetInspection> inspections =
                new ArrayList<>();

        for (String equipment :
                grouped.keySet()) {

            LocalDate latestDate =

                    grouped.get(equipment)
                            .keySet()
                            .stream()
                            .max(LocalDate::compareTo)
                            .orElse(null);

            if (latestDate == null) {
                continue;
            }

            List<InspectionMeasurement>
                    latestMeasurements =

                    new ArrayList<>(

                            grouped.get(equipment)
                                    .get(latestDate));

            

            inspections.add(

                    new AssetInspection(

                            equipment,

                            latestDate,

                            latestMeasurements));
        }

        return inspections;
    }
}