import BoardScreen.SudokuBoard;
import Solver.SudokuRandomizer;
import Solver.SudokuSolver;

import javax.swing.*;
import java.awt.*;

public class SudokuFrame extends JFrame {
    public SudokuFrame() {
        setTitle("Sudoku Generator Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(800, 820);

        // holder: the parent of Board panel
        JPanel holder = new JPanel(new BorderLayout());


        // emptyPanel: it has the same level as board panel - to shift the boardPanel down.
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.blue);
        emptyPanel.setPreferredSize(new Dimension(10, 30));
        holder.add(emptyPanel, BorderLayout.NORTH);


        //Board panel: contains the sudoku board class
        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.blue);
        boardPanel.setPreferredSize(new Dimension(600, 600));
        holder.add(boardPanel, BorderLayout.CENTER);
        add(holder, BorderLayout.CENTER);

        // Create an instance of the randomizer class
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer(9, 20);
        sudokuRandomizer.fillValues();
        int[][] sudoku = sudokuRandomizer.getSudoku();
        // Create an instance of the SudokuBoard class <- visualise the board with this class
        SudokuBoard displayBoard = new SudokuBoard(sudoku);
        // Add instance to the board panel
        boardPanel.add(displayBoard, BorderLayout.CENTER);


        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton newBoardButton = createButton("New Board", 150, 60);
        JButton submitButton = createButton("Submit", 150, 60);
        JButton giveUpButton = createButton("Give Up!", 150, 60);

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

    public static void main(String[] args) {
        // Sudoku generator driver
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer(9, 20);
        sudokuRandomizer.fillValues();
        int[][] sudoku = sudokuRandomizer.getSudoku();
        sudokuRandomizer.printSudoku();
        System.out.println();

        // Sudoku solver driver
        SudokuSolver sudokuSolver = new SudokuSolver();
        sudokuSolver.solve(sudoku);
        sudokuSolver.printSudoku(sudoku);
        
        // Sudoku frame driver
        SwingUtilities.invokeLater(SudokuFrame::new);
    }
}
