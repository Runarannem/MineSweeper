package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class FileIOTest {

    @Test
    void testWriteToFile() throws IOException {
        // Create a temporary file for testing
        File tempFile = File.createTempFile("data1", ".txt");
        tempFile.deleteOnExit();

        int wins = 5;
        int losses = 3;

        // Write to the temporary file
        FileIO.writeToFile(wins, losses, tempFile.toString());

        // Read the file and check if the values are correct
        Scanner scanner = new Scanner(tempFile);
        int readWins = scanner.nextInt();
        int readLosses = scanner.nextInt();
        scanner.close();

        assertEquals(wins, readWins);
        assertEquals(losses, readLosses);
    }

    @Test
    void testReadWinsFromFile() {
        // Create a temporary file with some data
        File tempFile = new File("data1.txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("3 2");
        } catch (FileNotFoundException e) {
            fail("Failed to create temporary file for testing");
        }

        // Read the temporary file and check if the values are correct
        int totalWins = FileIO.readWinsFromFile(tempFile.getAbsolutePath());
        assertEquals(3, totalWins);
    }

    @Test
    void testReadLossesFromFile() {
        // Create a temporary file with some data
        File tempFile = new File("data1.txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("3 2");
        } catch (FileNotFoundException e) {
            fail("Failed to create temporary file for testing");
        }

        // Read the temporary file and check if the values are correct
        int totalLosses = FileIO.readLossesFromFile(tempFile.getAbsolutePath());
        assertEquals(2, totalLosses);
    }
}

