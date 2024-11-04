package dev.andreasarf.loan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubmitLoanRequest {

    private String userId;
    private BigDecimal mrp;
    private BigDecimal dp;
    private Integer vehicleYear; // "vehicle_year"
    private String policeNumber;
    private String machineNumber;

}
