package minesweeper;

import java.util.Random;

public class MinesweeperGame implements gameInterface {    
    // Numbers that represent states, meaning whether a tile is flagged or unrevealed
    public static final int UNREVEALED_STATE = -2;
    public static final int FLAGGED_STATE = -3;

    private boolean[][] revealedCells;
    private boolean[][] isMine;
    private int[][] gameBoardValue;
    private int[][] gameBoardState;
    
    private int numRows;
    private int numCols;
    private int numMines;
    private int numFlags;
    private boolean gameWon;
    private boolean gameLost;
    
    /**
    * This is the game's constructor
    */
    public MinesweeperGame(int numRows, int numCols, int numMines) {
        if (numMines > numRows*numCols || numRows < 0 || numCols < 0 || numMines < 0) {
            throw new IllegalArgumentException("The number of mines can't be higher than number of cells, and all input must be positive");
        }
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        this.gameBoardValue = new int[numRows][numCols];
        this.gameBoardState = new int[numRows][numCols];
        this.revealedCells = new boolean[numRows][numCols];
        this.isMine = new boolean[numRows][numCols];
        this.gameWon = false;
        this.gameLost = false;
        initGameBoard();
    }
    
    /**
    * This method is used to initalize the game board
    */
    public void initGameBoard() {
        // Clear the board and revealed arrays
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                gameBoardState[row][col] = UNREVEALED_STATE;
                revealedCells[row][col] = false;
            }
        }

        // Initialize game board with mines
        Random rand = new Random();
        int numPlacedMines = 0;
        while (numPlacedMines < numMines) {
            int row = rand.nextInt(numRows);
            int col = rand.nextInt(numCols);
            if (!isMine[row][col]) {
                isMine[row][col] = true;
                numPlacedMines++;
            }
        }
        
        // Calculate adjacent mine counts
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (!isMine[row][col]) {
                    int numAdjacentMines = getNumAdjacentMines(row, col);
                    gameBoardValue[row][col] = numAdjacentMines;
                }
            }
        }       
    }
    
    /**
    * This method is used to get the number of adjacent mines for a tile
    * @return int The number of adjacent mines
    */
    public int getNumAdjacentMines(int row, int col) {
        int numAdjacentMines = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < numRows && j >= 0 && j < numCols && isMine[i][j]) {
                    numAdjacentMines++;
                }
            }
        }
        return numAdjacentMines;
    }

    /**
    * This method is used to count how many non-mine tiles that are revealed
    * @return int The number of revealed non-mine tiles
    */
    public int numRevealedNonMineCells() {
        int count = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (!isMine[row][col] && revealedCells[row][col]) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
    * This method is used to check if a tile is revealed
    * @return boolean A boolean that says if the tile is revealed or not
    */
    public boolean isCellRevealed(int row, int col) {
        return revealedCells[row][col];
    }
    
    /**
    * This method is used to reveal a tile and the adjacent tiles that are not mines
    */
    public void revealCell(int row, int col) {
        if (row > getNumRows() || col > getNumCols()) {
            throw new IllegalArgumentException("You can't reveal a cell outside the board");
        }
        if (!revealedCells[row][col] && gameBoardState[row][col] != FLAGGED_STATE) {
            revealedCells[row][col] = true;
            if (isMine[row][col]) {
                gameLost = true;
                for (int r = 0; r < numRows; r++) {
                    for (int c = 0; c < numCols; c++) {
                        if (isMine[r][c]) {
                            revealedCells[r][c] = true;
                        }
                    }
                }
                return;
            } else if (gameBoardValue[row][col] == 0) {
                // Recursively reveal all adjacent cells with no adjacent mines
                revealAdjacentCells(row, col);
            }
            // If the number of revealed cells are the same as the number of tiles on the board minus the mines, the game is won
            if (numRevealedNonMineCells() == numRows * numCols - numMines) {
                gameWon = true;
            }
        }
    }

    /**
    * This method is used to reveal adjecent tiles (that are not mines) to a specific tile
    */
    public void revealAdjacentCells(int row, int col) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r >= 0 && r < numRows && c >= 0 && c < numCols && !isMine[r][c] && !revealedCells[r][c] && gameBoardState[r][c] != FLAGGED_STATE) {
                    revealedCells[r][c] = true;
                    if (gameBoardValue[r][c] == 0) {
                        revealAdjacentCells(r, c);
                    }
                }
            }
        }
    }

    /**
    * This method is used to get the value of a tile in the board
    * @return int The value of a tile in the board
    */
    public int getBoardValue(int row, int col) {
        return gameBoardValue[row][col];
    }

    /**
    * This method is used to get the state of a tile in the board
    * @return int The number which represent a state of a tile in the board
    */
    public int getBoardState(int row, int col) {
        return gameBoardState[row][col];
    }

    /**
    * This method is used to get whether a tile is a mine or not
    * @return boolean A boolean that says whether the tile is a mine or not
    */
    public boolean getIsMine(int row, int col) {
        return isMine[row][col];
    }

    // Used for testing
    public void setIsMine(int row, int col) {
        isMine[row][col] = true;
    }

    public int getNumRows() {
        return this.numRows;
    }
    
    public int getNumCols() {
        return this.numCols;
    }
    
    public int getNumMines() {
        return this.numMines;
    }

    public int getNumFlags() {
        return this.numFlags;
    }

    // Used for testing
    public boolean getIsFlagged(int row, int col) {
        return gameBoardState[row][col] == FLAGGED_STATE;
    }

    /**
    * This method is used to flag a tile and add a flag to the number of flags counted
    */
    public void flag(int row, int col) {
        int gameBoardStatus = gameBoardState[row][col];
        gameBoardState[row][col] = gameBoardStatus == FLAGGED_STATE ? UNREVEALED_STATE : FLAGGED_STATE;
        this.numFlags = gameBoardStatus == FLAGGED_STATE ? numFlags - 1 : numFlags + 1;
    }

    /**
    * This method is used to check if a game is won
    * @return boolean A boolean that says if a game is won or not
    */
    public boolean isGameWon() {
        return gameWon;
    }
    
    /**
    * This method is used to check if a game is lost
    * @return boolean A boolean that says if a game is lost or not
    */
    public boolean isGameLost() {
        return gameLost;
    }
}
