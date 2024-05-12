package Controller;
import javax.swing.*;
import GameScreen.SudokuFrame;
import HomePage.HomeScreen;

public class Controller extends JFrame {
    HomeScreen homeFrame;
    SudokuFrame gameFrame;

    public Controller() {
        homeFrame = new HomeScreen(this);
        gameFrame = new SudokuFrame(this);
        homeFrame.setVisible(true);
        gameFrame.setVisible(false);
    }

    public void showHome() {
        homeFrame.setVisible(true);
        gameFrame.setVisible(false);
    }

    public void playGame() {
        gameFrame.setVisible(true);
        homeFrame.setVisible(false);
    }
}