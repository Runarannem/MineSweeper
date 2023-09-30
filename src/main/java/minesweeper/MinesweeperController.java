package minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

public class MinesweeperController {

    @FXML private GridPane gameGrid;

    @FXML private Label timeLabel;
    @FXML private Label mineLabel;
    @FXML private Label statusLabel;
    @FXML private Label totalWinsLabel;
    @FXML private Label totalLossesLabel;

    @FXML private Button newGameMenuItem;

    private MinesweeperGame game;
    private Button[][] buttonGrid;

    private void initMineSweeper() {
        // Create a new MinesweeperGame with default settings
        this.game = new MinesweeperGame(10, 10, 10);

        // Initialize the buttons array and add them to the gameGrid GridPane
        buttonGrid = new Button[game.getNumRows()][game.getNumCols()];
        for (int row = 0; row < game.getNumRows(); row++) {
            for (int col = 0; col < game.getNumCols(); col++) {
                Button button = new Button();
                button.setPrefSize(30, 30);
                final int finalRow = row;
                final int finalCol = col;
                button.setOnMouseClicked(event -> {
                    if (!game.isGameLost() && !game.isGameWon()) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            handleRightClick(finalRow, finalCol);
                        } else {
                            game.revealCell(finalRow, finalCol);
                        }
                        updateBoard();
                        updateStatusLabel();
                    }
                });

                gameGrid.add(button, col, row);
                buttonGrid[row][col] = button;
            }
        }

        // Update the mine and status label
        statusLabel.setText("");
        mineLabel.setText("Mines: " + game.getNumMines());
    }

    @FXML
    private void handleNewGameMenuItemClick() {
        // Reset the game and update the UI
        initMineSweeper();
        updateBoard();
        updateStatusLabel();
    }

    /**
     * Update the button text and style based on the MinesweeperGame instance.
     */
    private void updateBoard() {
        for (int row = 0; row < game.getNumRows(); row++) {
            for (int col = 0; col < game.getNumCols(); col++) {
                Button button = buttonGrid[row][col];
                int cellState = game.getBoardState(row, col);
                int cellValue = game.getBoardValue(row, col);
                boolean isMine = game.getIsMine(row, col);
                if (game.isCellRevealed(row, col)) {
                    if (isMine) {
                        button.setText("X");
                        button.setStyle("-fx-background-color: #f00; -fx-border-color: black");
                    } else if (cellValue == 0) {
                        button.setText("");
                        button.setStyle("-fx-background-color: #fff; -fx-border-color: black");
                    } else {
                        button.setText(Integer.toString(cellValue));
                        button.setStyle("-fx-background-color: #fff; -fx-border-color: black");
                    }
                } else if (!game.isCellRevealed(row, col)) {
                    if (cellState == MinesweeperGame.FLAGGED_STATE) {
                        button.setText("F");
                        button.setStyle("-fx-background-color: #0f0; -fx-border-color: black");
                    } else {
                        button.setText("");
                        button.setStyle("-fx-background-color: #ccc; -fx-border-color: black");
                    }
                }
            }
        }
    }

    /**
     * Update win-/losscount and their labels, as well as the satuslabel.
     */
    private void updateStatusLabel() {
        int winCount = FileIO.readWinsFromFile("data.txt");
        int lossCount = FileIO.readLossesFromFile("data.txt");
        if (game.isGameWon()) {
            statusLabel.setText("YOU WON! :)");
            statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #0f0");
            winCount++;
            FileIO.writeToFile(winCount, lossCount, "data.txt");
        } else if (game.isGameLost()) {
            statusLabel.setText("YOU LOST! :(");
            statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #f00");
            lossCount++;
            FileIO.writeToFile(winCount, lossCount, "data.txt");
        } else {
            statusLabel.setText("");
        }
        totalWinsLabel.setText("Total wins: " + FileIO.readWinsFromFile("data.txt"));
        totalLossesLabel.setText("Total losses: " + FileIO.readLossesFromFile("data.txt"));
    }

    /**
     * Handle right click, so that the button is flagged instead of revealed.
     */
    private void handleRightClick(int row, int col) {
        Button button = buttonGrid[row][col];
        int cellValue = game.getBoardState(row, col);
        if (cellValue == MinesweeperGame.FLAGGED_STATE) {
            button.setText("");
            game.flag(row, col);
        } else if (cellValue == MinesweeperGame.UNREVEALED_STATE) {
            button.setText("F");
            game.flag(row, col);
        }
        mineLabel.setText("Mines: " + (game.getNumMines() - game.getNumFlags()));
    }
}
