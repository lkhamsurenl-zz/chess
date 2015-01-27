package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import Pieces.NightRider;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NightRiderTest {
    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, 0, 3, chessBoard);
    King black_king = new King(false, 2, 4, chessBoard);

    NightRider black_nightRider = new NightRider(false, 7, 0, chessBoard);
    NightRider white_nightRider = new NightRider(true, 0, 0, chessBoard);
    @Before
    public void beforeAnyTest() {
        white_king.setPosition(0,3);
        black_king.setPosition(2,4);

        black_nightRider.setPosition(7,0);
        white_nightRider.setPosition(0,0);
    }

    @Test
    public void testIsAnyObstacle() throws Exception {
        black_king.setPosition(5,4);
        // there is a king in the way
        assertTrue(black_nightRider.isAnyObstacle(4, 6));
        // but can reach regular 1-knight
        assertFalse(black_nightRider.isAnyObstacle(6,2));
        assertFalse(white_nightRider.isAnyObstacle(6,3));
    }

    @Test
    public void testIsReachable() throws Exception {
        black_king.setPosition(5,4);
        // not a valid move
        assertFalse(black_nightRider.isReachable(4, 5));
        // but can reach regular 1-knight
        assertTrue(black_nightRider.isReachable(6, 2));
        assertTrue(white_nightRider.isReachable(6, 3));
    }

    @Test
    public void testPossibleMovements() throws Exception {
        white_nightRider.setPosition(0,1);
        // it should print out only knight movements
        ArrayList<Position> positions = black_nightRider.possibleMovements();
        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }
        System.out.println("NEXT WAVE>>>>");
        ArrayList<Position> positions1 = white_nightRider.possibleMovements();
        for (Position position : positions1) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }

    }
}