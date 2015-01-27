package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Bishop extends ChessPiece {
    /**
     * **********************************************************************
     * Constructors
     * ************************************************************************
     */
    public Bishop() {
        super();
    }

    public Bishop(boolean color, int x, int y, ChessBoard chessBoard) {
        super(color, x, y, chessBoard);
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    @Override
    public boolean isAnyObstacle(int x, int y) {
        return isAnyObstacleDiagonally(x, y);
    }

    @Override
    public boolean isReachable(int x, int y) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return (!(x == getCurrent_row() && y == getCurrent_col())) && isValidLocation(x, y) &&
                isInSameDiagonal(x, y) && !isAnyObstacle(x, y);
    }

    /*
    * need to check only the diagonal elements
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 1; i < ChessBoard.ROW_BOUNDARY; i++) {
            for (int iterator1 = -1; iterator1 < 2; iterator1 = iterator1 + 2) {
                for (int iterator2 = -1; iterator2 < 2; iterator2 = iterator2 + 2) {
                    if (this.isValidMovement(getCurrent_row() + i * iterator1, getCurrent_col() + i * iterator2)) {
                        Position position = new Position(getCurrent_row() + i * iterator1, getCurrent_col() + i * iterator2);
                        positions.add(position);
                    }
                }
            }
        }
        return positions;
    }
}
