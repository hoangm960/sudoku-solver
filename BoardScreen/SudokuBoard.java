package BoardScreen;
import Solver.SudokuRandomizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SudokuBoard extends JPanel{
    public static int size = 9;
    private JTextField[][] cells;
    private int[][] board_;


    public SudokuBoard(int[][] sudoku) {
        this.board_ = sudoku;
        buildBoard();
    }

    private void buildBoard() {
        setLayout(new GridLayout(size, size));
        setPreferredSize(new Dimension(600, 600));
        cells = new JTextField[size][size];

        // Create board with cells
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells.length; col++) {
                JTextField cell = new JTextField();
                cell.setPreferredSize(new Dimension(600/20,600/20));
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", 1, 30));
                cell.addKeyListener(new SudokuListener(row, col));
                cells[row][col] = cell;
                fillCell(row, col);
                add(cell);
            }

            //TODO: Decorate the board here

        }
    }

    private void fillCell(int row, int col) {
        if (board_[row][col] != 0 ) {
            cells[row][col].setText(Integer.toString(board_[row][col]));
            cells[row][col].setForeground(Color.GRAY);
            cells[row][col].setEditable(false);
        } else {
            cells[row][col].setText("");
            cells[row][col].setForeground(Color.BLACK);
            cells[row][col].setEditable(true);
        }
    }


    private class SudokuListener extends KeyAdapter {
        private int row, col;

        public SudokuListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // Function to Navigate cells with keyboard
        @Override
        public void keyPressed (KeyEvent e) {
            // Move focus between cells
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveFocus(row, col - 1);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveFocus(row, col + 1);
                    break;
                case KeyEvent.VK_UP:
                    moveFocus(row - 1, col);
                    break;
                case KeyEvent.VK_DOWN:
                    moveFocus(row + 1, col);
                    break;
            }
        }

        // Function to Display valid inputs to cells
        @Override
        public void keyTyped(KeyEvent e) {
            char inputChar = e.getKeyChar();

            // Check if the typed character is generated by computer and if it is a valid digit.
            if (!isComputerGenerated(row, col) && inputChar >= '1' && inputChar <= '9') {
                cells[row][col].setText(Character.toString(inputChar));
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { // Check if Backspace key is pressed
                cells[row][col].setText(""); // Clear the cell
            }
            e.consume(); // Consume the event to prevent further processing
        }

        private void moveFocus (int newRow, int newCol) {
            if (isValidPos(newRow, newCol)) {
                cells[newRow][newCol].requestFocus();
            }
        }

        private boolean isValidPos(int row, int col) {
            return row >= 0 && row < size && col >= 0 && col < size;
        }

        private boolean isComputerGenerated (int row, int col) {
            return board_[row][col] != 0;
        }
    }

    public void updateBoard(int[][] board) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells.length; col++) {
                board_[row][col] = board[row][col];
            }
        }

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells.length; col++) {
                fillCell(row, col);
            }
        }
    }


    public static void main(String[] args) {
        // Sudoku generator driver
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer(9, 20);
        sudokuRandomizer.fillValues(10);
        int[][] sudoku = sudokuRandomizer.getSudoku();
        SwingUtilities.invokeLater(() -> new SudokuBoard(sudoku));
    }
}
