package tic_tac_toe;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TIC_TAC_TOE_GAME extends Frame implements ActionListener {
    private static final char X = 'X';
    private static final char O = 'O';
    private static char currentPlayer = X;
    private static char[][] board = new char[3][3];
    private Button[][] buttons = new Button[3][3];
    private Label playerTurnLabel;
    private boolean playWithBot = false;
    private Random random = new Random();

    public TIC_TAC_TOE_GAME() {
        // Ask user to choose opponent
        Dialog choiceDialog = new Dialog(this, "Choose Opponent", true);
        choiceDialog.setLayout(new FlowLayout());
        choiceDialog.setSize(300, 150);
        Label label = new Label("Play against:");
        Button humanBtn = new Button("Human");
        Button botBtn = new Button("Bot");
        humanBtn.addActionListener(e -> {
            playWithBot = false;
            choiceDialog.dispose();
        });
        botBtn.addActionListener(e -> {
            playWithBot = true;
            choiceDialog.dispose();
        });
        choiceDialog.add(label);
        choiceDialog.add(humanBtn);
        choiceDialog.add(botBtn);
        choiceDialog.setVisible(true);

        // Initialize the frame
        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Create a panel for the game board
        Panel boardPanel = new Panel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.BLACK); // Black background
        initializeBoard();

        // Create buttons and add to the panel
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(" ");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].setBackground(Color.BLACK); // Button background black
                buttons[i][j].setForeground(Color.WHITE); // Default text color
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }

        // Create a label to display the current player's turn
        playerTurnLabel = new Label("Player " + currentPlayer + "'s turn", Label.CENTER);
        playerTurnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerTurnLabel.setBackground(Color.DARK_GRAY);
        playerTurnLabel.setForeground(Color.CYAN);

        // Add components to the frame
        add(playerTurnLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        // Close operation
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (clickedButton == buttons[i][j] && board[i][j] == ' ') {
                    board[i][j] = currentPlayer;
                    clickedButton.setLabel(String.valueOf(currentPlayer));

                    // Apply neon color for X and O
                    if (currentPlayer == X) {
                        clickedButton.setForeground(Color.GREEN); // Neon green for X
                    } else {
                        clickedButton.setForeground(Color.RED); // Neon pink for O
                    }

                    if (checkWin()) {
                        displayWinner();
                        return;
                    } else if (checkDraw()) {
                        displayDraw();
                        return;
                    }
                    currentPlayer = (currentPlayer == X) ? O : X;
                    updatePlayerTurnLabel();

                    // If playing with bot and it's bot's turn, make bot move
                    if (playWithBot && currentPlayer == O) {
                        botMove();
                    }
                }
            }
        }
    }

    private void botMove() {
        // Simple random move bot
        int i, j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (board[i][j] != ' ');
        board[i][j] = O;
        buttons[i][j].setLabel(String.valueOf(O));
        buttons[i][j].setForeground(Color.RED);
        if (checkWin()) {
            displayWinner();
            return;
        } else if (checkDraw()) {
            displayDraw();
            return;
        }
        currentPlayer = X;
        updatePlayerTurnLabel();
    }

    private void updatePlayerTurnLabel() {
        playerTurnLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true;
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void displayWinner() {
        showMessage("Player " + currentPlayer + " wins!");
    }

    private void displayDraw() {
        showMessage("The game is a draw!");
    }

    private void showMessage(String message) {
        Dialog dialog = new Dialog(this, "Game Over", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 150);
        Label label = new Label(message);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        dialog.add(label);
        Button ok = new Button("OK");
        ok.addActionListener(e -> {
            dialog.dispose();
            resetGame();
        });
        dialog.add(ok);
        dialog.setVisible(true);
    }

    private void resetGame() {
        initializeBoard();
        currentPlayer = X;
        updatePlayerTurnLabel();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setLabel(" ");
                buttons[i][j].setForeground(Color.WHITE); // Reset to default color
            }
        }
    }

    public static void main(String[] args) {
        new TIC_TAC_TOE_GAME();
    }
}
