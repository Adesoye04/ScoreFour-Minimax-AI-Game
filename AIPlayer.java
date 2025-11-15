package player2;

import gameBoard.version5.Board;
import gameBoard.version5.Position;

import java.util.List;
import java.util.Random;

/**
 * AIPlayer models the behaviour of the AI.
 * Allows for various difficulties and better AI logic
 *
 * @author Adesoye Oyeyiola and Rayan Rakib
 * @studentID 230164785 and 230162489
 */

public class AIPlayer extends Player {
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
    private Position getOpponentColor() {
        return (beadColor == Position.BLACK) ? Position.WHITE : Position.BLACK;
    }
    private Random random;
    private Difficulty difficulty;

    public AIPlayer(String name, Position beadColor) {
        this(name, beadColor, Difficulty.HARD); // Default to HARD if not specified
    }

    public AIPlayer(String name, Position beadColor, Difficulty difficulty) {
        super(name, beadColor);
        this.difficulty = difficulty;
        this.random = new Random();
    }

    public int[] getBestMove(GameState state) {
        List<int[]> availableMoves = state.getAvailableMoves();

        if (difficulty == Difficulty.EASY && Math.random() < 0.8) {
            return pickRandomMove(availableMoves);
        } else if (difficulty == Difficulty.MEDIUM && Math.random() < 0.3) {
            return pickRandomMove(availableMoves);
        } else {

            int[] bestMove = null;
            int bestScore = Integer.MIN_VALUE;

            int searchDepth = switch (difficulty) {
                case EASY -> 1;
                case MEDIUM -> 3;
                case HARD -> 5;
            };

            for (int[] move : availableMoves) {
                GameState cloned = state.clone();
                cloned.playMove(move[0], move[1]);
                int score = minimax(cloned, searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }

            return bestMove != null ? bestMove : pickRandomMove(availableMoves); // fallback
        }
    }

    @Override
    public void makeMove(Board board) {
        GameState state = new GameState(board, this.beadColor);
        int[] move = getBestMove(state);
        if (move != null) {
            board.makeMove(move[0], move[1], beadColor);
        }
    }

    private int minimax(GameState state, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || state.isGameOver())
            return state.evaluate(maximizingPlayer ? beadColor : getOpponentColor());

        List<int[]> moves = state.getAvailableMoves();

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : moves) {
                GameState nextState = state.clone();
                nextState.playMove(move[0], move[1]); // This updates the currentPlayer internally
                int eval = minimax(nextState, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : moves) {
                GameState nextState = state.clone();
                nextState.playMove(move[0], move[1]); // CurrentPlayer is already opponent here
                int eval = minimax(nextState, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }


    private int[] pickRandomMove(List<int[]> availableMoves) {
        return availableMoves.get(random.nextInt(availableMoves.size()));
    }
}
