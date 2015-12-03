package Pieces;

import Model.ChessBoard;
import Model.Position;
import View.View;

import java.util.ArrayList;

/**
 * The abstract class representing a chess piece, shared across all the instances of the chess pieces.
 * Created by luvsandondov on 9/8/14.
 */
public abstract class ChessPiece {
    /**
     * *************************************************************************
     * Class private variables representing pieces
     * *****************************************************************************
     */
    // 1 = white, 0=black
    private boolean color;
    // Current position of the piece.
    // TODO(luvsandondov): Use Model.Position rather than row, col.
    private int current_row;
    private int current_col;
    // chessBoard the piece is on.
    // TODO(luvsandondov): Move ChessBoard out of this class.
    ChessBoard chessBoard;

    /**
     * *************************************************************************
     * Constructors
     * *****************************************************************************
     */
    public ChessPiece() {
        this.color = true;
        current_col = -1;
        current_row = -1;
        // We also need Model.ChessBoard instance in which one it is referring.
        chessBoard = null;
    }

    // Set the piece
    public ChessPiece(boolean color, int x, int y, ChessBoard chessBoard) {
        this.color = color;
        current_row = x;
        current_col = y;
        if (isValidLocation(x, y)) {
            // Initialize the piece position in the board.
            chessBoard.ChessBoard[x][y] = this;
        }
    }

    /**
     * *************************************************************************
     * Abstract methods
     * *****************************************************************************
     */
    // Figure out if there is any obstacle to reach the destination.
    public abstract boolean isAnyObstacle(int x, int y);

    // Check if the given location is reachable by the piece and no piece in the way.
    abstract boolean isReachable(int x, int y);

    // Get all possible movements for the piece.
    public abstract ArrayList<Position> possibleMovements();

    /**
     * *************************************************************************
     * Public functions
     * *****************************************************************************
     */
    public boolean getColor() {
        return this.color;
    }

    public int getCurrent_row() {
        return current_row;
    }

    public int getCurrent_col() {
        return current_col;
    }

    public ChessBoard getChessBoard() {return chessBoard; }

    /*
    * Set the new position of the piece, useful for debugging.
    * */
    public void setPosition(int x, int y) {
        // Remove the piece from previous location.
        chessBoard.ChessBoard[current_row][current_col] = null;
        // Update current location.
        current_row = x;
        current_col = y;
        // Update the position of the King, if the current piece is a king.
        if (this instanceof King) {
            if (getColor()) {
                chessBoard.white_king_row = current_row;
                chessBoard.white_king_col = current_col;
            } else {
                chessBoard.black_king_row = current_row;
                chessBoard.black_king_col = current_col;
            }
        }
        if(isValidLocation(x,y)) {
            // Update the board again withe piece new location.
            chessBoard.ChessBoard[current_row][current_col] = this;
        }
    }


    // Check if the given location is not out of boundary.
    public boolean isValidLocation(int x, int y) {
        return 0 <= x && x < chessBoard.ROW_BOUNDARY && 0 <= y && y < chessBoard.COL_BOUNDARY;
    }

    // Determine if given movement is valid by no obstacles, and wouldn't give up the king.
    public boolean isValidMovement(int x, int y) {
        boolean value = false;
        if (isReachable(x, y)) {
            int row = this.getCurrent_row();
            int col = this.getCurrent_col();
            ChessPiece temp_removed_piece = tryPosition(x, y);
            // Check if king is not threatened by any piece from other side, otherwise the movement is not valid.
            King alikeKing = getColor() ? (King) chessBoard.ChessBoard[chessBoard.white_king_row][chessBoard.white_king_col] :
                    (King) chessBoard.ChessBoard[chessBoard.black_king_row][chessBoard.black_king_col];
            if (!alikeKing.isInCheck()) {
                value = true;
            }
            revertPosition(row, col, temp_removed_piece);
        }
        return value;
    }

    // Move to the specific location, by capturing piece if there is one and setPosition.
    public void moveTo(int x, int y) {
        if (isValidMovement(x, y)) {
            ChessPiece removedPiece = null;
            // Update the position.
            if (chessBoard.ChessBoard[x][y] != null) {
                 removedPiece = chessBoard.ChessBoard[x][y];
            }
            // Add it to the removed piece collector.
            View.queryRemovedPiece(chessBoard, this, removedPiece, getCurrent_row(), getCurrent_col());

            setPosition(x, y);
            // Pawn is no longer in a initial state.
            if(this instanceof Pawn) {
                Pawn pawn = (Pawn) this;
                if(pawn.getInitState()) pawn.setInitState(false);
            }
            // Terrorize's counter will increase as it moves.
            else if(this instanceof Terrorize) {
                ((Terrorize) this).incCounter();
            }
        } else {
            // Then moveTo cannot be done
            System.out.println("Cannot Move To the Specified location (" + x + ", " + y + ")");
            System.exit(-1);
        }
    }

