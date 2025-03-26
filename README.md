# 🎮 Gomoku 9x9

A classic Five-in-a-Row game implemented in Java with an AI opponent. 

<<<<<<< HEAD
![Empty Gomoku board](/assets/starterBoard.png)
=======
![Empty Gomoku board](/assets/emptyBoard.png)
>>>>>>> 0fbca528e2fb52531c46ef977d01ccca1d00b874
## 🚀 Features

- 🎮 9x9 game board for a faster-paced experience
- 🤖 MiniMax AI with alpha-beta pruning
- 👥 Two-player mode (Human vs Human)
- 🎮 Single-player mode (Human vs AI)
- 🎨 Clean and intuitive command-line interface
- 🎯 Win detection for horizontal, vertical, and diagonal lines

## 📋 Requirements

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🛠️ Installation

1. Clone the repository:
```bash
git clone https://github.com/A-Shalchian/Gomoku-9x9.git
```

2. Compile the Java files:
```bash
javac *.java
```

3. Run the game:
```bash
java Main.java
```

## 🎮 How to Play

1. Choose your game mode:
   - 1: Human vs AI
   - 2: Human vs Human

2. Select your player symbol ('B' for black or 'W' for white)

3. Enter your move by specifying the row and column numbers

![players turn](/assets/playerTurn.png)

4. The first player to get 5 of their symbols in a row (horizontal, vertical, or diagonal) wins!


## 🤖 AI Implementation

The AI uses the MiniMax algorithm with alpha-beta pruning to make strategic moves. It evaluates the board state and chooses the best possible move to either win or prevent the opponent from winning.

## 📝 Rules

- Black player always goes first
- Players take turns placing their symbols on the board
- First to get 5 symbols in a row (horizontally, vertically, or diagonally) wins
- If the board is filled and no player has 5 in a row, the game is a draw

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Author

👤 Arash Shalchian

- GitHub: [A-Shalchian](https://github.com/A-shalchian)
- LinkedIn: [Arash Shalchian](https://www.linkedin.com/in/a-shalchian/)
