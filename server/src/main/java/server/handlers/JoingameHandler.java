package server.handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.GameService;
import service.AuthService;
import model.response.JoinGameResponse;
import model.request.JoinGameRequest;

public class JoinGameHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();

        // Get auth token from the request header
        String authToken = req.headers("Authorization");
        if (authToken == null || authToken.isEmpty()) {
            res.status(400); // Bad Request
            return gson.toJson(new JoinGameResponse(false, "Missing auth token"));
        }

        // Call AuthService to validate the auth token
        AuthService authService = new AuthService();
        boolean isValidToken = authService.validateAuthToken(authToken);
        if (!isValidToken) {
            res.status(401); // Unauthorized (invalid token)
            return gson.toJson(new JoinGameResponse(false, "Invalid auth token"));
        }

        // Parse request body to get playerColor and gameId
        JoinGameRequest joinGameRequest = gson.fromJson(req.body(), JoinGameRequest.class);
        if (joinGameRequest == null || joinGameRequest.getPlayerColor() == null || joinGameRequest.getGameId() == null) {
            res.status(400); // Bad Request
            return gson.toJson(new JoinGameResponse(false, "Missing player color or game ID"));
        }

        // Call GameService to join the game
        GameService gameService = new GameService();
        JoinGameResponse response = gameService.joinGame(authToken, joinGameRequest.getPlayerColor(), joinGameRequest.getGameId());

        // Set status based on response success/failure
        if (response.isSuccess()) {
            res.status(200); // OK
        } else {
            res.status(500); // Internal Server Error
        }

        // Return the response as JSON
        return gson.toJson(response);
    }
}