package Pieces;

import Model.ChessBoard;
import Model.Position;

import java.util.ArrayList;

/**
 * Created by luvsandondov on 9/16/14.
 *  Nightrider can make an unlimited number of knight moves (that is, (1,2) cells) in any direction in a straight line
 *  (like other riders, it cannot change direction partway through its move).
 *  Slight modification: it can only make that "jump, if there is no piece in any 1-knight places it'll visit"
 */
public class NightRider extends ChessPiece{
    // factor on how many knight jump it is making
    private int multiplicity = 1;
    /**
     * *********************************************************************************************
     * Constructors
     * ********************************************************************************************
     */
    public NightRider() {
        super();
    }

    public NightRider(boolean color, int x, int y, ChessBoard chessBoard) {

        super(color, x, y, chessBoard);
        if(isLegalNightRiderMove(x,y)) {
            multiplicity = getMultiplicity(x,y);
        }
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
        multiplicity = getMultiplicity(x,y);
        // steps of how much taking
        int row_step = (x - getCurrent_row())/ multiplicity;
        int col_step = (y - getCurrent_col()) / multiplicity;
        // it's assumed here that movement is valid.
        // we need to check movement validity through checking
        for(int i=1; i<= multiplicity; i++) {
            if(i != multiplicity) {
                if(isAnyPieceInLocation(getCurrent_row() + i * row_step, getCurrent_col() + i*col_step)) {
                    return true;
                }
            }
            else {
                // it's the destination position, so we check if it's the alias
                return isAliasPieceInLocation(x,y);
            }
        }
        return false;
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
        return isValidLocation(x, y) && isLegalNightRiderMove(x, y) && !isAnyObstacle(x, y);
    }

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
    private int getMultiplicity(int x, int y){
        int mul;
        // absolute difference in col and row
        int abs_row_diff = Math.abs(getCurrent_row() - x);
        int abs_col_diff = Math.abs(getCurrent_col() - y);
        if(abs_row_diff < abs_col_diff) {
            mul= abs_col_diff  -  abs_row_diff;
        }
        else {
            mul = abs_row_diff - abs_col_diff;
        }
        return mul;
    }
    /*
    * Figures out if the movement is legal piece movement
    * * @param x -- row
    * @param y --column
    * */
    private boolean isLegalNightRiderMove(int x, int y) {
        if(!(x==getCurrent_row() && y == getCurrent_col())) {
            int abs_row_diff = Math.abs(getCurrent_row() - x);
            int abs_col_diff = Math.abs(getCurrent_col() - y);
            if(abs_row_diff < abs_col_diff) {
                return (abs_col_diff == abs_row_diff *2);
            }
            else {
                return (abs_row_diff == abs_col_diff *2);
            }
        }
        return false;
    }

}
