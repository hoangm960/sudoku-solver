package BoardScreen;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SudokuBoard extends JPanel{
    private static int size = 9;
    private JTextField[][] cells;

    public SudokuBoard() {
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
                cell.addKeyListener(new SudokuListener());
                cells[row][col] = cell;
                add(cell);
            }

            // Decorate the board here
            // TO DO:
            
        }
        setVisible(true);
    }

    // Function to Navigate cells with keyboard
    private class SudokuListener extends KeyAdapter {
        @Override
        public void keyPressed (KeyEvent e) {
            int[] position = findPosition(e);
            
            if (position == null) {
                return;
            }

            int row = position[0];
            int col = position[1];

            // Move focus between cells
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (col > 0)
                        cells[row][col-1].requestFocus();                    
                    break;
                case KeyEvent.VK_RIGHT:
                    if (col < size-1)
                        cells[row][col + 1].requestFocus(); 
                    break;
                case KeyEvent.VK_UP:
                    if (row > 0)
                        cells[row-1][col].requestFocus(); 
                    break;
                case KeyEvent.VK_DOWN:
                    if (row < size-1)
                        cells[row+1][col].requestFocus(); 
                    break;
            }
        }

        // Function to Display valid inputs to cells
        @Override
        public void keyTyped(KeyEvent e) {
            int[] position = findPosition(e);

            if (position == null) {
                return;
            }

            int row = position[0];
            int col = position[1];

            char inputChar = e.getKeyChar();

            // Add the condition to check if the block is initially empty, then input the number
            // else cannot change the existing number in the cell.
            // TO DO

            // Check if the typed character is a digit from 1 to 9
            if (inputChar >= '1' && inputChar <= '9') {
                cells[row][col].setText(Character.toString(inputChar));
            }
            e.consume(); // Consume the event to prevent further processing
        }

        // Function to Find location of the current cell
        public int[] findPosition (KeyEvent e) {
            JTextField source =  (JTextField) e.getSource();
            int[] position = new int[2];

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells.length; j++) {
                    if (cells[i][j] == source) {
                        position[0] = i;
                        position[1] = j;
                        return position;
                    }
                }
            }
            return null;
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuBoard());
    }
}
