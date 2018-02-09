package tvz.java.plavi.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="tasks")
@Data
@NoArgsConstructor
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Column(unique=true, nullable=false)
	private String name;

    @Column(nullable=false)
	private String status;

    @Column(nullable=false)
    private int estimated;

    @Column(nullable=false)
	private Date created;

    @JsonManagedReference("users-tasks")
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonManagedReference("projects-tasks")
    @ManyToOne
    @JoinColumn(name="project_id")
	private Project project;
}