    // Check if particular piece can directly eat opponent's king.
    public boolean canDirectlyEatKing() {
        // Get the opposite Pieces.King's location
        int row = this.getColor() ? chessBoard.black_king_row : chessBoard.white_king_row;
        int col = this.getColor() ? chessBoard.black_king_col : chessBoard.white_king_col;
        if (this instanceof Pawn) {
            // Then we  have to check if it's actually can eat king different way than other pieces.
            if (((Pawn) this).isTryingEatingOther(row, col)) {
                return true;
            } else
                return false;
        } else {
            // If it is reachable, then it is eatable.
            return isReachable(row, col);
        }
    }

    /**
     * *********************************************************************************************************
     * Helper functions
     * **********************************************************************************************************
     */
    // Figures out if there is any piece in certain location, assume x,y is valid Location.
    public boolean isAliasPieceInLocation(int x, int y) {
        ChessPiece piece = chessBoard.ChessBoard[x][y];
        return (piece != null && piece.color == this.getColor());
    }

    public boolean isAnyPieceInLocation(int x, int y) {
        ChessPiece piece = chessBoard.ChessBoard[x][y];
        boolean value = (piece != null);
        return value;
    }

    public boolean isInSameCol(int y) {
        return (this.getCurrent_col() == y);
    }

    public boolean isInSameRow(int x) {
        return (this.getCurrent_row() == x);
    }

    public boolean isInSameDiagonal(int x, int y) {
        return Math.abs(this.getCurrent_row() - x) == Math.abs(this.getCurrent_col() - y);
    }

    /*
    * Figures out if there is any obstacle horizontally from current to destination.
    * @param col of destination
    */
    public boolean isAnyObstacleHorizontally(int x, int y) {
        if (isInSameRow(x)) {
            // Assume it is horizontally reachable.
            int curr_col = this.getCurrent_col();
            int curr_row = this.getCurrent_row();
            // Set the iterator to be whichever lower.
            int iterator = curr_col < y ? 1 : -1;
            for (int j = 1; j <= Math.abs(y - curr_col); j++) {
                if (j == Math.abs(y - curr_col)) {
                    // Check if there the destination piece has same color there.
                    if (isAliasPieceInLocation(curr_row, curr_col + j * iterator)) {
                        return true;
                    }
                } else {
                    // It is not the last element, so we check if it's piece or not.
                    if (isAnyPieceInLocation(curr_row, curr_col + j * iterator)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Figures out if there is any obstacle diagonally from current to destination.
    public boolean isAnyObstacleVertically(int x, int y) {
        if (isInSameCol(y)) {
            // Assume it is vertically reachable.
            int curr_col = this.getCurrent_col();
            int curr_row = this.getCurrent_row();
            // Set the iterator to be whichever lower.
            int iterator = curr_row < x ? 1 : -1;
            for (int i = 1; i <= Math.abs(x - curr_row); i++) {
                if (i == Math.abs(x - curr_row)) {
                    // Check if there the destination piece has same color there.
                    if (isAliasPieceInLocation(curr_row + i * iterator, curr_col)) {
                        return true;
                    }
                } else {
                    // It is not the last element, so we check if it's piece or not.
                    if (isAnyPieceInLocation(curr_row + i * iterator, curr_col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Figures out if there is any obstacle diagonally from current to destination to reach the given location.
    public boolean isAnyObstacleDiagonally(int x, int y) {
        if (isInSameDiagonal(x, y)) {
            int curr_col = this.getCurrent_col();
            int curr_row = this.getCurrent_row();
            // Set the iterator to be whichever lower.
            int iterator1 = curr_row < x ? 1 : -1;
            int iterator2 = curr_col < y ? 1 : -1;
            for (int i = 1; i <= Math.abs(x - curr_row); i++) {
                if ((i == Math.abs(x - curr_row)) && (i == Math.abs(y - curr_col))) {
                    // Check if there the destination piece has same color there.
                    if (isAliasPieceInLocation(curr_row + i * iterator1, curr_col + i * iterator2)) {
                        return true;
                    }
                } else {
                    // It is not the last element, so we check if it's piece or not.
                    if (isAnyPieceInLocation(curr_row + i * iterator1, curr_col + i * iterator2)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /*
    * Try this new position temporarily, so do not delete the piece.
    * */
    private ChessPiece tryPosition(int row, int col) {
        ChessPiece temp_removed_piece = null;
        if(chessBoard.ChessBoard[row][col] != null) {
            temp_removed_piece = chessBoard.ChessBoard[row][col];
        }
        this.setPosition(row, col);
        return temp_removed_piece;
    }
    /*
    * Put back the piece into its row, col position and put any piece previously been in the tried position.
    * */
    private void revertPosition(int row, int col, ChessPiece temp_removed_piece) {
        int tried_row = this.getCurrent_row();
        int tried_col = this.getCurrent_col();
        this.setPosition(row, col);
        if(temp_removed_piece!=null) {
            // Put this piece back in its location.
            temp_removed_piece.setPosition(tried_row, tried_col);
        }
    }
}