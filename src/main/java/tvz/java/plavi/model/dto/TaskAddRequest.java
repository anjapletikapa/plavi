package tvz.java.plavi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskAddRequest {

    private String name;
    private int estimated;
    private Long projectId;
    private Long userId;

}
