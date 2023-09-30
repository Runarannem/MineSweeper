package minesweeper;

public interface gameInterface {
    public void initGameBoard();

    public int getNumAdjacentMines(int row, int col);
    public int numRevealedNonMineCells();
    public boolean isCellRevealed(int row, int col);

    public void revealCell(int row, int col);
    public void revealAdjacentCells(int row, int col);

    public int getBoardValue(int row, int col);
    public int getBoardState(int row, int col);
    public boolean getIsMine(int row, int col);

    public int getNumRows();
    public int getNumCols();
    public int getNumMines();
    public int getNumFlags();

    public void flag(int row, int col);
    
    public boolean isGameWon();
    public boolean isGameLost();

}
