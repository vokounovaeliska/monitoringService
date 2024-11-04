package cz.vokounovaeliska.monitoringservice.implementation.service;

import cz.vokounovaeliska.monitoringservice.api.exception.BadRequestException;
import cz.vokounovaeliska.monitoringservice.api.exception.InternalErrorException;
import cz.vokounovaeliska.monitoringservice.api.exception.ResourceNotFoundException;
import cz.vokounovaeliska.monitoringservice.api.requests.AddUserRequest;
import cz.vokounovaeliska.monitoringservice.api.services.UserService;
import cz.vokounovaeliska.monitoringservice.dto.UserDTO;
import cz.vokounovaeliska.monitoringservice.entity.User;
import cz.vokounovaeliska.monitoringservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long add(AddUserRequest userRequest) {
        try {
            return userRepository.save(new User(userRequest.getName(), userRequest.getEmail())).getId();
        } catch (
                DataIntegrityViolationException e) {
            throw new BadRequestException("User with email: " + userRequest.getEmail() + " already exists");
        } catch (DataAccessException e) {
            LOG.error("Error while adding user", e);
            throw new InternalErrorException("Error while adding user");
        }
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDTO get(long id) {
        return userRepository.findById(id)
                .map(this::mapUserToUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User ID: " + id + " not found."));
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUserToUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByAccessToken(String accessToken) {
        return userRepository.findByAccessToken(accessToken)
                .map(this::mapUserToUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with access token: " + accessToken + " not found."));
    }

    private UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAccessToken());
    }
}
