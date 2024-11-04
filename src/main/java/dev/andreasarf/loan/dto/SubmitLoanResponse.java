package dev.andreasarf.loan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.andreasarf.loan.common.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubmitLoanResponse {

    private String userId;
    private List<Loan> loans;

//    public SubmitLoanResponse(String userId, dev.andreasarf.loan.model.Loan entity) {
//        final var loan = from(entity);
//        return new SubmitLoanResponse(userId, List.of(loan));
//    }

    public static Loan from(dev.andreasarf.loan.model.Loan entity) {
        return Loan.builder()
                .mrp(entity.getMrp())
                .dp(entity.getDp())
                .vehicleYear(entity.getVehicleYear())
                .machineNumber(entity.getMachineNumber())
                .status(entity.getStatus())
                .build();
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Loan {

        private BigDecimal mrp;
        private BigDecimal dp;
        private Integer vehicleYear;
        private String policeNumber;
        private String machineNumber;
        private LoanStatus status;
    }
}
