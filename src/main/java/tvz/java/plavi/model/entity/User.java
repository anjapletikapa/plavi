package tvz.java.plavi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tasks", "projectStakeholders"})
@ToString(exclude = {"tasks", "projectStakeholders"})
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String firstname;

    @Column(nullable=false)
    private String lastname;

    @Column(unique=true, nullable=false)
    private String username;

    @JsonIgnore
    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String gender;

    @JsonManagedReference("roles-users")
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @JsonBackReference("users-tasks")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @JsonBackReference("users-project_stakeholders")
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<ProjectStakeholder> projectStakeholders;
}
