package tvz.java.plavi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserTasksCounter {

    private String username;
    private long taskCount;
    private float finishedPercentage;

}
