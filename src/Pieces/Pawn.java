package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/10/14.
 */
public class Pawn extends ChessPiece {

    // Determine if it's a initial state
    private boolean isInInitState;

    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Pawn() {
        super();
    }

    public Pawn(boolean color, int x, int y, ChessBoard chessBoard) {
        super(color, x, y, chessBoard);
        if (color) {
            // then it's white, so row==1
            isInInitState = (x == 1);
        } else {
            // it's black, so row == 6
            isInInitState = (x == 6);
        }
    }
    /***********************************************************************************************
     * Useful functoins
     * ********************************************************************************************/
    public boolean getInitState() {
        return isInInitState;
    }

    public void setInitState(boolean currState) {
        isInInitState = currState;
    }

    /***********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************/

    // NOTE: this method called only when the destination is valid movement for the pawn, pawn is the only piece cannot eat thing front of it
    // So we can safely assume that checking no obstacle diagonally and horizontally are enough, as they are the only possible movements for the pawn

    @Override
    public boolean isAnyObstacle(int x, int y) {
        // For pawn, even there is opponent piece when moving forward, cannot eat it
        if (isMovingForward(x, y) && !isAnyPieceInLocation(x, y)) {
            return false;
        } else if (isJumpTwo(x, y) && !isAnyObstacleVertically(x, y) && !isAnyPieceInLocation(x, y)) {
            return false;
        }
        // then it's trying to eat, so check no obstacle diagonally
        else  if(isTryingEatingOther(x,y)) {
            return isAnyObstacleDiagonally(x, y);
        }
        return true;
    }

    @Override
    public boolean isReachable(int x, int y) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        if (isValidLocation(x, y) && isLegalPawnMove(x, y)) {
            // Check no alias piece in a way, by checking its alias is obstacling it
            if (!isAnyObstacle(x, y)) {
                return true;
            }
        }
        return false;
    }

    /*
    * Pieces.Pawn, we need to consider that it can move at most distance to horizontally and 1 vertically
    * */
    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = -2; i < 3; i++) {
            for (int j = -1; j < 2; j++) {
                // valid movement method checks if there is any obstacle and movement is legal for the piece
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
     * Helper functions specific to the class
     * ********************************************************************************************
     */
    /*
    *  Figures out if it's a legal pawn move
    * */
    public boolean isLegalPawnMove(int x, int y) {
        if (!(x == getCurrent_row() && y == getCurrent_col())) {
            return (isJumpTwo(x, y) || isMovingForward(x, y) || isTryingEatingOther(x, y));
        }
        return false;
    }

    //moving by one
    boolean isMovingForward(int x, int y) {
        if (isInSameCol(y)) {
            int indicator = getColor() ? 1 : -1;
            // if white, then should be moving up
            // else should be moving down
            return indicator * (x - getCurrent_row()) == 1;
        }
        return false;
    }

    // if there is non
    public boolean isJumpTwo(int x, int y) {
        // it is first move and same column, then we know it cannot move back
        if (isInInitState && isInSameCol(y)) {
            int indicator = getColor() ? 1: -1;
            return indicator * ( x - getCurrent_row() ) == 2;
        }
        return false;
    }

    // Trying to eat other piece diagonally, so there has to be a opponent piece on that location
    public boolean isTryingEatingOther(int x, int y) {
        boolean isAnyOpponentPiece = !isAliasPieceInLocation(x, y) && isAnyPieceInLocation(x, y);
        int indicator = getColor() ? 1 : -1;
        return ((x - getCurrent_row()) * indicator == 1) && (Math.abs(y - getCurrent_col()) == 1) && isAnyOpponentPiece;

    }
}
