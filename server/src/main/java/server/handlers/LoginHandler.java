package server.handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import service.AuthService;
import model.request.LoginRequest;
import model.response.LoginResponse;

public class LoginHandler {
    public String handleRequest(Request req, Response res) {
        // Deserialize the incoming JSON request body to a LoginRequest object
        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);

        // Validate the input data
        String validationMessage = validateInput(loginRequest);
        if (validationMessage != null) {
            res.status(400); // Bad Request
            return gson.toJson(new LoginResponse(false, validationMessage, null));
        }

        // Call the AuthService to attempt login
        AuthService authService = new AuthService();
        LoginResponse response = authService.login(loginRequest);

        // Set the appropriate status code based on the response
        if (response.isSuccess()) {
            res.status(200); // OK
        } else {
            res.status(401); // Unauthorized (invalid credentials)
        }

        return gson.toJson(response);
    }

    // Validate user input (username, password)
    private String validateInput(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return "Username cannot be empty.";
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return "Password cannot be empty.";
        }
        return null; // No validation errors
    }
}