package minesweeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {
    /**
    * This method is used to write losses and wins to file
    */
    public static void writeToFile(int wins, int losses, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(wins + " " + losses + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace(); // handle exceptions
        }
    }

    /**
    * This method is used to read the user's wins from file
    * @return int The total of games won
    */
    public static int readWinsFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int totalWins = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                int winsRead = Integer.parseInt(parts[0]);
                totalWins += winsRead;
            }
            scanner.close();
            return totalWins;
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // handle exceptions
            return 0;
        }
    }
    
    /**
    * This method is used to read the user's losses from file
    * @return int The total of games lost
    */
    public static int readLossesFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int totalLosses = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                int lossesRead = Integer.parseInt(parts[1]);
                totalLosses += lossesRead;
            }
            scanner.close();
            return totalLosses;
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // handle exceptions
            return 0;
        }
    }
}
