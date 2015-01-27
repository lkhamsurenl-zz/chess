package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/15/14.
 * <p/>
 * This piece can do movement depending on its count variable:
 * count %3 ==0 it acts like queen
 * count %3 ==1 it acts like knight
 * count %3 ==2 it can move to any location, but cannot eat anything
 * Whenever there is a movement happened, the count will increase by one
 * <p/>
 * WARNING!!!!!!!!!!!!!!!!
 * NOTE: The only other thing we have to do is update counter in MoveTo() function in ChessPice class
 * WARNING!!!!!!!!!!!!!!!!!!!!!!!
 */
public class Terrorize extends ChessPiece {
    /*
    * private member count figures out which movement to do
    * */
    private int count;

    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public Terrorize() {
        super();
        count = 0;
    }

    public Terrorize(boolean color, int x, int y, int count, ChessBoard chessBoard) {
        super(color, x, y, chessBoard);
        this.count = count;
    }

    /**
     * *********************************************************************************************
     * Abstract methods inherited from the base class. See the Pieces.ChessPiece class for description
     * ********************************************************************************************
     */
    /*
    * @param x -- destination row
    * @param y -- destination column
    *
    * */
    @Override
    public boolean isAnyObstacle(int x, int y) {
        // Queen move
        if (count % 3 == 0) {
            return isAnyObstacleHorizontally(x, y) || isAnyObstacleVertically(x, y) || isAnyObstacleDiagonally(x, y);
        } else if (count % 3 == 1) {
            // Knight movement
            return isAliasPieceInLocation(x, y);
        } else {
            // CAn move anywhere with no piece
            return isAnyPieceInLocation(x, y);
        }
    }

    /*
    * @param x -- destination row
    * @param y -- destination column
    *
    * */
    @Override
    public boolean isReachable(int x, int y) {
        // Check the potential position
        // Figure out if it is valid in a boundary using isValidLocation and valid Movement
        return isValidLocation(x, y) && isLegalTerrorizeMove(x, y) && !isAnyObstacle(x, y);
    }

    @Override
    public ArrayList<Position> possibleMovements() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < ChessBoard.ROW_BOUNDARY; i++) {
            for (int j = 0; j < ChessBoard.COL_BOUNDARY; j++) {
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
    public int getCounter(){
        return this.count;
    }
    public void incCounter(){
        this.count++;
    }
    /*
    * Figures out if the movement is legal piece movement
    * * @param x -- row
    * @param y --column
    * */
    private boolean isLegalTerrorizeMove(int x, int y) {
        if (count % 3 == 0) {
            // Queen move
            return isLegalQueenMove(x, y);
        } else if (count % 3 == 1) {
            //check if Pieces.Knight movement
            return isLegalKnightMove(x, y);
        } else {
            // since it can get anywhere
            // NOTE: we check if there is any piece in the destination with isAnyObstacle function
            return true;
        }
    }

    /*
    * Figures out if the move is a legal Pieces.Queen move
    * */
    private boolean isLegalQueenMove(int x, int y) {
        if (!(x == getCurrent_row() && y == getCurrent_col())) {
            return (isInSameCol(y) || isInSameRow(x) || isInSameDiagonal(x, y));
        }
        return false;
    }

    /*
    * It's valid knight move, if exactly distance 3 and not in same row or column
    * @param x -- row
    * @param y --column
    * */
    private boolean isLegalKnightMove(int x, int y) {
        //cannot be in a same position
        if (!(x == getCurrent_row() && y == getCurrent_col())) {
            int distance = Math.abs(x - getCurrent_row()) + Math.abs(y - getCurrent_col());
            return ((distance == 3) && !(isInSameCol(y) || isInSameRow(x)));
        }
        return false;
    }

}
