public final class Viewport
{
    private int row;
    private int col;
    private final int numRows;
    private final int numCols;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public boolean contains(Point p) {
        return p.y >= this.row && p.y < this.row + this.numRows
                && p.x >= this.col && p.x < this.col + this.numCols;
    }

    public Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    public Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

    public void shift(int col, int row) {
        this.setCol(col);
        this.setRow(row);
    }

    public int getRow() {
        return row;
    }

    private void setRow(int r) {
        row = r;
    }

    public int getCol() {
        return col;
    }

    private void setCol(int c) {
        col = c;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

}
