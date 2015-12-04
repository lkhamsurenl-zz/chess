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

    public King(boolean color, Position p, ChessBoard chessBoard) {
        super(color, p, chessBoard);
        // Initialize King location
        if (!color) {
            chessBoard.black_king_row = p.getRow();
            chessBoard.black_king_col = p.getCol();
        } else {
            chessBoard.white_king_row = p.getRow();
            chessBoard.white_king_col = p.getCol();
        }
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    @Override
    public boolean isAnyObstacle(Position p) {
        return isAnyObstacleHorizontally(p) || isAnyObstacleVertically(p) || isAnyObstacleDiagonally(p);
    }

    @Override
    public boolean isReachable(Position p) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation
        return isValidLocation(p) && isLegalKingMove(p) && !isAnyObstacle(p);
    }

    /*
    * Figures out if it's a legal king move
    *
    * */
    public boolean isLegalKingMove(Position p) {
        if (!(p.getRow() == getCurrent_row() && p.getCol() == getCurrent_col())) {
            return (p.getRow() != getCurrent_row() || p.getCol() != getCurrent_col()) && (Math.abs(this.getCurrent_row() - p.getRow()) < 2 &&
                    Math.abs(this.getCurrent_col() - p.getCol()) < 2);
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
                Position p = new Position(getCurrent_row() + i, getCurrent_col() + j);
                if ((i != 0 || j != 0) && isValidMovement(p)) {
                    positions.add(p);
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
