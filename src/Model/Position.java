package Model;

/**
 * Created by luvsandondov on 9/11/14.
 */
public class Position {
    /*
    * Helper class to facilitate location by creating (row, column) pair
    * */
    private int row;
    private int col;

    public Position() {
        row = -1;
        col = -1;
    }

    public Position(int x, int y) {
        row = x;
        col = y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int x) {
        row = x;
    }

    public void setCol(int y) {
        col = y;
    }

}
