public class BotLogic {
    private static final int BOARD_SIZE = 9;
    private static final char EMPTY = '.';
    private static final char BLACK = 'B';
    private static final char WHITE = 'W';
    private static final int MAX_DEPTH = 3;
    
    public int[] makeMove(char[][] board, char symbol) {
        return findBestMove(board, symbol);
    }
    
    private int[] findBestMove(char[][] board, char symbol) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        char opponentSymbol = (symbol == BLACK) ? WHITE : BLACK;
        
        // For each empty cell, try placing the symbol and evaluate the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    // Try this move
                    board[i][j] = symbol;
                    
                    // If this move wins, choose it immediately
                    if (checkWin(board, symbol)) {
                        board[i][j] = EMPTY; // Undo the move
                        return new int[]{i, j};
                    }
                    
                    // Check if opponent can win in one move and block it
                    board[i][j] = opponentSymbol;
                    if (checkWin(board, opponentSymbol)) {
                        board[i][j] = EMPTY; // Undo the move
                        return new int[]{i, j};
                    }
                    
                    // Restore and evaluate using minimax
                    board[i][j] = EMPTY;
                    board[i][j] = symbol;
                    int score = minimax(board, 0, false, symbol, opponentSymbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = EMPTY; // Undo the move
                    
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        
        return bestMove;
    }
    
    private int minimax(char[][] board, int depth, boolean isMaximizing, char symbol, char opponentSymbol, int alpha, int beta) {
        // Base cases
        if (checkWin(board, symbol)) return 10 - depth;
        if (checkWin(board, opponentSymbol)) return depth - 10;
        if (isBoardFull(board) || depth == MAX_DEPTH) return 0;
        
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = symbol;
                        int eval = minimax(board, depth + 1, false, symbol, opponentSymbol, alpha, beta);
                        board[i][j] = EMPTY;
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = opponentSymbol;
                        int eval = minimax(board, depth + 1, true, symbol, opponentSymbol, alpha, beta);
                        board[i][j] = EMPTY;
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) break;
                    }
                }
            }
            return minEval;
        }
    }
    
    private boolean checkWin(char[][] board, char symbol) {
        // Check horizontal
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i][j + k] != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        
        // Check vertical
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j] != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        
        // Check diagonal (top-left to bottom-right)
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        
        // Check diagonal (top-right to bottom-left)
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = BOARD_SIZE - 1; j >= 4; j--) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j - k] != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        
        return false;
    }
    
    private boolean isBoardFull(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
