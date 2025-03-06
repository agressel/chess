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

