package dev.andreasarf.loan.controller;

import dev.andreasarf.loan.common.ApiConstant;
import dev.andreasarf.loan.dto.CreateUserRequest;
import dev.andreasarf.loan.dto.CreateUserResponse;
import dev.andreasarf.loan.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.V1_USER)
public class UserController {

    private final UserService service;

    // POST /api/v1/loan/user
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        log.info("Accepting create user {}", request);
        final var response = service.create(request);
        return ResponseEntity.ok(response);
    }
}
