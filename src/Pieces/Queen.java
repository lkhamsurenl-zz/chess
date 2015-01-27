package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Queen extends ChessPiece {
    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Queen() {
        super();
    }

    public Queen(boolean color, int x, int y, ChessBoard chessBoard) {
        super(color, x, y, chessBoard);
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
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return isValidLocation(x, y) && isLegalQueenMove(x, y) && !isAnyObstacle(x, y);
    }

    /*
    * Pieces.Queen has much bigger, so we just check for all the cells
    * NOTE: not the optimal way to do this
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < chessBoard.ROW_BOUNDARY; i++) {
            for (int j = 0; j < chessBoard.COL_BOUNDARY; j++) {
                if (isValidMovement(i, j)) {
                    Position position = new Position(i, j);
                    positions.add(position);
                }
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
* Figures out if the move is a legal Pieces.Queen move
* */
    private boolean isLegalQueenMove(int x, int y) {
        if (!(x == getCurrent_row() && y == getCurrent_col())) {
            return (isInSameCol(y) || isInSameRow(x) || isInSameDiagonal(x, y));
        }
        return false;
    }
}
