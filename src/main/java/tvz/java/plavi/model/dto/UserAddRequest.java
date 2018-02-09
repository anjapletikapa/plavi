package tvz.java.plavi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.java.plavi.model.entity.Role;

@Data
@NoArgsConstructor
public class UserAddRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String gender;
    private Long roleId;

}
