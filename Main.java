import Controller.Controller;

public class Main {
    Controller controller;

    public Main() {
        controller = new Controller();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.controller.showHome();
    }
}
