package GameScreen.helper;

public class Difficulty {
    public int board_size;
    public int[] hole_range;
    public int max_tries;

    public Difficulty(int board_size, int[] hole_range, int max_tries) {
        this.board_size = board_size;
        this.hole_range = hole_range;
        this.max_tries = max_tries;
    }
}
