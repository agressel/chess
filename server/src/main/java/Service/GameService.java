package Service;

import model.Game;
import model.response.GameResponse;
import model.request.GameRequest;
import dataaccess.GameDataAccess;
import dataaccess.PlayerDataAccess;
import dataaccess.AuthDataAccess;
import java.util.List; // Import List

public class GameService {

    private GameDataAccess gameDataAccess;
    private PlayerDataAccess playerDataAccess;
    private AuthDataAccess authDataAccess;

    public GameService() {
        this.gameDataAccess = new GameDataAccess();
        this.playerDataAccess = new PlayerDataAccess();
        this.authDataAccess = new AuthDataAccess();
    }

    public GameResponse createGame(GameRequest gameRequest) {
        if (gameRequest == null || gameRequest.getCreatorId() == null) {
            return new GameResponse(false, "Invalid game creation request.");
        }

        Game game = new Game(gameRequest.getCreatorId(), gameRequest.getGameType());
        boolean isCreated = gameDataAccess.saveGame(game);
        if (isCreated) {
            return new GameResponse(true, "Game created successfully.", game.getGameId());
        } else {
            return new GameResponse(false, "Failed to create the game.");
        }
    }

    public GameResponse joinGame(String authToken, String playerColor, String gameId) {
        if (gameId == null || playerColor == null) {
            return new GameResponse(false, "Game ID or player color is missing.");
        }

        Game game = gameDataAccess.getGameById(gameId);
        if (game == null) {
            return new GameResponse(false, "Game not found.");
        }

        if (game.isFull()) {
            return new GameResponse(false, "Game is already full.");
        }

        boolean playerAdded = gameDataAccess.addPlayerToGame(gameId, authToken, playerColor);
        if (playerAdded) {
            return new GameResponse(true, "Player joined the game successfully.");
        } else {
            return new GameResponse(false, "Failed to join the game.");
        }
    }

    public GameResponse getGameState(String gameId) {
        if (gameId == null) {
            return new GameResponse(false, "Game ID is missing.");
        }

        Game game = gameDataAccess.getGameById(gameId);
        if (game == null) {
            return new GameResponse(false, "Game not found.");
        }

        return new GameResponse(true, "Game found.", game.getState());
    }

    public GameResponse updateGameState(String gameId, String newState) {
        if (gameId == null || newState == null) {
            return new GameResponse(false, "Invalid game ID or state.");
        }

        boolean isUpdated = gameDataAccess.updateGameState(gameId, newState);
        if (isUpdated) {
            return new GameResponse(true, "Game state updated successfully.");
        } else {
            return new GameResponse(false, "Failed to update the game state.");
        }
    }

    public GameResponse listGames(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return new GameResponse(false, "Invalid authentication token.");
        }

        boolean isValid = authDataAccess.validateAuth(authToken);
        if (!isValid) {
            return new GameResponse(false, "Authentication failed.");
        }

        List<Game> gameList = gameDataAccess.getAllGames();
        if (gameList == null || gameList.isEmpty()) {
            return new GameResponse(false, "No games available.");
        }

        return new GameResponse(true, "Games listed successfully.", gameList);
    }
}