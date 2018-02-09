package tvz.java.plavi.dao;

import org.springframework.data.repository.CrudRepository;
import tvz.java.plavi.model.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
    Role findById(Long id);
}
