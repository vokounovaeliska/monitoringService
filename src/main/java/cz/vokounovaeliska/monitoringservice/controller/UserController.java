package cz.vokounovaeliska.monitoringservice.controller;

import cz.vokounovaeliska.monitoringservice.api.requests.AddUserRequest;
import cz.vokounovaeliska.monitoringservice.api.services.UserService;
import cz.vokounovaeliska.monitoringservice.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get a list of all users")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @Operation(summary = "Get user details by ID")
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.get(id));
    }

    @Operation(summary = "Create a new user")
    @PostMapping()
    public ResponseEntity<Long> add(
            @Parameter(description = "User details to create a new user", required = true)
            @RequestBody @Valid AddUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(request));
    }
}
