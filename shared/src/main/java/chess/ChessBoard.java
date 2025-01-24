package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
        resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (position.getRow() < 1 || position.getRow() > 8 || position.getColumn() < 1 || position.getColumn() > 8) {
            throw new IllegalArgumentException("Position is out of bounds: " + position);
        }

        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;

        board[row][col]=piece;
    }
    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (position.getRow() < 1 || position.getRow() > 8 || position.getColumn() < 1 || position.getColumn() > 8) {
            throw new IllegalArgumentException("Position is out of bounds: " + position);
        }

    int row = position.getRow() - 1;
    int col = position.getColumn() - 1;

    return board[row][col];
}
    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = null;
            }
        }
        for (int col = 0; col < 8; col++) {
            board[1][col] = new ChessPiece(PieceType.PAWN, "WHITE");
        }
        for (int col = 0; col < 8; col++) {
            board[6][col] = new ChessPiece(PieceType.PAWN, "BLACK");
        }
        board[0][0] = new ChessPiece(PieceType.ROOK, "WHITE");
        board[0][1] = new ChessPiece(PieceType.KNIGHT, "WHITE");
        board[0][2] = new ChessPiece(PieceType.BISHOP, "WHITE");
        board[0][3] = new ChessPiece(PieceType.QUEEN, "WHITE");
        board[0][4] = new ChessPiece(PieceType.KING, "WHITE");
        board[0][5] = new ChessPiece(PieceType.BISHOP, "WHITE");
        board[0][6] = new ChessPiece(PieceType.KNIGHT, "WHITE");
        board[0][7] = new ChessPiece(PieceType.ROOK, "WHITE");

        board[7][0] = new ChessPiece(PieceType.ROOK, "BLACK");
        board[7][1] = new ChessPiece(PieceType.KNIGHT, "BLACK");
        board[7][2] = new ChessPiece(PieceType.BISHOP, "BLACK");
        board[7][3] = new ChessPiece(PieceType.QUEEN, "BLACK");
        board[7][4] = new ChessPiece(PieceType.KING, "BLACK");
        board[7][5] = new ChessPiece(PieceType.BISHOP, "BLACK");
        board[7][6] = new ChessPiece(PieceType.KNIGHT, "BLACK");
        board[7][7] = new ChessPiece(PieceType.ROOK, "BLACK");

}

