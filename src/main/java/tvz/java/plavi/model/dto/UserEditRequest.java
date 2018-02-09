package tvz.java.plavi.model.dto;

import lombok.Data;

@Data
public class UserEditRequest {

    private String username;
    private String firstname;
    private String lastname;
    private String gender;

}
