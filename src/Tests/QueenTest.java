package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import Pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QueenTest {

    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, 0, 3, chessBoard);
    King black_king = new King(false, 2, 4, chessBoard);

    Queen white_queen = new Queen(true, 4, 2, chessBoard);
    Queen black_queen = new Queen(false, 7, 4, chessBoard);


    /*
    * I have to set the locations to be initial after each tests
    * */
    @Before
    public void afterEachTest() {
        white_king.setPosition(0, 3);
        black_king.setPosition(2, 4);

        white_queen.setPosition(4, 2);
        black_queen.setPosition(7, 4);

    }

    /*
    * destination is in boundary and no obstacle in the way
    * */
    @Test
    public void testIsReachable() throws Exception {
        white_queen.setPosition(0, 2);
        assertEquals(white_queen.isReachable(0, 3), false);
        // but can eat the king
        assertEquals(white_queen.isReachable(2, 4), true);
        // cannot step over it
        assertEquals(white_queen.isReachable(3, 5), false);
    }

    @Test
    public void testIsValidMovement() throws Exception {
        white_queen.setPosition(4, 3);
        black_queen.setPosition(7, 3);
        // now the white_king is in danger, if the queen moves horizontally
        assertEquals(white_queen.isValidMovement(4, 2), false);
        // but it can move vertically
        assertEquals(white_queen.isValidMovement(1, 3), true);
    }

    @Test
    public void testMoveTo() throws Exception {
        white_queen.moveTo(4, 3);
        assertNotNull(chessBoard.ChessBoard[4][3]);
        assertNull(chessBoard.ChessBoard[4][2]);

    }

    @Test
    public void testCanDirectlyEatKing() throws Exception {
        assertEquals(white_queen.canDirectlyEatKing(), true);
        black_queen.setPosition(3, 3);
        assertEquals(white_queen.canDirectlyEatKing(), false);
    }

    /*
    * Enumerate all possible valid movements
    * */
    @Test
    public void testPossibleMovements() {
        ArrayList<Position> positions = white_queen.possibleMovements();
        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }
    }
}