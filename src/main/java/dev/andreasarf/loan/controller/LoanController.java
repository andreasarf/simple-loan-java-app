package dev.andreasarf.loan.controller;

import dev.andreasarf.loan.common.ApiConstant;
import dev.andreasarf.loan.dto.ApproveLoanRequest;
import dev.andreasarf.loan.dto.ErrorResponse;
import dev.andreasarf.loan.dto.SubmitLoanRequest;
import dev.andreasarf.loan.dto.SubmitLoanResponse;
import dev.andreasarf.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.V1_ROOT)
public class LoanController {

    private final LoanService service;

    // dummy
    @GetMapping("/echo")
    public ResponseEntity<String> echo() {
        log.debug("Accepting echo");
        return ResponseEntity.ok("echo");
    }

    @PostMapping
    public ResponseEntity<SubmitLoanResponse> submit(@RequestBody SubmitLoanRequest request) {
        log.info("Submit new lona {}", request);
        final var responseOpt = service.submit(request);

        if (responseOpt.isPresent()) {
            return ResponseEntity.ok(responseOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approve(@RequestBody ApproveLoanRequest request) {
        log.info("Approve loan {}", request);
        final var responseOpt = service.approve(request);

        if (responseOpt.isPresent()) {
            return ResponseEntity.ok(responseOpt.get());
        }
        final var error = new ErrorResponse("loan_not_found", "Loan not Found");
        return ResponseEntity.badRequest().body(error);
    }

}
