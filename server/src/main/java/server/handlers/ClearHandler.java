package server.handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.GameService;
import model.response.ClearResponse;

public class ClearHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();

        GameService gameService = new GameService();
        ClearResponse response = gameService.clearGames();

        if (response.isSuccess()) {
            res.status(200); // OK
        } else {
            res.status(500); // Internal Server Error
        }

        return gson.toJson(response);
    }
}