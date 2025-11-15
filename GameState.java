package player2;

import gameBoard.version5.Board;
import gameBoard.version5.Line;
import gameBoard.version5.Position;
import gameBoard.version5.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * GameState models the current state of the board along with the active player.
 * It is used heavily by AI logic for move simulation and evaluation.
 * Files are part of a larger team project
 * @author Adesoye Oyeyiola
 * All code in this document was written by the author: Adesoye Oyeyiola
 * To win a ScoreFour Game, you must have 4 balls of the same color on a line.
 * @studentID 230164785
 */
// Gamestate class
public class GameState {
     private Board board;
     private Position currentPlayer;
// Constructor
     public GameState() {
          this.board = new Board();
          this.currentPlayer = Position.BLACK;
     }
// Constructor with parameters for an existing board and a Position player
     public GameState(Board existingBoard, Position player) {
          this.board = existingBoard;
          this.currentPlayer = player;
     }
// Makes a move
     public void playMove(int row, int peg) {
          board.makeMove(row, peg, currentPlayer);
          currentPlayer = (currentPlayer == Position.BLACK) ? Position.WHITE : Position.BLACK;
     }
// Checks if the game is over by calling the method that runs through the entire table looking for win lines.
     public boolean isGameOver() {
          return Line.checkWin(Position.BLACK, board) || Line.checkWin(Position.WHITE, board);
     }
// Method creates a clone of the board
     public GameState clone() {
          Board newBoard = new Board();
          for (int row = 0; row < 4; row++) {
               for (int peg = 0; peg < 4; peg++) {
                    for (int height = 0; height < 4; height++) {
                         newBoard.setColor(row, peg, height, board.getColor(row, peg, height));
                    }
               }
          }
          return new GameState(newBoard, this.currentPlayer);
     }
// Arranges all available moves into an array
     public List<int[]> getAvailableMoves() {
          List<int[]> moves = new ArrayList<>();
          for (int row = 0; row < 4; row++) {
               for (int peg = 0; peg < 4; peg++) {
                    if (board.getHeight(row, peg) < 4) {
                         moves.add(new int[]{row, peg});
                    }
               }
          }
          return moves;
     }
/** This is the main logic body of the code. The entire AI is based on the fact that it tries to maximize its score.
 * Logically, it is not out to beat you, rather, it is out to increase its score through playing the moves with the highest score.
 * We weighted sets of moves the AI could take based on the importance of the move. 
 * Any move from you that will minimize its score is also countered (or at least, it tries to)
 * Let me walk you through the weights.
 */
     public int evaluate(Position aiColor) {
          Position opponent = (aiColor == Position.BLACK) ? Position.WHITE : Position.BLACK;
          int score = 0;
/** A win is the most important part of the game as a win for the AI ends the game.
 * Therefore, it's weighted the highest. A loss also has the lowest weight as an opponent's win ensures a loss for our AI
 */
          if (Line.checkWin(aiColor, board)) return 3000;  
          if (Line.checkWin(opponent, board)) return -3000;

          for (Location[] line : Line.getAllLines()) {
               int aiCount = 0;
               int opponentCount = 0;
               int emptyCount = 0;
// checks through all the lines for every past play
               for (Location loc : line) {
                    Position p = board.getColor(loc.getRow(), loc.getPeg(), loc.getHeight());
                    if (p == aiColor) aiCount++;
                    else if (p == opponent) opponentCount++;
                    else emptyCount++;
               }

               if (aiCount == 3 && emptyCount == 1) score += 300; // If the AI has 3 balls in a row from previous plays; weight:300
               else if (aiCount == 2 && emptyCount == 2) score += 30; //If the AI has 2 balls in a row on a line; weight:30

               if (opponentCount == 3 && emptyCount == 1) score -= 300;// same as above but negated due to it being the opponent's possible move
               else if (opponentCount == 2 && emptyCount == 2) score -= 30;
               //There were plans to add center weighting as the center is a prime position to start a play but it was ultimately shelved due to time constraints
          }

          return score;
     }
// returns board
     public Board getBoard() {
          return board;
     }
//returns current player
     public Position getCurrentPlayer() {
          return currentPlayer;
     }
}
