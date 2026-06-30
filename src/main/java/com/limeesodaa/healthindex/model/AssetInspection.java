package com.limeesodaa.healthindex.model;

import java.time.LocalDate;
import java.util.List;

public record AssetInspection(
        String equipmentId,
        LocalDate inspectionDate,
        List<InspectionMeasurement> measurements
        ) {

}
