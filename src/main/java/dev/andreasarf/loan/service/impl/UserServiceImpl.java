package dev.andreasarf.loan.service.impl;

import dev.andreasarf.loan.dto.CreateUserRequest;
import dev.andreasarf.loan.dto.CreateUserResponse;
import dev.andreasarf.loan.model.User;
import dev.andreasarf.loan.repository.UserRepository;
import dev.andreasarf.loan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public CreateUserResponse create(CreateUserRequest request) {
        final var entity = User.builder()
                .username(request.getUsername())
                .build();
        final var saved = repository.save(entity);
        return new CreateUserResponse(saved.getId(), saved.getUsername());
    }
}
