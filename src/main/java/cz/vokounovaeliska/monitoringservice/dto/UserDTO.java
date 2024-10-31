package cz.vokounovaeliska.monitoringservice.dto;

import lombok.Value;

@Value
public class UserDTO {
    long id;
    String name;
    String email;
    String accessToken;

}
