package tvz.java.plavi.dao;

import org.springframework.data.repository.CrudRepository;
import tvz.java.plavi.model.entity.Project;
import tvz.java.plavi.model.entity.Task;
import tvz.java.plavi.model.entity.User;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findByName(String name);
    List<Task> findByUserAndProject(User user, Project project);
    List<Task> findByProjectAndStatus(Project project, String status);
    Long countByUserAndProjectAndStatus(User user, Project project, String status);
    Long countByUserAndProject(User user, Project project);
    List<Task> findAll();
}
