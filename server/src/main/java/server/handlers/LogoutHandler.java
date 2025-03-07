import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.AuthService;
import model.response.LogoutResponse;

public class LogoutHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();

        String authToken = req.headers("Authorization");
        if (authToken == null || authToken.isEmpty()) {
            res.status(400); // Bad Request
            return gson.toJson(new LogoutResponse(false, "Missing auth token"));
        }

        AuthService authService = new AuthService();
        LogoutResponse response = authService.logout(authToken);

        if (response.isSuccess()) {
            res.status(200);
        } else {
            res.status(401);
        }

        return gson.toJson(response);
    }
}