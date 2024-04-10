public class SudokuRandomizer {
    public int[][] generateRandomSudoku() {

        int[][] sudoku = new int[9][9];

        // Initialize all cells to 0
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = 0;
            }
        }

        // Randomly fill diagonal sudoku boxes
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for (int i = 0; i < 3; i++) {
            shuffleArray(numbers);
            for (int j = 0; j < 3; j++) {
                sudoku[i * 3][j * 3] = numbers[j * 3];
                sudoku[i * 3 + 1][j * 3 + 1] = numbers[j * 3 + 1];
                sudoku[i * 3 + 2][j * 3 + 2] = numbers[j * 3 + 2];
            }
        }

        // Randomly remove 40-50 entries to create holes
        int holes = (int) (Math.random() * 10) + 40;
        for (int i = 0; i < holes; i++) {
            int row = (int) (Math.random() * 9);
            int col = (int) (Math.random() * 9);
            sudoku[row][col] = 0;
        }

        if (!isValid(sudoku)) {
            return generateRandomSudoku();
        }

        return sudoku;
    }

    // Fisher-Yates shuffle algorithm
    public void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static boolean isValid(int[][] matrix) {
        // Check rows
        for (int i = 0; i < 9; i++) {
            boolean[] nums = new boolean[9];
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j] != 0) {
                    if (nums[matrix[i][j] - 1])
                        return false;
                    nums[matrix[i][j] - 1] = true;
                }
            }
        }

        // Check columns
        for (int j = 0; j < 9; j++) {
            boolean[] nums = new boolean[9];
            for (int i = 0; i < 9; i++) {
                if (matrix[i][j] != 0) {
                    if (nums[matrix[i][j] - 1])
                        return false;
                    nums[matrix[i][j] - 1] = true;
                }
            }
        }

        // Check 3x3 sub-grids
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                boolean[] nums = new boolean[9];
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (matrix[i + k][j + l] != 0) {
                            if (nums[matrix[i + k][j + l] - 1])
                                return false;
                            nums[matrix[i + k][j + l] - 1] = true;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void print_matrix(int[][] matrix) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(matrix[x][y] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SudokuRandomizer randomizer = new SudokuRandomizer();
        print_matrix(randomizer.generateRandomSudoku());
    }
}
