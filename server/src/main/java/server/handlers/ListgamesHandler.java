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

        // Get auth token from the request header
        String authToken = req.headers("Authorization");
        if (authToken == null || authToken.isEmpty()) {
            res.status(400); // Bad Request
            return gson.toJson(new ListGamesResponse(false, "Missing auth token"));
        }

        // Call AuthService to validate the auth token
        AuthService authService = new AuthService();
        boolean isValidToken = authService.validateAuthToken(authToken);
        if (!isValidToken) {
            res.status(401); // Unauthorized (invalid token)
            return gson.toJson(new ListGamesResponse(false, "Invalid auth token"));
        }

        // Call GameService to retrieve the list of games
        GameService gameService = new GameService();
        ListGamesResponse response = gameService.listGames();

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