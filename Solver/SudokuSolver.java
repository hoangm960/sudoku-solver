package Solver;

public class SudokuSolver {
    public int[] find_possible_values(int[][] matrix, int x, int y) {
        int[] all_values = new int[9];
        for (int i = 0; i < 9; i++) {
            all_values[i] = 1;
        }

        for (int i = 0; i < 9; i++) {
            if (matrix[x][i] != 0) {
                all_values[matrix[x][i] - 1] = 0;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (matrix[i][y] != 0) {
                all_values[matrix[i][y] - 1] = 0;
            }
        }
        int x0 = (x / 3) * 3;
        int y0 = (y / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[x0 + i][y0 + j] != 0) {
                    all_values[matrix[x0 + i][y0 + j] - 1] = 0;
                }
            }
        }

        return convert_possible_values(all_values);
    }

    private int[] convert_possible_values(int[] all_values) {
        int i = 0;
        int[] possible_values = new int[9];
        for (int j = 0; j < 9; j++) {
            if (all_values[j] == 1) {
                possible_values[i] = j + 1;
                i++;
            }
        }
        return remove_trailing_zeros(possible_values);
    }

    private int[] remove_trailing_zeros(int[] possible_values) {
        int i = 8;
        while (i >= 0 && possible_values[i] == 0) {
            i--;
        }
        int[] new_possible_values = new int[i + 1];
        for (int j = 0; j <= i; j++) {
            new_possible_values[j] = possible_values[j];
        }
        return new_possible_values;
    }

    public void solve(int[][] matrix) {
        if (isSolved(matrix)) {
            return;
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (matrix[x][y] == 0) {
                    int[] possibleValues = find_possible_values(matrix, x, y);

                    for (int value : possibleValues) {
                        matrix[x][y] = value;

                        solve(matrix);
                        if (isSolved(matrix)) {
                            return;
                        }

                        matrix[x][y] = 0;
                    }

                    return;
                }
            }
        }
    }

    private boolean isSolved(int[][] matrix) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (matrix[x][y] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printSudoku(int[][] matrix) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(matrix[x][y] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer(9, 20);
        sudokuRandomizer.fillValues();
        int[][] sudoku = sudokuRandomizer.getSudoku();
        sudokuRandomizer.printSudoku();
        System.out.println();

        SudokuSolver sudokuSolver = new SudokuSolver();
        sudokuSolver.solve(sudoku);
        sudokuSolver.printSudoku(sudoku);
    }
}