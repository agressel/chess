import chess.*;
import spark.Service;


public class Main {
    public static void main(String[] args) {
        Service server = Service.ignite().port(8080);

        // Serve static files (index.html, etc.) from the resources/web folder
        server.staticFileLocation("/web");

        // Root endpoint to check if server is working
        server.get("/", (req, res) -> {
            res.redirect("/index.html"); // Redirect to the homepage
            return null;
        });

        // Example: ChessPiece output
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        server.awaitInitialization();
    }
}
