import java.util.Scanner;

public class Gomuko {
    private static final int BOARD_SIZE = 9; // the board will be 9x9
    private static final char EMPTY = '.';
    private static final char BLACK = 'B'; 
    private static final char WHITE = 'W';
    
    private char[][] board;
    private String player1Name;
    private String player2Name;
    private char player1Symbol;
    private char player2Symbol;
    private boolean isAIGame;
    private Scanner scanner;
    private BotLogic botLogic;
    
    public Gomuko() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        scanner = new Scanner(System.in);
        botLogic = new BotLogic();
        initializeBoard();
    }
    
    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    public void startGame() {
        System.out.println("Welcome to Gomoku (Five in a Row)!");
        System.out.println("Select game mode:");
        System.out.println("1. 1 Player (Human vs AI)");
        System.out.println("2. 2 Players (Human vs Human)");
        
        int choice = getValidInput(1, 2);
        
        isAIGame = (choice == 1); // if the choice was one this will be true, else false
        
        if (isAIGame) {
            setupOnePlayerGame();
        } else {
            setupTwoPlayerGame();
        }
        
        playGame();
        scanner.close(); // closing scanner to prevent resource leaks
    }
    
    private void setupOnePlayerGame() {
        System.out.print("Enter your name: ");
        player1Name = scanner.nextLine();
        
        System.out.println(player1Name + ", choose your symbol (B for Black, W for White):");
        char symbol = getValidSymbol();
        
        player1Symbol = symbol;
        player2Symbol = (symbol == BLACK) ? WHITE : BLACK;
        player2Name = "AI";
        
        System.out.println(player1Name + " will play as '" + player1Symbol + "'");
        System.out.println(player2Name + " will play as '" + player2Symbol + "'");
    }
    
    private void setupTwoPlayerGame() {
        System.out.print("Player 1, enter your name: ");
        player1Name = scanner.nextLine();
        
        System.out.println(player1Name + ", choose your symbol (B for Black, W for White):");
        char symbol = getValidSymbol();
        
        player1Symbol = symbol;
        player2Symbol = (symbol == BLACK) ? WHITE : BLACK;
        
        System.out.print("Player 2, enter your name: ");
        player2Name = scanner.nextLine();
        
        System.out.println(player1Name + " will play as '" + player1Symbol + "'");
        System.out.println(player2Name + " will play as '" + player2Symbol + "'");
    }
    
    private char getValidSymbol() {
        while (true) {
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("B") || input.equals("W")) {
                return input.charAt(0);
            }
            // if they enter anything other than B or W
            System.out.println("Invalid input. Please enter 'B' or 'W':");
        }
    }
    
    private int getValidInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } 
                // if they enter any number outside of the range
                System.out.println("Please enter a number between " + min + " and " + max + ":");
            } catch (NumberFormatException e) { // if they enter anything other than a number
                System.out.println("Invalid input. Please enter a number:");
            }
        }
    }
    
    private void playGame() {
        boolean isBlackTurn = true; // black always goes first
        boolean gameOver = false;
        
        displayBoard();
        
        while (!gameOver) {
            char currentSymbol = isBlackTurn ? BLACK : WHITE;
            String currentPlayer = (player1Symbol == currentSymbol) ? player1Name : player2Name;
            boolean isAITurn = isAIGame && currentPlayer.equals("AI");
            
            if (isAITurn) {
                System.out.println(currentPlayer + "'s turn (" + currentSymbol + ")");
                makeAIMove(currentSymbol);
            } else {
                System.out.println(currentPlayer + "'s turn (" + currentSymbol + ")");
                makePlayerMove(currentSymbol);
            }
            
            displayBoard();
            
            if (checkWin(currentSymbol)) {
                System.out.println(currentPlayer + " wins!");
                gameOver = true;
            } else if (isBoardFull()) {
                System.out.println("The game is a draw!");
                gameOver = true;
            }
            
            isBlackTurn = !isBlackTurn;
        }
    }
    
    private void makePlayerMove(char symbol) {
        while (true) {
            System.out.print("Enter row (1-" + BOARD_SIZE + "): ");
            int row = getValidInput(1, BOARD_SIZE) - 1;
            
            System.out.print("Enter column (1-" + BOARD_SIZE + "): ");
            int col = getValidInput(1, BOARD_SIZE) - 1;
            
            if (board[row][col] == EMPTY) {
                board[row][col] = symbol;
                break;
            } else {
                System.out.println("That position is already occupied. Try again.");
            }
        }
    }
    
    private void makeAIMove(char symbol) {
        System.out.println("AI is thinking...");
        int[] bestMove = botLogic.makeMove(board, symbol);
        int row = bestMove[0];
        int col = bestMove[1];
        
        board[row][col] = symbol;
        System.out.println("AI places " + symbol + " at position (" + (row + 1) + ", " + (col + 1) + ")");
    }
    
    private boolean checkWin(char symbol) {
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
    
    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void displayBoard() {
        System.out.println();
        
        // Print column numbers
        System.out.print("   ");
        for (int j = 0; j < BOARD_SIZE; j++) {
            System.out.printf("%2d ", j + 1);
        }
        System.out.println();
        System.out.println("  " + "---".repeat(BOARD_SIZE) + "-"); // Added top border
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.printf("%2d|", i + 1);
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.printf(" %c ", board[i][j]);
            }
            System.out.println("|"); // Added side border
        }
        System.out.println("  " + "---".repeat(BOARD_SIZE) + "-"); // Added bottom border
    }
}
