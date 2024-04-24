package Solver;

public class SudokuRandomizer {
    int[] matrix_[];
    int board_size_; // Number of columns/rows
    int SRN_; // Square root of N
    int num_holes_; // Number of missing digits
    int reps; // Number of times to repeat the randomization

    public SudokuRandomizer(int board_size, int num_holes) {
        this.board_size_ = board_size;
        this.num_holes_ = num_holes;

        SRN_ = (int) Math.sqrt(board_size);

        matrix_ = new int[board_size][board_size];
    }

    public void fillValues(int max_tries) {
        while (reps < max_tries && getSolutionNum() != 1) {
            // Reset the board
            resetSudoku();

            // Fill the diagonal of SRN x SRN matrices
            fillDiagonal();

            // Fill remaining blocks
            fillRemaining(0, SRN_);

            // Remove randomly "num_holes_" digits
            removeKDigits();

            reps++;
        }
        if (reps == max_tries) {
            System.out.println("Could not find a solution after " + max_tries + " tries.");
        } else {
            System.out.println("Number of tries: " + reps);
        }
    }

    private void resetSudoku() {
        for (int i = 0; i < board_size_; i++) {
            for (int j = 0; j < board_size_; j++) {
                matrix_[i][j] = 0;
            }
        }
    }

    // Fill the diagonal SRN x SRN matrixes
    private void fillDiagonal() {
        for (int i = 0; i < board_size_; i = i + SRN_)
            fillBox(i, i);
    }

    // Fill a 3 x 3 matrix.
    private void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < SRN_; i++) {
            for (int j = 0; j < SRN_; j++) {
                do {
                    num = randomGenerator(board_size_);
                } while (!isNumInBox(row, col, num));

                matrix_[row + i][col + j] = num;
            }
        }
    }

    // Random generator
    private int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    // Returns false if given 3 x 3 block contains num.
    private boolean isNumInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < SRN_; i++)
            for (int j = 0; j < SRN_; j++)
                if (matrix_[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    // A recursive function to fill remaining matrix
    private boolean fillRemaining(int i, int j) {
        // j reach to the end of the row
        if (j >= board_size_) {
            // Move to next row
            if (i < board_size_) {
                i = i + 1;
                j = 0;
            }

            // Reach the end of the matrix
            else
                return true;
        }

        // Avoid diagonal 3x3 matrixes
        if (i < SRN_) {
            if (j < SRN_)
                j = SRN_;
        } else if (i < board_size_ - SRN_) {
            if (j == (int) (i / SRN_) * SRN_)
                j = j + SRN_;
        } else {
            if (j == board_size_ - SRN_) {
                i = i + 1;
                j = 0;
                if (i >= board_size_)
                    return true;
            }
        }

        for (int num = 1; num <= board_size_; num++) {
            if (CheckIfSafe(i, j, num)) {
                matrix_[i][j] = num;
                if (fillRemaining(i, j + 1))
                    return true;

                matrix_[i][j] = 0;
            }
        }
        return false;
    }

    // Check if safe to put in cell
    private boolean CheckIfSafe(int i, int j, int num) {
        return (isNumInRow(i, num) &&
                isNumInCol(j, num) &&
                isNumInBox(i - i % SRN_, j - j % SRN_, num));
    }

    // Check number is in row i
    private boolean isNumInRow(int i, int num) {
        for (int j = 0; j < board_size_; j++)
            if (matrix_[i][j] == num)
                return false;
        return true;
    }

    // Check number is in col i
    private boolean isNumInCol(int j, int num) {
        for (int i = 0; i < board_size_; i++)
            if (matrix_[i][j] == num)
                return false;
        return true;
    }

    // Remove the digits from "num_holes"
    public void removeKDigits() {
        int count = num_holes_;
        while (count != 0) {
            int cellId = randomGenerator(board_size_ * board_size_) - 1;

            // Get coordinates i and j from ID
            int i = (cellId / board_size_);
            int j = cellId % board_size_;

            if (matrix_[i][j] != 0) {
                // Remove the digit
                count--;
                matrix_[i][j] = 0;
            }
        }
    }

    private int getSolutionNumHelper(int i, int j, int[][] cells, int count) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return 1 + count;
        }
        if (cells[i][j] != 0) // Skip filled cells
            return getSolutionNumHelper(i + 1, j, cells, count);
        // Search for 2 solutions instead of 1
        // Break, if 2 solutions are found
        for (int val = 1; val <= 9 && count <= 2; ++val) {
            if (CheckIfSafe(i, j, val)) {
                cells[i][j] = val;
                // add additional solutions
                count = getSolutionNumHelper(i + 1, j, cells, count);
            }
        }
        cells[i][j] = 0; // Reset on backtrack
        return count;
    }

    public int getSolutionNum() {
        return getSolutionNumHelper(0, 0, matrix_, 0);
    }

    // Print sudoku
    public void printSudoku() {
        for (int i = 0; i < board_size_; i++) {
            for (int j = 0; j < board_size_; j++)
                System.out.print(matrix_[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    // Return the sudoku
    public int[][] getSudoku() {
        return matrix_;
    }

    // Driver code
    public static void main(String[] args) {
        int N = 9, K = 40;
        SudokuRandomizer sudoku = new SudokuRandomizer(N, K);
        sudoku.fillValues(10);
        sudoku.printSudoku();
    }
}