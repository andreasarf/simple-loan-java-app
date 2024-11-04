package dev.andreasarf.loan.service.impl;

import dev.andreasarf.loan.common.LoanStatus;
import dev.andreasarf.loan.dto.ApproveLoanRequest;
import dev.andreasarf.loan.dto.ApproveLoanResponse;
import dev.andreasarf.loan.dto.SubmitLoanRequest;
import dev.andreasarf.loan.dto.SubmitLoanResponse;
import dev.andreasarf.loan.model.Loan;
import dev.andreasarf.loan.repository.LoanRepository;
import dev.andreasarf.loan.repository.UserRepository;
import dev.andreasarf.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;
    private final UserRepository userRepository;

    @Override
    public Optional<SubmitLoanResponse> submit(SubmitLoanRequest request) {
        final var user = userRepository.findByUsername(request.getUserId());
        if (user.isPresent()) {
            final var entity = Loan.builder()
                    .user(user.get())
                    .mrp(request.getMrp())
                    .dp(request.getDp())
                    .policeNumber(request.getPoliceNumber())
                    .machineNumber(request.getMachineNumber())
                    .status(LoanStatus.SUBMITTED)
                    .build();
            final var saved = repository.save(entity);
            return Optional.of(new SubmitLoanResponse(saved.getUser().getUsername(),
                    List.of(SubmitLoanResponse.from(entity))));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ApproveLoanResponse> approve(ApproveLoanRequest request) {
        final var loanOpt = repository.findByUsernameAndPoliceNumber(request.getUserId(), request.getPoliceNumber());

        if (loanOpt.isPresent()) {
            final var loan = loanOpt.get();
            loan.setStatus(LoanStatus.APPROVED);
            repository.save(loan);
            return Optional.of(ApproveLoanResponse.builder()
                            .userId(request.getUserId())
                            .policeNumber(request.getPoliceNumber())
                            .message("Loan updated successfully.")
                    .build());
        }

        return Optional.empty();
    }
}
