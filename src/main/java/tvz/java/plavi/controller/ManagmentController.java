package tvz.java.plavi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tvz.java.plavi.dao.*;
import tvz.java.plavi.model.dto.TaskAddRequest;
import tvz.java.plavi.model.dto.TaskEditRequest;
import tvz.java.plavi.model.dto.UserAddRequest;
import tvz.java.plavi.model.dto.UserEditRequest;
import tvz.java.plavi.model.entity.Project;
import tvz.java.plavi.model.entity.ProjectStakeholder;
import tvz.java.plavi.model.entity.Task;
import tvz.java.plavi.model.entity.User;

import java.sql.Date;

@RestController
@RequestMapping("/api")
public class ManagmentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectStakeholderRepository projectStakeholderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserAddRequest userAddRequest) {
        if (userRepository.findByUsername(userAddRequest.getUsername()) != null) {
            return new ResponseEntity<String>("Username is already taken!", HttpStatus.NOT_ACCEPTABLE);
        }
        User user = new User();
        user.setUsername(userAddRequest.getUsername());
        user.setFirstname(userAddRequest.getFirstname());
        user.setLastname(userAddRequest.getLastname());
        user.setGender(userAddRequest.getGender());
        user.setPassword(passwordEncoder.encode(userAddRequest.getPassword()));
        Long roleId = userAddRequest.getRoleId() != null ? userAddRequest.getRoleId() : 1;
        user.setRole(roleRepository.findById(roleId));
        userRepository.save(user);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping("/editUser")
    public ResponseEntity<?> editUser(@RequestBody UserEditRequest userEditRequest) {
        User user = userRepository.findByUsername(userEditRequest.getUsername());
        user.setFirstname(userEditRequest.getFirstname());
        user.setLastname(userEditRequest.getLastname());
        user.setGender(userEditRequest.getGender());
        userRepository.save(user);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(value = "/finishTask/{id}")
    public ResponseEntity<?> finishTask(@PathVariable Long id) {
        Task task = taskRepository.findOne(id);
        task.setStatus("Finished");
        taskRepository.save(task);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    @PostMapping("/addProject")
    public ResponseEntity<?> addProject(@RequestBody String name) {
        Project project = new Project();
        project.setName(name);
        projectRepository.save(project);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    @PostMapping("/addTask")
    public ResponseEntity<?> addProject(@RequestBody TaskAddRequest taskAddRequest) {
        Task task = new Task();
        java.util.Date utilDate = new java.util.Date();
        task.setCreated(new Date(utilDate.getTime()));
        task.setStatus("In progress");
        task.setName(taskAddRequest.getName());
        task.setProject(projectRepository.findById(taskAddRequest.getProjectId()));
        task.setUser(userRepository.findById(taskAddRequest.getUserId()));
        task.setEstimated(taskAddRequest.getEstimated());
        taskRepository.save(task);
        
        User user = userRepository.findById(taskAddRequest.getUserId());
        Project project = projectRepository.findById(taskAddRequest.getProjectId());
        ProjectStakeholder projectStakeholder = projectStakeholderRepository.findByProjectAndUser(project, user);
        if (projectStakeholder == null) {
        	projectStakeholder = new ProjectStakeholder();
        	projectStakeholder.setProject(project);
        	projectStakeholder.setUser(user);
        	projectStakeholderRepository.save(projectStakeholder);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findOne(id);
        User user = task.getUser();
        Project project = task.getProject();
        taskRepository.delete(task);
        
        Long count = taskRepository.countByUserAndProject(user, project);
        if (count == 0) {
        	ProjectStakeholder projectStakeholder = projectStakeholderRepository.findByProjectAndUser(project, user);
        	projectStakeholderRepository.delete(projectStakeholder);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    @PutMapping("/editTask")
    public ResponseEntity<?> editTask(@RequestBody TaskEditRequest taskEditRequest) {
        Task task = taskRepository.findOne(taskEditRequest.getId());
        task.setName(taskEditRequest.getName());
        task.setEstimated(taskEditRequest.getEstimated());
        Project project = projectRepository.findById(taskEditRequest.getProjectId());
        User user = userRepository.findById(taskEditRequest.getUserId());
        task.setProject(project);
        task.setUser(user);
        taskRepository.save(task);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}