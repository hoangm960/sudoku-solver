import java.awt.*;
import javax.swing.*;

public class SudokuSolver {

    private int[][] board;

    public SudokuSolver(int[][] board) {
        this.board = board;
    }

    public boolean solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(row, col, num)) {
                            board[row][col] = num;
                            if (solve()) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num)
                return false;
            if (board[i][col] == num)
                return false;
            int boxRow = (row / 3) * 3;
            int boxCol = (col / 3) * 3;
            if (board[boxRow + i / 3][boxCol + i % 3] == num)
                return false;
        }
        return true;
    }

    public void printBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] board = new int[9][9];
        // initialize board with values

        SudokuSolver solver = new SudokuSolver(board);
        if (solver.solve()) {
            solver.printBoard();
        } else {
            System.out.println("No solution exists");
        }

        // Create GUI
        JFrame frame = new JFrame("Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField field = new JTextField(1);
                field.setEditable(false);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setText(String.valueOf(board[row][col]));
                panel.add(field);
            }
        }

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
