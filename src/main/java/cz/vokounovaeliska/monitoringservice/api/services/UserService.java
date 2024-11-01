package cz.vokounovaeliska.monitoringservice.api.services;

import cz.vokounovaeliska.monitoringservice.api.requests.AddUserRequest;
import cz.vokounovaeliska.monitoringservice.dto.UserDTO;

import java.util.List;

public interface UserService {
    long add(AddUserRequest userRequest);

    void delete(long id);

    UserDTO get(long id);

    List<UserDTO> getAll();
}