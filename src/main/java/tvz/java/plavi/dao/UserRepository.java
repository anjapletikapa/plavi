package tvz.java.plavi.dao;

import org.springframework.data.repository.CrudRepository;
import tvz.java.plavi.model.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String username);
    User findById(Long id);
    Long countByGender(String gender);
    List<User> findAll();
}
