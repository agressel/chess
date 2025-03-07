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

        String authToken = req.headers("Authorization");
        if (authToken == null || authToken.isEmpty()) {
            res.status(400); // Bad Request
            return gson.toJson(new JoinGameResponse(false, "Missing auth token"));
        }

        AuthService authService = new AuthService();
        boolean isValidToken = authService.validateAuthToken(authToken);
        if (!isValidToken) {
            res.status(401); // Unauthorized (invalid token)
            return gson.toJson(new JoinGameResponse(false, "Invalid auth token"));
        }

        JoinGameRequest joinGameRequest = gson.fromJson(req.body(), JoinGameRequest.class);
        if (joinGameRequest == null || joinGameRequest.getPlayerColor() == null || joinGameRequest.getGameId() == null) {
            res.status(400); // Bad Request
            return gson.toJson(new JoinGameResponse(false, "Missing player color or game ID"));
        }

        GameService gameService = new GameService();
        JoinGameResponse response = gameService.joinGame(authToken, joinGameRequest.getPlayerColor(), joinGameRequest.getGameId());

        if (response.isSuccess()) {
            res.status(200);
        } else {
            res.status(500);
        }

        return gson.toJson(response);
    }
}