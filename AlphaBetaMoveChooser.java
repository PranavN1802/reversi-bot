import java.util.List;

public class AlphaBetaMoveChooser extends MoveChooser {
    private static final int[][] board_weights = {
            { 120, -20, 20, 5, 5, 20, -20, 120 },
            { -20, -40, -5, -5, -5, -5, -40, -20 },
            { 20, -5, 15, 3, 3, 15, -5, 20 },
            { 5, -5, 3, 3, 3, 3, -5, 5 },
            { 5, -5, 3, 3, 3, 3, -5, 5 },
            { 20, -5, 15, 3, 3, 15, -5, 20 },
            { -20, -40, -5, -5, -5, -5, -40, -20 },
            { 120, -20, 20, 5, 5, 20, -20, 120 },
    };

    public AlphaBetaMoveChooser(int searchDepth) {
        super("AlphaBeta", searchDepth);
    }

    public Move chooseMove(BoardState boardState, Move hint) {
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        Score bestScore = minimax_val(boardState, searchDepth, alpha, beta, boardState.colour == 1);
        return bestScore.getMove();
    }

    private Score minimax_val(BoardState boardState, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (depth == 0 || boardState.gameOver()) {
            double score = boardEval(boardState);
            return new Score(score, null);
        }

        List<Move> legalMoves = boardState.getLegalMoves();
        Score bestScore = null;

        if (maximizingPlayer) {
            double maxScore = Double.NEGATIVE_INFINITY;
            for (Move legalMove : legalMoves) {
                BoardState boardStateCopy = boardState.deepCopy();
                boardStateCopy.makeLegalMove(legalMove);

                Score score = minimax_val(boardStateCopy, depth - 1, alpha, beta, false);
                if (score.getScore() > maxScore) {
                    maxScore = score.getScore();
                    bestScore = new Score(maxScore, legalMove);
                }

                alpha = Math.max(alpha, maxScore);
                if (alpha >= beta) {
                    break;
                }
            }
        } else {
            double minScore = Double.POSITIVE_INFINITY;
            for (Move legalMove : legalMoves) {
                BoardState copy = boardState.deepCopy();
                copy.makeLegalMove(legalMove);

                Score score = minimax_val(copy, depth - 1, alpha, beta, true);
                if (score.getScore() < minScore) {
                    minScore = score.getScore();
                    bestScore = new Score(minScore, legalMove);
                }

                beta = Math.min(beta, minScore);
                if (beta <= alpha) {
                    break;
                }
            }
        }

        return bestScore;
    }

    public int boardEval(BoardState boardState) {
        int weights_of_move = 0;
        for (int r = 0; r <= 7; r++) {
            for (int c = 0; c <= 7; c++) {
                if (boardState.getContents(r, c) == 1) {
                    weights_of_move += board_weights[r][c];
                } else if (boardState.getContents(r, c) == -1) {
                    weights_of_move -= board_weights[r][c];
                }
            }
        }

        return weights_of_move;
    }

    private static class Score {
        private double score;
        private Move move;

        public Score(double score, Move move) {
            this.score = score;
            this.move = move;
        }

        public double getScore() {
            return score;
        }

        public Move getMove() {
            return move;
        }

        public void setMove(Move move) {
            this.move = move;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}