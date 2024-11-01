package cz.vokounovaeliska.monitoringservice.controller;

import cz.vokounovaeliska.monitoringservice.api.requests.AddUserRequest;
import cz.vokounovaeliska.monitoringservice.api.services.UserService;
import cz.vokounovaeliska.monitoringservice.dto.UserDTO;
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

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.get(id));
    }

    @PostMapping()
    public ResponseEntity<Long> add(@RequestBody AddUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(request));
    }
}