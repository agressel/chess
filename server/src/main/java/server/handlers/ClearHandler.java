package server.handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.GameService;
import model.response.ClearResponse;

public class ClearHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();

        // Call GameService to clear all games
        GameService gameService = new GameService();
        ClearResponse response = gameService.clearGames();

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