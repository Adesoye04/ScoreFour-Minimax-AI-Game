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
 *
 * @author Adesoye Oyeyiola
 * @studentID 230164785
 */

public class GameState {
     private Board board;
     private Position currentPlayer;

     public GameState() {
          this.board = new Board();
          this.currentPlayer = Position.BLACK;
     }

     public GameState(Board existingBoard, Position player) {
          this.board = existingBoard;
          this.currentPlayer = player;
     }

     public void playMove(int row, int peg) {
          board.makeMove(row, peg, currentPlayer);
          currentPlayer = (currentPlayer == Position.BLACK) ? Position.WHITE : Position.BLACK;
     }

     public boolean isGameOver() {
          return Line.checkWin(Position.BLACK, board) || Line.checkWin(Position.WHITE, board);
     }

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

     public int evaluate(Position aiColor) {
          Position opponent = (aiColor == Position.BLACK) ? Position.WHITE : Position.BLACK;
          int score = 0;

          if (Line.checkWin(aiColor, board)) return 3000;
          if (Line.checkWin(opponent, board)) return -3000;

          for (Location[] line : Line.getAllLines()) {
               int aiCount = 0;
               int opponentCount = 0;
               int emptyCount = 0;

               for (Location loc : line) {
                    Position p = board.getColor(loc.getRow(), loc.getPeg(), loc.getHeight());
                    if (p == aiColor) aiCount++;
                    else if (p == opponent) opponentCount++;
                    else emptyCount++;
               }

               if (aiCount == 3 && emptyCount == 1) score += 300;
               else if (aiCount == 2 && emptyCount == 2) score += 30;

               if (opponentCount == 3 && emptyCount == 1) score -= 300;
               else if (opponentCount == 2 && emptyCount == 2) score -= 30;
          }

          return score;
     }

     public Board getBoard() {
          return board;
     }

     public Position getCurrentPlayer() {
          return currentPlayer;
     }
}
