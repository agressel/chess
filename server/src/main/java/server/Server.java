package server;

import spark.*;
import server.handlers.RegisterHandler;


public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.post("/register", (req, res) -> {
            // Create a new RegisterHandler instance and call handleRequest
            return (new RegisterHandler()).handleRequest(req, res);
        });

        // Handle 404 errors (resource not found)
        Spark.notFound((req, res) -> {
            return "Resource not found";
        });

        // Handle internal server errors (500)
        Spark.exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.body("Something went wrong: " + e.getMessage());
        });

        // Initialize the server
        Spark.init();
        Spark.awaitInitialization();

        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}