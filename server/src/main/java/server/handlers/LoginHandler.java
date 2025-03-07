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
        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);

        String validationMessage = validateInput(loginRequest);
        if (validationMessage != null) {
            res.status(400); // Bad Request
            return gson.toJson(new LoginResponse(false, validationMessage, null));
        }

        AuthService authService = new AuthService();
        LoginResponse response = authService.login(loginRequest);

        if (response.isSuccess()) {
            res.status(200); // OK
        } else {
            res.status(401); // Unauthorized (invalid credentials)
        }

        return gson.toJson(response);
    }

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