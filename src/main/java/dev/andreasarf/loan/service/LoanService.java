package dev.andreasarf.loan.service;

import dev.andreasarf.loan.dto.ApproveLoanRequest;
import dev.andreasarf.loan.dto.ApproveLoanResponse;
import dev.andreasarf.loan.dto.SubmitLoanRequest;
import dev.andreasarf.loan.dto.SubmitLoanResponse;

import java.util.Optional;

public interface LoanService {
    Optional<SubmitLoanResponse> submit(SubmitLoanRequest request);

    Optional<ApproveLoanResponse> approve(ApproveLoanRequest request);
}
