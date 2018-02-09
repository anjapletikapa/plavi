package tvz.java.plavi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TotalUsers {

    private long allUsers;
    private long maleUsers;
    private long femaleUsers;
}
