package tvz.java.plavi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name="projects")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tasks", "projectStakeholders"})
@ToString(exclude = {"tasks", "projectStakeholders"})
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Column(unique=true, nullable=false)
    private String name;

    @JsonBackReference("projects-tasks")
    @OneToMany(mappedBy="project")
    private List<Task> tasks;

    @JsonBackReference("projects-project_stakeholders")
	@OneToMany(mappedBy="project", cascade=CascadeType.ALL)
	private List<ProjectStakeholder> projectStakeholders;
}