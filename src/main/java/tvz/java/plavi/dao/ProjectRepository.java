package tvz.java.plavi.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import tvz.java.plavi.model.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByName(String name);
    Project findById(Long id);
    List<Project> findAll();
}
