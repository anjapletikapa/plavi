package tvz.java.plavi.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="project_stakeholders")
@Data
@NoArgsConstructor
public class ProjectStakeholder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @JsonManagedReference("projects-project_stakeholders")
    @ManyToOne
    @JoinColumn(name="project_id")
	private Project project;

    @JsonManagedReference("users-project_stakeholders")
    @ManyToOne
    @JoinColumn(name="user_id")
	private User user;
}