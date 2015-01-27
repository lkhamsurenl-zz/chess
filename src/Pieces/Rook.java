package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Rook extends ChessPiece {

    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Rook() {
        super();
    }

    public Rook(boolean color, int x, int y, ChessBoard chessBoard) {
        super(color, x, y, chessBoard);
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */

    @Override
    public boolean isAnyObstacle(int x, int y) {
        return isAnyObstacleHorizontally(x, y) || isAnyObstacleVertically(x, y);
    }

    @Override
    public boolean isReachable(int x, int y) {
        boolean value = false;
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return isValidLocation(x, y) && isLegalRookMove(x, y) && !isAnyObstacle(x, y);
    }

    /*
    * For rook, we check horizontal and vertical moves separately
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < chessBoard.ROW_BOUNDARY; i++) {
            if (isValidMovement(i, getCurrent_col()) && i != getCurrent_row()) {
                Position position = new Position(i, getCurrent_col());
                positions.add(position);
            }

        }
        for (int j = 0; j < chessBoard.COL_BOUNDARY; j++) {
            if (isValidMovement(getCurrent_row(), j) && j != getCurrent_col()) {
                Position position = new Position(getCurrent_row(), j);
                positions.add(position);
            }
        }
        return positions;
    }

    /**
     * *********************************************************************************************
     * Helper functions
     * ********************************************************************************************
     */

    /*
    * Figures out if the move is a legal rook move by checking if in same row or column
    * */
    public boolean isLegalRookMove(int x, int y) {
        if (!(x == getCurrent_row() && y == getCurrent_col())) {
            return (isInSameCol(y) || isInSameRow(x));
        }
        return false;
    }
}
