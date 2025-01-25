package chess;

import java.util.Collection;
import java.util.Objects;
import java.util.ArrayList;


/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }

    /**
     * The various different chess piece options
     */

    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */

    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;

    }

    /**
     * @return which type of chess piece this piece is
     */

    public PieceType getPieceType() {
        return pieceType;
    }


    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (pieceType == PieceType.KING) {
            return kingMoves(board, myPosition);

        } else if (pieceType == PieceType.ROOK) {
            return rookMoves(board, myPosition);
        /*
        } else if (pieceType == PieceType.QUEEN) {
            return queenMoves(board, myPosition);
        } else if (pieceType == PieceType.BISHOP) {
            return bishopMoves(board, myPosition);
        } else if (pieceType == PieceType.KNIGHT) {
            return knightMoves(board, myPosition);
        } else if (pieceType == PieceType.PAWN) {
            return pawnMoves(board, myPosition);
        }

         */
        } else {
            return null;
        }
    }


    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (row + y >= 1 && row + y <= 8 && col + x >= 1 && col + x <= 8) {
                    ChessPosition targetPosition = new ChessPosition(row + y, col + x);
                    ChessPiece targetPiece = board.getPiece(targetPosition);
                    //bounds
                    if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }

            }

        }
        return moves;
    }

    public Collection<ChessMove> rookMoves (ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        for (int y = 1; y <= 8; y++) {
            if (row + y >= 1 && row + y <= 8 && col >= 1 && col <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + y, col);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece == null) {
                    moves.add(new ChessMove(myPosition, targetPosition, null));
                }
                if (targetPiece != null) {
                    if (targetPiece.pieceColor == this.pieceColor) {
                        break;
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                        break;
                    }
                }
            }

            for (int x = 1; x <= 8; x++) {
                if (row >= 1 && row <= 8 && col + x >= 1 && col + x <= 8) {
                    ChessPosition targetPosition = new ChessPosition(row + y, col + x);
                    ChessPiece targetPiece = board.getPiece(targetPosition);
                    if (targetPiece == null) {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                    if (targetPiece != null) {
                        if (targetPiece.pieceColor == this.pieceColor) {
                            break;
                        } else {
                            moves.add(new ChessMove(myPosition, targetPosition, null));
                            break;
                        }
                    }
                }
            }

        return moves;

    };


     /*
    public Collection<ChessMove> bishopMoves (ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        int x = 0
        for (int y = 1, y <= 8, y++);
            x += 1;
            if (row + y >= 1 && row + y <= 8 && col + x >= 1 && col + x <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + y, col + x);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece != null) {
                    if (targetPiece != this.pieceColor) {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                    if (targetPiece != this.pieceColor) {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
        int x = 8
        for (int y = -8, y <= 8, y++){
            x -= 1;
            if (row + y >= 1 && row + y <= 8 && col + x >= 1 && col + x <= 8) {
            ChessPosition targetPosition = new ChessPosition(row + y, col + x);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece != null){
                if (targetPiece != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, targetPosition, null));
                }
                if (targetPiece != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, targetPosition, null));
                }
    }


}




