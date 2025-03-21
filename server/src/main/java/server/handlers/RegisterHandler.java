package server.handlers;

import spark.*;
import com.google.gson.Gson;
import server.services.UserService;
import server.models.RegisterRequest;
import server.models.RegisterResponse;

public class RegisterHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(req.body(), RegisterRequest.class);

        String validationMessage = validateInput(registerRequest);
        if (validationMessage != null) {
            res.status(400); // Bad Request
            return gson.toJson(new RegisterResponse(false, validationMessage));
        }

        UserService userService = new UserService();
        RegisterResponse response = userService.register(registerRequest);

        if (response.isSuccess()) {
            res.status(201); // Created
        } else {
            res.status(409); // Conflict (e.g., username already taken)
        }

        return gson.toJson(response);
    }

    private String validateInput(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isEmpty() || request.getUsername().length() < 3) {
            return "Username must be at least 3 characters long.";
        }
        if (request.getPassword() == null || request.getPassword().isEmpty() || request.getPassword().length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        if (request.getEmail() == null || request.getEmail().isEmpty() || !request.getEmail().contains("@")) {
            return "Invalid email address.";
        }
        return null;
    }
}