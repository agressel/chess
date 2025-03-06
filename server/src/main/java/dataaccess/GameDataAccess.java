package dataaccess;

import model.Game;
import java.util.*;

public class GameDataAccess {
    private final Map<String, Game> games; // In-memory storage for games

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

