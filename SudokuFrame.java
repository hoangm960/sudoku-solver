import BoardScreen.SudokuBoard;
import Solver.SudokuRandomizer;
import Solver.SudokuSolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame {
    private static int[][] unsolved_board_;
    private static int[][] solved_board_;

    public SudokuFrame() {
        setTitle("Sudoku Generator Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(800, 820);

        // holder: the parent of Board panel
        JPanel holder = new JPanel(new BorderLayout());

        // emptyPanel: it has the same level as board panel - to shift the boardPanel
        // down.
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.blue);
        emptyPanel.setPreferredSize(new Dimension(10, 30));
        holder.add(emptyPanel, BorderLayout.NORTH);

        // Board panel: contains the sudoku board class
        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.blue);
        boardPanel.setPreferredSize(new Dimension(600, 600));
        holder.add(boardPanel, BorderLayout.CENTER);
        add(holder, BorderLayout.CENTER);

        // Create an instance of the SudokuBoard class <- visualise the board with this
        // class
        SudokuBoard displayBoard = new SudokuBoard(unsolved_board_);
        // Add instance to the board panel
        boardPanel.add(displayBoard, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton newBoardButton = createButton("New Board", 150, 60);
        // TODO: Add check solution function
        JButton submitButton = createButton("Submit", 150, 60);
        // TODO: Add display solution function
        JButton giveUpButton = createButton("Give Up!", 150, 60);

        newBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                renewBoard();
                displayBoard.updateBoard(unsolved_board_);
            }
        });

        giveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                displayBoard.updateBoard(solved_board_);
            }
        });

        buttonPanel.add(newBoardButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(giveUpButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(Color.green);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFont(new Font("Arial", Font.BOLD, 20));
        return button;
    }

    private static void renewBoard() {
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer(9, 30);
        sudokuRandomizer.fillValues(50);
        sudokuRandomizer.printSudoku();
        unsolved_board_ = sudokuRandomizer.getSudoku();
        solved_board_ = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solved_board_[i][j] = unsolved_board_[i][j];
            }
        }

        SudokuSolver sudokuSolver = new SudokuSolver();
        sudokuSolver.solve(solved_board_);
        sudokuSolver.printSudoku(solved_board_);
    }

    public static void main(String[] args) {
        // Sudoku generator driver
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer(9, 30);
        sudokuRandomizer.fillValues(50);
        sudokuRandomizer.printSudoku();
        unsolved_board_ = sudokuRandomizer.getSudoku();
        solved_board_ = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solved_board_[i][j] = unsolved_board_[i][j];
            }
        }

        // Sudoku solver driver
        SudokuSolver sudokuSolver = new SudokuSolver();
        sudokuSolver.solve(solved_board_);
        sudokuSolver.printSudoku(solved_board_);

        // Sudoku frame driver
        SwingUtilities.invokeLater(SudokuFrame::new);
    }
}
