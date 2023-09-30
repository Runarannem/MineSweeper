package minesweeper;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinesweeperTest {

    private MinesweeperGame game;

    @BeforeEach
    void setUp() {
        game = new MinesweeperGame(10, 10, 10);
    }

    @Test
    void testInitGameBoard() {
        // Test that game board is initialized properly
        game.initGameBoard();
        assertFalse(game.isGameWon());
        assertFalse(game.isGameLost());
        assertEquals(0, game.numRevealedNonMineCells());
    }

    @Test
    void testGetNumAdjacentMines() {
        MinesweeperGame game1 = new MinesweeperGame(10, 10, 0);
        // Test a cell with no adjacent mines
        assertEquals(0, game1.getNumAdjacentMines(0, 0));

        // Test a cell with one adjacent mine
        game1.setIsMine(0, 1);;
        assertEquals(1, game1.getNumAdjacentMines(0, 0));

        // Test a cell with two adjacent mines
        game1.setIsMine(1, 1);;
        assertEquals(2, game1.getNumAdjacentMines(0, 0));
    }

    @Test
    void testIsCellRevealed() {
        // Test that a cell that has not been revealed is not revealed
        assertFalse(game.isCellRevealed(0, 0));

        // Test that a cell that has been revealed is revealed
        game.revealCell(0, 0);
        assertTrue(game.isCellRevealed(0, 0));
    }

    @Test
    void testRevealCell() {
        // Set up game board with a mine and a non-mine cell
        game.setIsMine(0, 0);
        // Reveal non-mine cell
        game.revealCell(1, 1);
        assertTrue(game.isCellRevealed(1, 1));
        assertFalse(game.isGameLost());
        assertFalse(game.isGameWon());
        // Reveal mine cell
        game.revealCell(0, 0);
        assertTrue(game.isCellRevealed(0, 0));
        assertTrue(game.isCellRevealed(1, 1));
        assertTrue(game.isGameLost());
        assertFalse(game.isGameWon());
    }

    @Test
    void testRevealAdjacentCells() {
        // Set up a scenario where no cells have adjacent mines
        MinesweeperGame game2 = new MinesweeperGame(10, 10, 0);
        game2.setIsMine(5, 5);

        // Call the method on one cell with no adjacent mines
        game2.revealAdjacentCells(2, 2);

        // Check that all adjacent cells have been revealed
        assertTrue(game2.isCellRevealed(1, 1));
        assertTrue(game2.isCellRevealed(1, 2));
        assertTrue(game2.isCellRevealed(1, 3));
        assertTrue(game2.isCellRevealed(2, 1));
        assertTrue(game2.isCellRevealed(2, 2));
        assertTrue(game2.isCellRevealed(2, 3));
        assertTrue(game2.isCellRevealed(3, 1));
        assertTrue(game2.isCellRevealed(3, 2));
        assertTrue(game2.isCellRevealed(3, 3));

        // Check that the mine cell has not been revealed
        assertFalse(game2.isCellRevealed(5, 5));
    }

    @Test
    void testFlag() {
        // Check if the cell is flagged
        game.flag(2, 3);
        assertTrue(game.getIsFlagged(2, 3), "The cell should be flagged");

        // Check if the same cell gets unflagged
        game.flag(2, 3);
        assertFalse(game.isCellRevealed(2, 3), "The flag should be removed");
        
        // Check if the cell is flagged again
        game.flag(2, 3);
        assertTrue(game.getIsFlagged(2, 3), "The cell should be flagged again");

        // Check if the number of flags is correct
        assertEquals(1, game.getNumFlags(), "There should be one flag on the board");

        // Check if the cell gets unflagged
        game.flag(2, 3);
        assertFalse(game.isCellRevealed(2, 3), "The flag should be removed");

        // Check if the number of flags is correct
        assertEquals(0, game.getNumFlags(), "There should be no flags on the board");
    }
}
