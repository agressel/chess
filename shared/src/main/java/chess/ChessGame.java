package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor TeamTurn;
    private ChessBoard board;
    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
        this.TeamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return TeamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.TeamTurn = team;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return TeamTurn == chessGame.TeamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TeamTurn, board);
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece startPiece = board.getPiece(startPosition);
        if (startPiece == null){
            return Collections.emptyList();
        }
        Collection<ChessMove> allMoves = startPiece.pieceMoves(board, startPosition);
        Collection<ChessMove> checkedMoves = new ArrayList<>();
        //iterate all moves
        for (ChessMove move : allMoves){
            ChessPosition currentPlace = move.getStartPosition();
            ChessPiece currentPiece = board.getPiece(currentPlace);
            ChessPosition newPlace = move.getEndPosition();
            ChessPiece newPiece = board.getPiece(newPlace);

            //alter board
            board.addPiece(newPlace, currentPiece);
            board.addPiece(currentPlace, null);
            if (isInCheck(startPiece.getTeamColor())) {
                //revert
                board.addPiece(currentPlace, currentPiece);
                board.addPiece(newPlace, newPiece);
            } else {
                checkedMoves.add(move);
                // Revert changes
                board.addPiece(currentPlace, currentPiece);
                board.addPiece(newPlace, newPiece);
            }
        }

        return checkedMoves;
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition starter = move.getStartPosition();
        ChessPosition ender = move.getEndPosition();
        Collection<ChessMove> validMoves = validMoves(starter);

        if (!validMoves.contains(move)) {
            throw new InvalidMoveException("The move is invalid.");
        }
        ChessPiece currentPiece = board.getPiece(starter);
        ChessPiece newPiece = board.getPiece(ender);
        //check if player turn
        if (currentPiece.getTeamColor() == TeamTurn) {
            // It correct team
        } else {
            throw new InvalidMoveException("It's not your turn.");
        }
        //promotions
        if (currentPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
            if ((currentPiece.getTeamColor() == TeamColor.WHITE && ender.getRow() == 8) ||
                    (currentPiece.getTeamColor() == TeamColor.BLACK && ender.getRow() == 1)) {

                ChessPiece promotedPiece = new ChessPiece(currentPiece.getTeamColor(), move.getPromotionPiece());

                board.addPiece(starter, null);
                board.addPiece(ender, promotedPiece);


                setTeamTurn(currentPiece.getTeamColor() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
                return;
            }
        }
        //
        //alter board
        board.addPiece(starter, null);
        board.addPiece(ender, currentPiece);
        //change turn
        if (TeamTurn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn((TeamColor.WHITE));
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //find king position
        ChessPosition kingPosition = null;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPiece piece = board.getPiece(new ChessPosition(y + 1, x + 1));
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    //if correct color
                    if (piece.getTeamColor() == teamColor) {
                        kingPosition = new ChessPosition(y + 1, x + 1);
                    }
                }
                if (kingPosition != null) {
                    break;
                }
            }
            if (kingPosition != null) {
                break;
            }
        }
        //iterate over all enemy move
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPosition piecePosition = new ChessPosition(y + 1, x + 1);
                ChessPiece piece = board.getPiece(piecePosition);
                if (piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> enemyMoves = piece.pieceMoves(board, piecePosition);
                    for (ChessMove moves : enemyMoves) {
                        if (moves.getEndPosition().equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        } else {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    ChessPosition piecePosition = new ChessPosition(y + 1, x + 1);
                    ChessPiece piece = board.getPiece(piecePosition);
                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> friendlyMoves = piece.pieceMoves(board, piecePosition);
                        for (ChessMove moves : friendlyMoves) {
                            ChessPosition startPosition = moves.getStartPosition();
                            ChessPosition endPosition = moves.getEndPosition();

                            ChessPiece currentPiece = board.getPiece(startPosition);
                            ChessPiece newPiece = board.getPiece(endPosition);

                            //alter board
                            board.addPiece(startPosition, null);
                            board.addPiece(endPosition, currentPiece);
                            //areygrwujegfiu
                            if (!isInCheck(teamColor)) {
                                board.addPiece(startPosition, currentPiece);
                                board.addPiece(endPosition, newPiece);
                                return false;
                            }

                            // Revert
                            board.addPiece(startPosition, currentPiece);
                            board.addPiece(endPosition, newPiece);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    ChessPosition piecePosition = new ChessPosition(y + 1, x + 1);
                    ChessPiece piece = board.getPiece(piecePosition);
                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> friendlyMoves = validMoves(piecePosition);
                        if (!friendlyMoves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

}
