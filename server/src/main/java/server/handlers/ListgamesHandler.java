package server.handlers;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.GameService;
import service.AuthService;
import model.response.ListGamesResponse;

public class ListgamesHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();

        String authToken = req.headers("Authorization");
        if (authToken == null || authToken.isEmpty()) {
            res.status(400);
            return gson.toJson(new ListGamesResponse(false, "Missing auth token"));
        }

        AuthService authService = new AuthService();
        boolean isValidToken = authService.validateAuthToken(authToken);
        if (!isValidToken) {
            res.status(401);
            return gson.toJson(new ListGamesResponse(false, "Invalid auth token"));
        }

        GameService gameService = new GameService();
        ListGamesResponse response = gameService.listGames();

        if (response.isSuccess()) {
            res.status(200);
        } else {
            res.status(500);
        }

        return gson.toJson(response);
    }
}