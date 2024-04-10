public class SudokuSolver {
    public static int[] find_possible_values(int[][] matrix, int x, int y) {
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

    private static int[] convert_possible_values(int[] all_values) {
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

    private static int[] remove_trailing_zeros(int[] possible_values) {
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

    public static void solve(int[][] matrix) {
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

    private static boolean isSolved(int[][] matrix) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (matrix[x][y] == 0) {
                    return false;
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
        // int[][] matrix_1 = {
        // { 0, 5, 0, 0, 0, 2, 0, 0, 6 },
        // { 0, 8, 0, 0, 0, 6, 7, 0, 3 },
        // { 0, 0, 0, 7, 4, 0, 0, 0, 8 },
        // { 6, 7, 0, 9, 8, 0, 0, 0, 0 },
        // { 0, 3, 1, 0, 5, 0, 6, 0, 9 },
        // { 0, 0, 0, 0, 0, 3, 8, 2, 0 },
        // { 0, 0, 0, 0, 0, 0, 1, 8, 0 },
        // { 0, 0, 0, 3, 0, 0, 0, 0, 2 },
        // { 9, 2, 0, 5, 0, 0, 0, 7, 0 }
        // };
        // Sudoku_hw4.find_possible_values(matrix_1, 0, 0); // returns [1, 3, 4, 7]
        // Sudoku_hw4.find_possible_values(matrix_1, 3, 5); // returns [1, 4]
        // Sudoku_hw4.find_possible_values(matrix_1, 6, 3); // returns [2, 4, 6]
        // Sudoku_hw4.solve(matrix_1); // after this call, matrix_1 is the matrix below
        // 1 5 7 8 3 2 4 9 6
        // 2 8 4 1 9 6 7 5 3
        // 3 9 6 7 4 5 2 1 8
        // 6 7 2 9 8 4 5 3 1
        // 8 3 1 2 5 7 6 4 9
        // 5 4 9 6 1 3 8 2 7
        // 7 6 3 4 2 9 1 8 5
        // 4 1 5 3 7 8 9 6 2
        // 9 2 8 5 6 1 3 7 4
        //
        // int[][] matrix_10 = {
        //         { 5, 3, 0, 0, 8, 0, 0, 0, 0 },
        //         { 0, 0, 0, 0, 0, 0, 0, 2, 0 },
        //         { 0, 0, 6, 9, 0, 0, 5, 0, 7 },
        //         { 0, 0, 7, 5, 0, 0, 4, 0, 9 },
        //         { 0, 0, 0, 0, 0, 0, 0, 0, 1 },
        //         { 6, 0, 0, 0, 0, 7, 0, 0, 0 },
        //         { 0, 0, 4, 0, 0, 0, 0, 1, 0 },
        //         { 8, 0, 0, 0, 9, 0, 2, 0, 4 },
        //         { 0, 0, 0, 2, 0, 0, 0, 6, 0 }
        // };
        SudokuRandomizer sudokuRandomizer = new SudokuRandomizer();
        int[][] sudoku = sudokuRandomizer.generateRandomSudoku();
        print_matrix(sudoku);
        System.out.println();
        SudokuSolver.solve(sudoku); // after this call, matrix_10 is the matrix below
        print_matrix(sudoku);
        // 5 3 2 7 8 4 1 9 6
        // 7 4 9 1 5 6 8 2 3
        // 1 8 6 9 2 3 5 4 7
        // 3 1 7 5 6 2 4 8 9
        // 4 2 5 8 3 9 6 7 1
        // 6 9 8 4 1 7 3 5 2
        // 2 6 4 3 7 8 9 1 5
        // 8 7 1 6 9 5 2 3 4
        // 9 5 3 2 4 1 7 6 8
    }
}