package dataaccess;

import model.Game;
import java.util.*;

public class GameDataAccess {
    private final Map<String, Game> games; // In-memory storage for games

    public GameDataAccess() {
        this.games = new HashMap<>();
    }

