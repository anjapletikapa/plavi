package tvz.java.plavi.dao;

import org.springframework.data.repository.CrudRepository;
import tvz.java.plavi.model.entity.Project;
import tvz.java.plavi.model.entity.ProjectStakeholder;
import tvz.java.plavi.model.entity.User;

import java.util.List;

public interface ProjectStakeholderRepository extends CrudRepository<ProjectStakeholder, Long> {
    List<ProjectStakeholder> findByUser(User user);
    List<ProjectStakeholder> findByProject(Project project);
    ProjectStakeholder findByProjectAndUser(Project project, User user);
}
