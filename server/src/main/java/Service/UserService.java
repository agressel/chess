package Service;

import model.User;
import model.response.UserResponse;
import model.request.RegisterRequest;
import model.request.LoginRequest;
import dataaccess.UserDataAccess;
import dataaccess.AuthDataAccess;

public class UserService {
    private UserDataAccess userDataAccess;
    private AuthDataAccess authDataAccess;

    public UserService() {
        this.userDataAccess = new UserDataAccess();
        this.authDataAccess = new AuthDataAccess();
    }

    public UserResponse registerUser(RegisterRequest registerRequest) {
        if (registerRequest == null || registerRequest.getUsername() == null || registerRequest.getPassword() == null) {
            return new UserResponse(false, "Invalid registration request.");
        }

        // Check if the username is already taken
        if (userDataAccess.getUser(registerRequest.getUsername()) != null) {
            return new UserResponse(false, "Username already exists.");
        }

        User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        userDataAccess.createUser(newUser);

        String authToken = authDataAccess.createAuth(newUser);

        return new UserResponse(true, "Registration successful.", authToken);
    }

    public UserResponse loginUser(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return new UserResponse(false, "Invalid login request.");
        }

        User user = userDataAccess.getUser(loginRequest.getUsername());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return new UserResponse(false, "Invalid username or password.");
        }

        String authToken = authDataAccess.createAuth(user);

        return new UserResponse(true, "Login successful.", authToken);
    }
}