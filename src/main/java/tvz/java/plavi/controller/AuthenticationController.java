package tvz.java.plavi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;
import tvz.java.plavi.dao.RoleRepository;
import tvz.java.plavi.dao.UserRepository;
import tvz.java.plavi.model.dto.UserLoginRequest;
import tvz.java.plavi.model.entity.Role;
import tvz.java.plavi.model.entity.User;
import tvz.java.plavi.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final LogoutHandler logoutHandler;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthenticationController(LogoutHandler logoutHandler, AuthenticationManager authenticationManager, UserService userService) {
        this.logoutHandler = logoutHandler;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest user) {
        boolean isAuth = false;
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            if (authenticate.isAuthenticated()) {
                isAuth = true;
            }
        } catch (Exception e) {
            isAuth = false;
        }

        if (isAuth) {
            return new ResponseEntity<User>(userService.getUserByUsername(authenticate.getName()), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Credentials", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }
    }

    @GetMapping("/getLoggedUser")
    public ResponseEntity<?> getClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            return new ResponseEntity<User>(userService.getUserByUsername(authentication.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
}