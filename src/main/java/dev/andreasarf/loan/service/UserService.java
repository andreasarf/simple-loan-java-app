package dev.andreasarf.loan.service;

import dev.andreasarf.loan.dto.CreateUserRequest;
import dev.andreasarf.loan.dto.CreateUserResponse;

public interface UserService {

    CreateUserResponse create(CreateUserRequest request);
}
