package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */

public class King extends ChessPiece {
    public King() {
        super();
    }

    public King(boolean color, int x, int y, ChessBoard chessBoard) {
        super(color, x, y, chessBoard);
        // Initialize King location
        if (!color) {
            chessBoard.black_king_row = x;
            chessBoard.black_king_col = y;
        } else {
            chessBoard.white_king_row = x;
            chessBoard.white_king_col = y;
        }
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    @Override
    public boolean isAnyObstacle(int x, int y) {
        return isAnyObstacleHorizontally(x, y) || isAnyObstacleVertically(x, y) || isAnyObstacleDiagonally(x, y);
    }

    @Override
    public boolean isReachable(int x, int y) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation
        return isValidLocation(x, y) && isLegalKingMove(x, y) && !isAnyObstacle(x, y);
    }

    /*
    * Figures out if it's a legal king move
    *
    * */
    public boolean isLegalKingMove(int x, int y) {
        if (!(x == getCurrent_row() && y == getCurrent_col())) {
            return (x != getCurrent_row() || y != getCurrent_col()) && (Math.abs(this.getCurrent_row() - x) < 2 &&
                    Math.abs(this.getCurrent_col() - y) < 2);
        }
        return false;
    }

    /*
    * A king can move only adjacent cell, so we just have to cover all those
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((i != 0 || j != 0) && isValidMovement(getCurrent_row() + i, getCurrent_col() + j)) {
                    Position position = new Position(getCurrent_row() + i, getCurrent_col() + j);
                    positions.add(position);
                }
            }
        }
        return positions;
    }

    // Figure out if the current piece is in checkmate
    public boolean isInCheck() {
        for (int i = 0; i < chessBoard.ROW_BOUNDARY; i++) {
            for (int j = 0; j < chessBoard.COL_BOUNDARY; j++) {
                ChessPiece piece = chessBoard.ChessBoard[i][j];
                // Check any of the opponent piece can eat king directly
                if (piece != null && piece.getColor() != getColor() && piece.canDirectlyEatKing()) {
                    return true;
                }
            }
        }
        return false;
    }
}
