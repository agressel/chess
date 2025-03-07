package dataaccess;

import model.Game;
import java.util.*;

public class GameDataAccess {
    private final Map<String, Game> games;

    public GameDataAccess() {
        this.games = new HashMap<>();
    }

    public boolean saveGame(Game game) {
        if (game == null || game.getGameId() == null) {
            return false;
        }
        games.put(game.getGameId(), game);
        return true;
    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public boolean updateGame(String gameId, String newState) {
        Game game = games.get(gameId);
        if (game != null) {
            game.setState(newState);
            return true;
        }
        return false;
    }

    public List<Game> listGames() {
        return new ArrayList<>(games.values());
    }

    public boolean addPlayer(String gameId, String authToken, String playerColor) {
        Game game = games.get(gameId);
        if (game == null) {
            return false;
        }
        return game.addPlayer(authToken, playerColor);
    }
}