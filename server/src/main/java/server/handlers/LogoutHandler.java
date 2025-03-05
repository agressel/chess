import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import service.AuthService;
import model.response.LogoutResponse;

public class LogoutHandler {
    public String handleRequest(Request req, Response res) {
        Gson gson = new Gson();

        // Get auth token from the request header
        String authToken = req.headers("Authorization");
        if (authToken == null || authToken.isEmpty()) {
            res.status(400); // Bad Request
            return gson.toJson(new LogoutResponse(false, "Missing auth token"));
        }

        // Call AuthService to attempt logout
        AuthService authService = new AuthService();
        LogoutResponse response = authService.logout(authToken);

        // Set status based on response
        if (response.isSuccess()) {
            res.status(200); // OK
        } else {
            res.status(401); // Unauthorized (invalid token)
        }

        return gson.toJson(response);
    }
}