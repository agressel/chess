package server.handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.GameService;
import service.AuthService;
import model.response.CreateGameResponse;
import model.request.CreateGameRequest;

public class CreateGameHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();

        String authToken = req.headers("Authorization");
        if (authToken == null || authToken.isEmpty()) {
            res.status(400); // Bad Request
            return gson.toJson(new CreateGameResponse(false, "Missing auth token", null));
        }

        AuthService authService = new AuthService();
        boolean isValidToken = authService.validateAuthToken(authToken);
        if (!isValidToken) {
            res.status(401); // Unauthorized (invalid token)
            return gson.toJson(new CreateGameResponse(false, "Invalid auth token", null));
        }

        CreateGameRequest createGameRequest = gson.fromJson(req.body(), CreateGameRequest.class);
        if (createGameRequest == null || createGameRequest.getGameName() == null || createGameRequest.getGameName().isEmpty()) {
            res.status(400); // Bad Request
            return gson.toJson(new CreateGameResponse(false, "Missing game name", null));
        }

        GameService gameService = new GameService();
        CreateGameResponse response = gameService.createGame(authToken, createGameRequest.getGameName());

        if (response.isSuccess()) {
            res.status(200); // OK
        } else {
            res.status(500); // Internal Server Error
        }

        return gson.toJson(response);
    }
}