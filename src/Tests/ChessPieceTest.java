package Tests;

import Model.ChessBoard;
import Pieces.King;
import Pieces.Pawn;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ChessPieceTest extends TestCase {

    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, 0, 3, chessBoard);
    King black_king = new King(false, 2, 4, chessBoard);

    Pawn white_pawn = new Pawn(true, 1, 4, chessBoard);
    Pawn black_pawn = new Pawn(false, 1, 3, chessBoard);

    /*
    * I have to set the locations to be initial after each tests
    * */
    @Before
    public void afterEachTest() {
        white_king.setPosition(0, 3);
        black_king.setPosition(2, 4);

        white_pawn.setPosition(1, 4);
        black_pawn.setPosition(1, 3);

    }
    /*
    * check the boundaries. It only needed to be checked once.
    * */
    @Test
    public void testIsValidLocation() throws Exception {
        assertEquals(white_king.isValidLocation(1, 3), true);
        assertEquals(white_king.isValidLocation(-1, 4), false);
        assertEquals(white_king.isValidLocation(8, 3), false);
    }

    @Test
    public void testIsInSameCol() throws Exception {
        assertEquals(white_king.isInSameCol(3), true);
        assertEquals(white_king.isInSameCol(2), false);
    }

    @Test
    public void testIsInSameRow() throws Exception {
        assertEquals(white_king.isInSameRow(0), true);
        assertEquals(white_king.isInSameRow(4), false);
    }

    @Test
    public void testIsInSameDiagonal() throws Exception {
        assertEquals(white_king.isInSameDiagonal(3, 6), true);
        assertEquals(white_king.isInSameDiagonal(3, 4), false);
    }

    @Test
    public void testIsAnyObstacleHorizontally() throws Exception {
        black_pawn.setPosition(0, 4);
        // there is a black pawn in location, so remove it
        assertEquals(white_king.isAnyObstacleHorizontally(0, 4), false);
        // cannot step over black pawn
        assertEquals(white_king.isAnyObstacleHorizontally(0, 5), true);
        white_pawn.setPosition(0, 4);
        //cannot eat white pawn
        assertEquals(white_king.isAnyObstacleHorizontally(0, 4), true);
        assertEquals(white_king.isAnyObstacleHorizontally(0, 2), false);

    }

    @Test
    public void testIsAnyObstacleVertically() throws Exception {
        black_pawn.setPosition(1, 3);
        // there is a black pawn in location, so remove it
        assertEquals(white_king.isAnyObstacleVertically(1, 3), false);
        // cannot step over black pawn
        assertEquals(white_king.isAnyObstacleVertically(2, 3), true);
        white_pawn.setPosition(1, 3);
        //cannot eat white pawn
        assertEquals(white_king.isAnyObstacleVertically(1, 3), true);
    }

    @Test
    public void testIsAnyObstacleDiagonally() throws Exception {
        black_pawn.setPosition(1, 2);
        assertEquals(white_king.isAnyObstacleDiagonally(1, 2), false);
        assertEquals(white_king.isAnyObstacleDiagonally(2, 1), true);
        //System.out.println(white_pawn.getCurrent_row() + " , " + white_pawn.getCurrent_col());
        //System.out.println(white_king.getCurrent_row() + " , " + white_king.getCurrent_col());
    }

}