package FDBackend.Controllers;

import FDBackend.Configurations.HttpResponse;
import FDBackend.Entities.User;
import FDBackend.Exceptions.ExceptionHandling;
import FDBackend.Exceptions.UserExceptions.UserEmailExist;
import FDBackend.Exceptions.UserExceptions.UserNullObject;
import FDBackend.Exceptions.UserExceptions.UserUsernameExist;
import FDBackend.Security.AuthenticationUser;
import FDBackend.Security.JWTProvider;
import FDBackend.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static FDBackend.Configurations.SecurityConstants.JWT_TOKEN_HEADER;

@RestController
@RequestMapping(value = "/users")
public class UserController extends ExceptionHandling {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtTokenProvider;

    @Autowired
    public UserController(IUserService userService, AuthenticationManager authenticationManager, JWTProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserUsernameExist, UserEmailExist {
        var result = userService.register(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) throws UserNullObject {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser =  userService.findUserByUsername(user.getUsername());
        AuthenticationUser authUser = new AuthenticationUser(loginUser);
        HttpHeaders jwtHeader = getJWTHeader(authUser);
        System.out.println(jwtHeader);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message) {
        HttpResponse body = new HttpResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), message.toUpperCase());
        return new ResponseEntity<>(body,status);
    }

    private HttpHeaders getJWTHeader(AuthenticationUser authUser) {
        HttpHeaders httpHeaders =  new HttpHeaders();
        httpHeaders.add(JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(authUser));
        return httpHeaders;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }
}
