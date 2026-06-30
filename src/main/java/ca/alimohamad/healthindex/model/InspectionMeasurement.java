package ca.alimohamad.healthindex.model;

import java.time.LocalDate;

public record InspectionMeasurement(
        String equipmentId,
        LocalDate inspectionDate,
        String measurementName,
        String codeGroup,
        double rawValue
        ) {

}
