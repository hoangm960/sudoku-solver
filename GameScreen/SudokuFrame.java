package GameScreen;

import Solver.SudokuRandomizer;
import Solver.SudokuSolver;
import helper.Difficulty;
import helper.DifficultyMap;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import Controller.Controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import helper.Constant;



public class SudokuFrame extends JFrame {
    private Timer timer;
    private JLabel timerLabel;
    private static int[][] unsolved_board_;
    private static int[][] solved_board_;
    private static DifficultyMap difficulties = new DifficultyMap(3);
    private boolean difficulty_level = false;
    private static String difficulty_ = "easy"; // default

    public SudokuFrame(Controller controller) {
        setTitle("Sudoku - Game On!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(800, 820);
        setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon(Constant.getLogoLink());
        setIconImage(image.getImage());

        // holder: the parent of Board panel
        JPanel holder = new JPanel(new BorderLayout());

        // emptyPanel: it has the same level as board panel - to shift the boardPanel
        // down.
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(0x6d8fa4));
        emptyPanel.setPreferredSize(new Dimension(10, 30));
        holder.add(emptyPanel, BorderLayout.NORTH);

        // Board panel: contains the sudoku board class
        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(new Color(0x6d8fa4));
        // GradientPanel gradientPanel = new GradientPanel("#00c16e", "#037ef3");
        boardPanel.setPreferredSize(new Dimension(600, 600));
        holder.add(boardPanel, BorderLayout.CENTER);
        add(holder, BorderLayout.CENTER);
        // add(gradientPanel, BorderLayout.CENTER);

        // Create an instance of the SudokuBoard class <- visualise the board with this
        // class
        // SudokuBoard displayBoard = new SudokuBoard(unsolved_board_);
        SudokuBoard displayBoard = new SudokuBoard(new int[9][9]);

        // Add instance to the board panel
        boardPanel.add(displayBoard, BorderLayout.CENTER);

        // Create a panel for the buttons and timer
        // Panel buttonAndTimerPanel = new JPanel(new BorderLayout());

        // Set label "CS2_Spring 2024"
        JLabel bottom_label = new JLabel();
        bottom_label.setText("Hoang Nhat Minh, Nguyen Le Na, Le Thi Que My, Nguyen Hoang Thang. CS2_2024");
        bottom_label.setFont(new Font("Garamond", Font.BOLD, 16));
        bottom_label.setForeground(new Color(0x00000));

        // Add label to content pane
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(bottom_label);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBackground(new Color(0xfefefe));

        // Create the timer label
        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Garamond", Font.BOLD, 18));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add the timer label to the content pane
        add(timerLabel, BorderLayout.WEST);

        // Create and configure the timer (with initial delay set to 0)
        timer = new Timer(1000, new ActionListener() {
            private int hours = 0, minutes = 0, seconds = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the time
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
                if (minutes == 60) {
                    hours++;
                    minutes = 0;
                }

                // Update the timer label
                timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x102944));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton newChooseLevel = createButton("Choose Level", 150, 60);
        JButton submitButton = createButton("Submit", 150, 60);
        JButton giveUpButton = createButton("Give Up!", 150, 60);

        newChooseLevel.setFont(new Font("Garamond", Font.BOLD, 22));
        submitButton.setFont(new Font("Garamond", Font.BOLD, 22));
        giveUpButton.setFont(new Font("Garamond", Font.BOLD, 22));

        newChooseLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String[] options = { "Easy", "Medium", "Hard" };
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Select level:",
                        "Difficulty Level",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]);

                // Handle the user's choice
                if (choice >= 0) {
                    switch (choice) {
                        case 0: // Easy
                            difficulty_ = "easy";
                            break;
                        case 1: // Medium
                            difficulty_ = "medium";
                            break;
                        case 2: // Hard
                            difficulty_ = "hard";
                            break;
                    }
                    difficulty_level = true;
                    // Renew the board with the selected difficulty level
                    fillDifficulties(200);
                    renewBoard(difficulty_);
                    displayBoard.updateBoard(unsolved_board_);
                    timer.start();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Get current time

                if (!difficulty_level) {
                    JOptionPane.showMessageDialog(null, "Chọn level chưa mà đòi nộp!",
                            "Notice", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Show confirmation dialog
                JOptionPane confirmDialog = new JOptionPane("You are about to submit. Are you sure?",
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = confirmDialog.createDialog(null, "Confirmation");
                dialog.setVisible(true);

                // Get user's choice
                Object selectedValue = confirmDialog.getValue();

                if (selectedValue.equals(JOptionPane.YES_OPTION)) {
                    // User clicked Yes, proceed with showing the result
                    timer.stop();
                    if (checkSolution(displayBoard.getBoard())) {
                        JOptionPane.showMessageDialog(null, "bạn thật vjp pro, be my lỏd :D.");
                    } else {
                        int false_option = JOptionPane.showOptionDialog(null,
                                "đồ ngu ahihi, chúc bạn may mắn lần sau :p\n",
                                "Result", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                                new String[] { "Answer", "Cancel" }, "Answer");

                        if (false_option == JOptionPane.YES_NO_OPTION) {
                            displayBoard.updateBoard(solved_board_);
                        }
                    }
                }
            }
        });

        giveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (!difficulty_level) {
                    JOptionPane.showMessageDialog(null, "Chọn level chưa mà đòi withdraw!",
                            "Notice", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Show confirmation dialog
                JOptionPane gu_confirmDialog = new JOptionPane(
                        "Do you dare to give up? Chúng tôi còn không give up CS2 mà bạn give up game này",
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog gu_dialog = gu_confirmDialog.createDialog(null, "Give Up");
                gu_dialog.setVisible(true);

                // Get user's choice
                Object gu_selectedValue = gu_confirmDialog.getValue();

                if (gu_selectedValue.equals(JOptionPane.YES_OPTION)) {
                    // User clicked Yes, proceed with showing the result
                    timer.stop();
                    displayBoard.updateBoard(solved_board_);
                    difficulty_level = false;
                }
            }
        });

        buttonPanel.add(newChooseLevel);
        buttonPanel.add(submitButton);
        buttonPanel.add(giveUpButton);

        add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);

        // Handle window closing event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Exit the application when the window is closed
                System.exit(0);
            }
        });
    }

    private static void fillDifficulties(int max_tries) {
        String[] difficultyTypes = { "easy", "medium", "hard" };
        for (int i = 30; i <= 50; i += 10) {
            difficulties.put(
                    difficultyTypes[i % 3],
                    new Difficulty(
                            9,
                            new int[] { i, i + 10 },
                            max_tries));
        }
    }

    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(0xe4e0e7));
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        return button;
    }

    private static void renewBoard(String difficulty) {
        Difficulty difficultyValues = difficulties.get(difficulty);
        System.out.println(difficulty);
        System.out.println(difficultyValues.board_size);
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer(
                difficultyValues.board_size,
                (int) Math.floor(Math.random()
                        * (difficultyValues.hole_range[1] - difficultyValues.hole_range[0])
                        + difficultyValues.hole_range[0]));
        boolean success = sudokuRandomizer.fillValues(difficultyValues.max_tries);
        while (!success) {
            sudokuRandomizer = new SudokuRandomizer(
                    difficultyValues.board_size,
                    (int) Math.floor(Math.random()
                            * (difficultyValues.hole_range[1] - difficultyValues.hole_range[0])
                            + difficultyValues.hole_range[0]));
            success = sudokuRandomizer.fillValues(difficultyValues.max_tries);
        }
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

    private boolean checkSolution(int[][] current_board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (current_board[i][j] != solved_board_[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
