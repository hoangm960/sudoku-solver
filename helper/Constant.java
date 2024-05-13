package helper;

import java.io.File;

public class Constant {
    private static String logo_link_ = "../assets/Sudoku_logo.png";
    private static String music_link_ = "assets/background_music.wav";

    public static String getLogoLink() {
        return logo_link_;
    }

    public static File getMusicLink() {
        String[] path_split = music_link_.split("/");
        String parrent_path = path_split[0];
        String file_name = path_split[1];
        return new File(parrent_path, file_name);
    }
}
