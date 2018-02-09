package tvz.java.plavi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskEditRequest {

	private Long id;
    private String name;
    private int estimated;
    private Long projectId;
    private Long userId;

}
