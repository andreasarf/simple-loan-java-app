package dev.andreasarf.loan.service.impl;

import dev.andreasarf.loan.common.LoanStatus;
import dev.andreasarf.loan.dto.ApproveLoanRequest;
import dev.andreasarf.loan.dto.ApproveLoanResponse;
import dev.andreasarf.loan.dto.SubmitLoanRequest;
import dev.andreasarf.loan.dto.SubmitLoanResponse;
import dev.andreasarf.loan.model.Loan;
import dev.andreasarf.loan.model.User;
import dev.andreasarf.loan.repository.LoanRepository;
import dev.andreasarf.loan.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// junit + mockito
@SpringJUnitConfig(LoanServiceImpl.class)
class LoanServiceImplTest {

    @Autowired
    private LoanServiceImpl service;
    @MockBean
    private LoanRepository repository;
    @MockBean
    private UserRepository userRepository;


    @Test
    void givenValidRequest_whenSubmit_thenShouldSaveData() {
        // arrange
        final var request = SubmitLoanRequest.builder()
                .userId("user-1")
                .mrp(BigDecimal.ONE)
                .dp(BigDecimal.ONE)
                .policeNumber("police")
                .machineNumber("machine")
                .build();
        final var user = User.builder()
                .id(1L)
                .username(request.getUserId())
                .build();
        final var entity = Loan.builder()
                .user(user)
                .mrp(request.getMrp())
                .dp(request.getDp())
                .policeNumber(request.getPoliceNumber())
                .machineNumber(request.getMachineNumber())
                .status(LoanStatus.SUBMITTED)
                .build();
        final var response = Optional.of(new SubmitLoanResponse(entity.getUser().getUsername(),
                List.of(SubmitLoanResponse.from(entity))));

        Mockito.when(userRepository.findByUsername(request.getUserId()))
                .thenReturn(Optional.of(user));
        Mockito.when(repository.save(Mockito.any())).thenReturn(entity);

        // act
        final var actual = service.submit(request);

        // assert
        assertEquals(response, actual);
    }

    @Test
    void givenUserNotExist_whenSubmit_thenShouldReturnEmpty() {
        // arrange
        final var request = SubmitLoanRequest.builder()
                .userId("user-1")
                .mrp(BigDecimal.ONE)
                .dp(BigDecimal.ONE)
                .policeNumber("police")
                .machineNumber("machine")
                .build();

        Mockito.when(userRepository.findByUsername(request.getUserId()))
                .thenReturn(Optional.empty());

        // act
        final var actual = service.submit(request);

        // assert
        assertTrue(actual.isEmpty());
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void givenValidRequest_whenApprove_thenShouldUpdateData() {
        // arrange
        final var request = new ApproveLoanRequest("user-id", "pol-number");
        final var user = User.builder()
                .id(1L)
                .username(request.getUserId())
                .build();
        final var entity = Loan.builder()
                .user(user)
                .mrp(BigDecimal.ONE)
                .dp(BigDecimal.ONE)
                .policeNumber(request.getPoliceNumber())
                .machineNumber("12345")
                .status(LoanStatus.SUBMITTED)
                .build();
        final var saved = Loan.builder()
                .user(user)
                .mrp(BigDecimal.ONE)
                .dp(BigDecimal.ONE)
                .policeNumber(request.getPoliceNumber())
                .machineNumber("12345")
                .status(LoanStatus.APPROVED)
                .build();
        final var response = Optional.of(ApproveLoanResponse.builder()
                .userId(request.getUserId())
                .policeNumber(request.getPoliceNumber())
                .message("Loan updated successfully.")
                .build());
        final var captor = ArgumentCaptor.forClass(Loan.class);

        Mockito.when(repository.findByUsernameAndPoliceNumber(request.getUserId(), request.getPoliceNumber()))
                .thenReturn(Optional.of(entity));


        // act
        final var actual = service.approve(request);

        // assert
        assertEquals(response, actual);
        Mockito.verify(repository).save(captor.capture());
        assertEquals(LoanStatus.APPROVED, captor.getValue().getStatus());
        assertEquals(saved, captor.getValue());
    }
}
