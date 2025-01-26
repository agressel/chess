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
        } else if (pieceType == PieceType.BISHOP) {
            return bishopMoves(board, myPosition);

        } else if (pieceType == PieceType.QUEEN) {
            return queenMoves(board, myPosition);

        } else if (pieceType == PieceType.KNIGHT) {
            return knightMoves(board, myPosition);

        } else if (pieceType == PieceType.PAWN) {
            return pawnMoves(board, myPosition);
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

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        //upwards
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
        }
        // downwards
        for (int y = 1; y <= 8; y++) {
            if (row - y >= 1 && row - y <= 8 && col >= 1 && col <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - y, col);
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


        //rightwards
        for (int x = 1; x <= 8; x++) {
            if (row >= 1 && row <= 8 && col + x >= 1 && col + x <= 8) {
                ChessPosition targetPosition = new ChessPosition(row, col + x);
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

        //leftwards
        for (int x = 1; x <= 8; x++) {
            if (row >= 1 && row <= 8 && col - x >= 1 && col - x <= 8) {
                ChessPosition targetPosition = new ChessPosition(row, col - x);
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
    }


    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        //Up-Right
        int xa = 0;
        for (int y = 1; y <= 8; y++) {
            xa += 1;
            if (row + y >= 1 && row + y <= 8 && col + xa >= 1 && col + xa <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + y, col + xa);
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

        //Up-Left
        int xb = 0;
        for (int y = 1; y <= 8; y++) {
            xb += 1;
            if (row + y >= 1 && row + y <= 8 && col - xb >= 1 && col - xb <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + y, col - xb);
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


        //Down-Right
        int xc = 0;
        for (int y = 1; y <= 8; y++) {
            xc += 1;
            if (row - y >= 1 && row - y <= 8 && col + xc >= 1 && col + xc <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - y, col + xc);
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
        // Down-Left
        int xd = 0;
        for (int y = 1; y <= 8; y++) {
            xd += 1;
            if (row - y >= 1 && row - y <= 8 && col - xd >= 1 && col - xd <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - y, col - xd);
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
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        // Rook Functionality:
        //upwards
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
        }
        // downwards
        for (int y = 1; y <= 8; y++) {
            if (row - y >= 1 && row - y <= 8 && col >= 1 && col <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - y, col);
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


        //rightwards
        for (int x = 1; x <= 8; x++) {
            if (row >= 1 && row <= 8 && col + x >= 1 && col + x <= 8) {
                ChessPosition targetPosition = new ChessPosition(row, col + x);
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

        //leftwards
        for (int x = 1; x <= 8; x++) {
            if (row >= 1 && row <= 8 && col - x >= 1 && col - x <= 8) {
                ChessPosition targetPosition = new ChessPosition(row, col - x);
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
        //Bishop Functionality:
        //Up-Right
        int xa = 0;
        for (int y = 1; y <= 8; y++) {
            xa += 1;
            if (row + y >= 1 && row + y <= 8 && col + xa >= 1 && col + xa <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + y, col + xa);
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

        //Up-Left
        int xb = 0;
        for (int y = 1; y <= 8; y++) {
            xb += 1;
            if (row + y >= 1 && row + y <= 8 && col - xb >= 1 && col - xb <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + y, col - xb);
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


        //Down-Right
        int xc = 0;
        for (int y = 1; y <= 8; y++) {
            xc += 1;
            if (row - y >= 1 && row - y <= 8 && col + xc >= 1 && col + xc <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - y, col + xc);
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
        // Down-Left
        int xd = 0;
        for (int y = 1; y <= 8; y++) {
            xd += 1;
            if (row - y >= 1 && row - y <= 8 && col - xd >= 1 && col - xd <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - y, col - xd);
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
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        //Up-Right
        if (row + 2 >= 1 && row + 2 <= 8 && col + 1 >= 1 && col + 1 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row + 2, col + 1);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }

        //Up-Left
        if (row + 2 >= 1 && row + 2 <= 8 && col - 1 >= 1 && col - 1 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row + 2, col - 1);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }
        //Right-Up
        if (row + 1 >= 1 && row + 1 <= 8 && col + 2 >= 1 && col + 2 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row + 1, col + 2);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }
        //Right-Down
        if (row - 1 >= 1 && row - 1 <= 8 && col + 2 >= 1 && col + 2 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row - 1, col + 2);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }
        //Down-Right
        if (row - 2 >= 1 && row - 2 <= 8 && col + 1 >= 1 && col + 1 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row - 2, col + 1);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }
        //Down-Left
        if (row - 2 >= 1 && row - 2 <= 8 && col - 1 >= 1 && col - 1 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row - 2, col - 1);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }
        //Left-Up
        if (row + 1 >= 1 && row + 1 <= 8 && col - 2 >= 1 && col - 2 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row + 1, col - 2);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }
        //Left-Down
        if (row - 1 >= 1 && row - 1 <= 8 && col - 2 >= 1 && col - 2 <= 8) {
            ChessPosition targetPosition = new ChessPosition(row - 1, col - 2);
            ChessPiece targetPiece = board.getPiece(targetPosition);
            if (targetPiece == null || targetPiece.pieceColor != this.pieceColor) {
                moves.add(new ChessMove(myPosition, targetPosition, null));
            }
        }
        return moves;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        //IF WHITE
        if (this.pieceColor == ChessGame.TeamColor.WHITE) {
            //Forward one
            if (row + 1 >= 1 && row + 1 <= 8 && col >= 1 && col <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + 1, col);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece == null) {
                    if (row + 1 == 8) {
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.ROOK));
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
            //forward two
            if (row + 2 >= 1 && row + 2 <= 8 && col >= 1 && col <= 8) {
                if (row == 2) {
                    ChessPosition targetPosition = new ChessPosition(row + 2, col);
                    ChessPosition aheadPosition = new ChessPosition(row + 1, col);
                    ChessPiece targetPiece = board.getPiece(targetPosition);
                    ChessPiece aheadPiece = board.getPiece(aheadPosition);
                    if (targetPiece == null && aheadPiece == null) {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
            //right diagonal
            if (row + 1 >= 1 && row + 1 <= 8 && col + 1 >= 1 && col + 1 <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + 1, col + 1);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece != null && targetPiece.pieceColor != this.pieceColor) {
                    if (row + 1 == 8) {
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.ROOK));
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
            //left diagonal
            if (row + 1 >= 1 && row + 1 <= 8 && col - 1 >= 1 && col - 1 <= 8) {
                ChessPosition targetPosition = new ChessPosition(row + 1, col - 1);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece != null && targetPiece.pieceColor != this.pieceColor) {
                    if (row + 1 == 8) {
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.ROOK));
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
        } else {
            //IF BLACK
            //Forward
            if (row - 1 >= 1 && row - 1 <= 8 && col >= 1 && col <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - 1, col);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece == null) {
                    if (row - 1 == 1) {
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.ROOK));
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
            //Forward 2
            if (row - 2 >= 1 && row - 2 <= 8 && col >= 1 && col <= 8) {
                if (row == 7) {
                    ChessPosition targetPosition = new ChessPosition(row - 2, col);
                    ChessPosition aheadPosition = new ChessPosition(row - 1, col);
                    ChessPiece targetPiece = board.getPiece(targetPosition);
                    ChessPiece aheadPiece = board.getPiece(aheadPosition);
                    if (targetPiece == null && aheadPiece == null) {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }

            //right diagonal
            if (row - 1 >= 1 && row - 1 <= 8 && col + 1 >= 1 && col + 1 <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - 1, col + 1);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece != null && targetPiece.pieceColor != this.pieceColor) {
                    if (row - 1 == 1) {
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.ROOK));
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
            //left diagonal
            if (row - 1 >= 1 && row - 1 <= 8 && col - 1 >= 1 && col - 1 <= 8) {
                ChessPosition targetPosition = new ChessPosition(row - 1, col - 1);
                ChessPiece targetPiece = board.getPiece(targetPosition);
                if (targetPiece != null && targetPiece.pieceColor != this.pieceColor) {
                    if (row - 1 == 1) {
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, targetPosition, ChessPiece.PieceType.ROOK));
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
            }
        }
        return moves;
    }
};











