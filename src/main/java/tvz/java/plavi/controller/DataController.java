package tvz.java.plavi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tvz.java.plavi.dao.*;
import tvz.java.plavi.model.dto.TaskAddRequest;
import tvz.java.plavi.model.dto.TaskEditRequest;
import tvz.java.plavi.model.dto.TotalUsers;
import tvz.java.plavi.model.dto.UserTasksCounter;
import tvz.java.plavi.model.entity.Project;
import tvz.java.plavi.model.entity.ProjectStakeholder;
import tvz.java.plavi.model.entity.Task;
import tvz.java.plavi.model.entity.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectStakeholderRepository projectStakeholderRepository;

    @GetMapping("/getProjects/{userId}")
    public List<Project> getProjects(@PathVariable Long userId) {
        User user  = userRepository.findById(userId);
        List<ProjectStakeholder> projectStakeholders = projectStakeholderRepository.findByUser(user);
        List<Project> projects = new ArrayList<>();
        for (ProjectStakeholder projectStakeholder : projectStakeholders) {
            projects.add(projectStakeholder.getProject());
        }
        return projects;
    }

    @GetMapping("/getTasks/{userId}/{projectId}")
    public List<Task> getTasks(@PathVariable Long userId, @PathVariable Long projectId) {
        User user  = userRepository.findById(userId);
        Project project = projectRepository.findById(projectId);
        return taskRepository.findByUserAndProject(user, project);
    }

    @GetMapping("/getProjectTasks/{projectId}/{status}")
    public List<Task> getProjectTasks(@PathVariable Long projectId, @PathVariable String status) {
        Project project = projectRepository.findById(projectId);
        return taskRepository.findByProjectAndStatus(project, status);
    }

    @GetMapping("/getProjectStakeholders/{projectId}")
    public List<UserTasksCounter> getProjectStakeholders(@PathVariable Long projectId) {
        Project project = projectRepository.findById(projectId);
        List<ProjectStakeholder> projectStakeholders = projectStakeholderRepository.findByProject(project);
        List<User> users = new ArrayList<>();
        for (ProjectStakeholder projectStakeholder : projectStakeholders) {
            users.add(projectStakeholder.getUser());
        }
        List<UserTasksCounter> userTasksCounters = new ArrayList<>();
        for (User user : users) {
            long taskCounter = taskRepository.countByUserAndProject(user, project);
            long finishedTaskCounter = taskRepository.countByUserAndProjectAndStatus(user, project, "Finished");
            float finishedPercentage = ((float)finishedTaskCounter / taskCounter) * 100.0f;
            finishedPercentage = Math.round(finishedPercentage * 100.0f) / 100.0f;
            userTasksCounters.add(new UserTasksCounter(user.getUsername(), taskCounter, finishedPercentage));
        }
        return userTasksCounters;
    }

    @GetMapping("/getAllUsers")
    public TotalUsers getAllUsers() {
        long allUsers = userRepository.count();
        long maleUsers = userRepository.countByGender("male");
        long femaleUsers = allUsers - maleUsers;
        return new TotalUsers(allUsers, maleUsers, femaleUsers);
    }
    
    @GetMapping("/getUsers")
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
    
    @GetMapping("/getAllProjects")
    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects;
    }
    
    @GetMapping("/getAllTasks")
    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }
    
    @GetMapping("/getTask/{taskId}")
    public TaskEditRequest getTask(@PathVariable Long taskId) {
        Task task = taskRepository.findOne(taskId);
        TaskEditRequest taskEditRequest= new TaskEditRequest();
        taskEditRequest.setId(taskId);
        taskEditRequest.setName(task.getName());
        taskEditRequest.setEstimated(task.getEstimated());
        taskEditRequest.setProjectId(task.getProject().getId());
        taskEditRequest.setUserId(task.getUser().getId());
        return taskEditRequest;
    }
}