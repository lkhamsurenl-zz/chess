package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Knight extends ChessPiece {
    public Knight() {
        super();
    }

    public Knight(boolean color, int x, int y, ChessBoard chessBoard) {
        super(color, x, y, chessBoard);
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    /*
    * Only obstacle is when there is alias piece in the destination
    * NOTE: This method will be only called when the movement is valid
    * */
    @Override
    public boolean isAnyObstacle(int x, int y) {
        return isAliasPieceInLocation(x, y);
    }

    @Override
    public boolean isReachable(int x, int y) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement for particular piece
        return isValidLocation(x, y) && isLegalKnightMove(x, y) && !isAnyObstacle(x, y);
    }

    /*
    * Pieces.Knight can move distance at most 3, so check for all possibilities
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                //we need to check if the movement can be made without losing the king
                if (isValidMovement(getCurrent_row() + i, getCurrent_col() + j)) {
                    Position position = new Position(getCurrent_row() + i, getCurrent_col() + j);
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
    * It's valid knight move, if exactly distance 3 and not in same row or column
    * */
    public boolean isLegalKnightMove(int x, int y) {
        //cannot be in a same position
        if (!(x == getCurrent_row() && y == getCurrent_col())) {
            int distance = Math.abs(x - getCurrent_row()) + Math.abs(y - getCurrent_col());
            return ((distance == 3) && !(isInSameCol(y) || isInSameRow(x)));
        }
        return false;
    }

}
