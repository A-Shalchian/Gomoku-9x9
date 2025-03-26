import java.util.Scanner;

public class Gomuko {
    private static final int BOARD_SIZE = 9; // the board will be 9x9
    private static final char EMPTY = '.';
    private static final char BLACK = 'B'; 
    private static final char WHITE = 'W';
    private static final String RESET = "\u001B[0m";
    private static final String WHITE_COLOR = "\u001B[37m";  // Bright white
    private static final String HEADER = "\u001B[34m";
    private static final String BORDER = "\u001B[35m";
    private static final String WIN = "\u001B[32m";
    
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
            
            if (botLogic.checkWin(board, currentSymbol)) {
                printWinMessage(currentSymbol);
                gameOver = true;
            } else if (botLogic.isBoardFull(board)) {
                printDrawMessage();
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
    
    
    private void displayBoard() {
        System.out.println();
        
        // Print column numbers with color
        System.out.print(HEADER + "   ");
        for (int j = 0; j < BOARD_SIZE; j++) {
            System.out.printf("%2d ", j + 1);
        }
        System.out.println(RESET);
        System.out.println(BORDER + "  " + "---".repeat(BOARD_SIZE) + "-" + RESET);
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.printf(HEADER + "%2d" + BORDER + "|" + RESET, i + 1);
            for (int j = 0; j < BOARD_SIZE; j++) {
                switch (board[i][j]) {
                    case 'B':
                        System.out.print(WHITE_COLOR + " B " + RESET);
                        break;
                    case 'W':
                        System.out.print(WHITE_COLOR + " W " + RESET);
                        break;
                    default:
                        System.out.print(" . ");
                }
            }
            System.out.println(BORDER + "|" + RESET);
        }
        System.out.println(BORDER + "  " + "---".repeat(BOARD_SIZE) + "-" + RESET);
    }

    private void printWinMessage(char winner) {
        System.out.println(WIN + "\nPlayer " + (winner == 'B' ? "Black" : "White") + " wins!" + RESET);
    }

    private void printDrawMessage() {
        System.out.println(WIN + "\nThe game is a draw!" + RESET);
    }
}
