import BoardScreen.SudokuBoard;
import Solver.SudokuRandomizer;
import Solver.SudokuSolver;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

class Difficulty {
    public int board_size;
    public int[] hole_range;
    public int max_tries;

    public Difficulty(int board_size, int[] hole_range, int max_tries) {
        this.board_size = board_size;
        this.hole_range = hole_range;
        this.max_tries = max_tries;
    }
}

class Map {
    private String[] _key_set;
    private Difficulty[] _value_arr;
    private int _size;

    public Map(int max_size) {
        this._key_set = new String[max_size];

        this._value_arr = new Difficulty[max_size];
        this._size = 0;
    }

    public void put(String key, Difficulty value) {
        if (!containsKey(key)) {
            _key_set[_size] = key;
            _value_arr[_size] = value;
            _size++;
        } else {
            update(key, value);
        }
    }

    public Difficulty get(String key) {
        for (int i = 0; i < _size; i++) {
            if (_key_set[i].equals(key)) {
                return _value_arr[i];
            }
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public void update(String key, Difficulty value) {
        for (int i = 0; i < _size; i++) {
            if (_key_set[i].equals(key)) {
                _value_arr[i] = value;
            }
        }
    }
}

public class SudokuFrame extends JFrame {
    private Timer timer;
    private JLabel timerLabel;
    private static int[][] unsolved_board_;
    private static int[][] solved_board_;
    private static Map difficulties = new Map(3);
    private boolean difficulty_level = false;
    //TODO: change this variable after difficulty chosen
    private static String difficulty_ = "easy"; // default

    public SudokuFrame() {
        setTitle("Sudoku - Game On!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(800, 820);
        setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("C:\\Users\\hoang\\Downloads\\Soduku\\Sudoku_logo.png");
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
        //GradientPanel gradientPanel = new GradientPanel("#00c16e", "#037ef3");
        boardPanel.setPreferredSize(new Dimension(600, 600));
        holder.add(boardPanel, BorderLayout.CENTER);
        add(holder, BorderLayout.CENTER);
        //add(gradientPanel, BorderLayout.CENTER);

        // Create an instance of the SudokuBoard class <- visualise the board with this
        // class
        // SudokuBoard displayBoard = new SudokuBoard(unsolved_board_);
        SudokuBoard displayBoard = new SudokuBoard(new int[9][9]);

        // Add instance to the board panel
        boardPanel.add(displayBoard, BorderLayout.CENTER);
        
        // Create a panel for the buttons and timer
        //Panel buttonAndTimerPanel = new JPanel(new BorderLayout());
        
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
                String[] options = {"Easy", "Medium", "Hard"};
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
                        int false_option = JOptionPane.showOptionDialog(null, "đồ ngu ahihi, chúc bạn may mắn lần sau :p\n",
                            "Result", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Answer", "Cancel"}, "Answer");
                        //playBackgroundMusic("C:\\Users\\hoang\\Downloads\\Soduku\\new_backgroundsudoku.wav");
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
                JOptionPane gu_confirmDialog = new JOptionPane("Do you dare to give up? Chúng tôi còn không give up CS2 mà bạn give up game này",
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

    public static void main(String[] args) {
        fillDifficulties(200);
        renewBoard(difficulty_);

        // Sudoku frame driver
        SwingUtilities.invokeLater(SudokuFrame::new);
    }

   
}
