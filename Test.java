package Testing;

/**
 * The Test class tests the game board for the Score Four game.
 * It ensures the different classes work in synergy.
 * You can add, remove, and request moves via text commands.
 *
 * Updated to use AI and launch GUI mode.
 * @author: Adesoye Oyeyiola
 * @Student Id:230164785
 */

import java.util.Scanner;
import gameBoard.version5.Board;
import gameBoard.version5.Position;
import gameBoard.version5.Line;
import player2.AIPlayer;
import player2.GameState;
import main.ScoreFourGUI;

public class Test {
    private static Board board;

    public static void main(String[] args) {
        startTesting();
    }

    public static void startTesting() {
        Scanner scanner = new Scanner(System.in);
        board = new Board();

        System.out.println("Score Four Testing Mode");

        while (true) {
            String command = scanner.nextLine().toLowerCase().trim();

            if (command.equals("quit.")) {
                System.out.println("Exiting...");
                break;

            } else if (command.equals("clear.")) {
                board.eraseBoard();
                System.out.println("Done.");

            } else if (command.startsWith("add")) {
                handleAddCommand(command);

            } else if (command.startsWith("remove")) {
                handleRemoveCommand(command);

            } else if (command.startsWith("get")) {
                handleGetMoveCommand(command);

            } else if (command.equals("show board.")) {
                showBoard();

            } else if (command.equals("draw board.")) {
                drawBoard();

            } else if (command.equals("go gui.") || command.equals("go interactive.")) {
                ScoreFourGUI gui = new ScoreFourGUI(board);
                gui.run();
                break;

            } else {
                System.out.println("Unknown command.");
            }
        }
    }

    private static void handleAddCommand(String command) {
        command = command.replace(".", "");
        String[] parts = command.split(" ");

        if (parts.length == 5 && parts[0].equals("add") && parts[2].equals("bead") && parts[3].equals("to")) {
            try {
                Position color = Position.valueOf(parts[1].toUpperCase());
                String location = parts[4];
                int peg = locationToPeg(location.charAt(0));
                int row = Integer.parseInt(location.substring(1)) - 1;

                if (board.getHeight(row, peg) < 4) {
                    board.makeMove(row, peg, color);
                    System.out.println("Done.");

                    if (Line.checkWin(color, board)) {
                        System.out.println(color + " wins!");
                    }
                } else {
                    System.out.println("Impossible.");
                }
            } catch (Exception e) {
                System.out.println("Impossible.");
            }
        } else {
            System.out.println("Invalid command format.");
        }
    }

    private static void handleRemoveCommand(String command) {
        command = command.replace(".", "");
        String[] parts = command.split(" ");

        if (parts.length == 4 && parts[0].equals("remove") && parts[1].equals("bead") && parts[2].equals("from")) {
            try {
                String location = parts[3];
                int peg = locationToPeg(location.charAt(0));
                int row = Integer.parseInt(location.substring(1)) - 1;
                int height = board.getHeight(row, peg) - 1;

                if (height >= 0 && board.getColor(row, peg, height) != Position.EMPTY) {
                    board.setColor(row, peg, height, Position.EMPTY);
                    System.out.println("Done.");
                } else {
                    System.out.println("Impossible.");
                }
            } catch (Exception e) {
                System.out.println("Impossible.");
            }
        } else {
            System.out.println("Invalid command format.");
        }
    }

    private static void handleGetMoveCommand(String command) {
        try {
            Position color = command.contains("black") ? Position.BLACK : Position.WHITE;
            AIPlayer ai = new AIPlayer("TestAI", color);
            GameState state = new GameState(board, color);

            int[] move = ai.getBestMove(state);
            if (move != null) {
                System.out.println(convertRowPegToLocation(move[0], move[1]) + ".");
            } else {
                System.out.println("Impossible.");
            }
        } catch (Exception e) {
            System.out.println("Impossible.");
        }
    }

    private static int locationToPeg(char pegChar) {
        return Character.toUpperCase(pegChar) - 'A';
    }

    private static String convertRowPegToLocation(int row, int peg) {
        char pegLetter = (char) ('A' + peg);
        return pegLetter + Integer.toString(row + 1);
    }

    private static void showBoard() {
        for (int row = 0; row < 4; row++) {
            for (int peg = 0; peg < 4; peg++) {
                System.out.print((char) ('A' + peg) + "" + (row + 1) + ": ");
                for (int height = 0; height < 4; height++) {
                    Position color = board.getColor(row, peg, height);
                    if (color != Position.EMPTY) {
                        System.out.print(color == Position.BLACK ? "B" : "W");
                    }
                }
                System.out.println();
            }
        }
    }

    private static void drawBoard() {
        for (int height = 3; height >= 0; height--) {
            System.out.println("Layer " + (height + 1) + ":");
            for (int row = 0; row < 4; row++) {
                for (int peg = 0; peg < 4; peg++) {
                    Position color = board.getColor(row, peg, height);
                    if (color == Position.BLACK) {
                        System.out.print(" B ");
                    } else if (color == Position.WHITE) {
                        System.out.print(" W ");
                    } else {
                        System.out.print(" . ");
                    }
                }
                System.out.println();
            }
            System.out.println("-------------------------");
        }
    }
}
