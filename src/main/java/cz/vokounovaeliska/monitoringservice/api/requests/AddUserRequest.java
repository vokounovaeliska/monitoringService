package cz.vokounovaeliska.monitoringservice.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {
    private String name;
    private String email;
}
