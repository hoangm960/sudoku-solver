import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.sound.sampled.*;

public class Official_SudokuHomeScreen extends JFrame {
    //private boolean is_musicPlaying = false;
    //private JButton volume_button;

    public Official_SudokuHomeScreen() {
        setTitle("Sudoku_CS2-Spring2024");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 800);
        setLocationRelativeTo(null);

        // Set the icon logo
        ImageIcon image = new ImageIcon("C:\\Users\\hoang\\Downloads\\Soduku\\Sudoku_logo.png");
        setIconImage(image.getImage());

        // Set label "CS2_Spring 2024"
        JLabel label = new JLabel();
        label.setText("Hoang Nhat Minh, Nguyen Le Na, Le Thi Que My, Nguyen Hoang Thang. CS2_2024");
        label.setFont(new Font("Garamond", Font.BOLD, 16));
        label.setForeground(new Color(0x00000));

        // Add label to content pane
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(label);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBackground(new Color(0xfefefe));;

        // Create a gradient panel with colors #00c16e and #037ef3 from left to right
        GradientPanel gradientPanel = new GradientPanel("#b6cdce", "#6d8fa4");
        add(gradientPanel, BorderLayout.CENTER);

        // Play background music
        playBackgroundMusic("C:\\Users\\hoang\\Downloads\\Soduku\\background_music.wav");

        // Create a button
        // button PlayGame
        JButton playGame_button = new JButton("PLAY GAME!");
        playGame_button.setBackground(new Color(0xe4e0e7)); // Set background color
        playGame_button.setFont(new Font("Garamond", Font.BOLD, 30));
        playGame_button.setForeground(Color.BLACK);
        playGame_button.setPreferredSize(new Dimension(200, 50)); // Set preferred size for the button

        // Add button to the gradient panel
        gradientPanel.setLayout(null); // Set layout to null for absolute positioning
        playGame_button.setBounds(280, 340, 250, 50); // Set button position and size
        gradientPanel.add(playGame_button); // Add button to the gradient panel

        //buttonGuideline
        JButton guideline_button = new JButton("GUIDELINE");
        guideline_button.setBackground(new Color(0xe4e0e7)); // Set background color
        guideline_button.setFont(new Font("Garamond", Font.BOLD, 30));
        guideline_button.setForeground(Color.BLACK);
        guideline_button.setPreferredSize(new Dimension(200, 50)); // Set preferred size for the button

        guideline_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a new tab with credits
                open_Guideline();
            }
        });

        gradientPanel.setLayout(null); // Set layout to null for absolute positioning
        guideline_button.setBounds(280, 410, 250, 50); // Set button position and size
        gradientPanel.add(guideline_button); // Add button to the gradient panel

        //button Credits
        JButton credit_button = new JButton("CREDITS");
        credit_button.setBackground(new Color(0xe4e0e7)); // Set background color
        credit_button.setFont(new Font("Garamond", Font.BOLD, 30));
        credit_button.setForeground(Color.BLACK);
        credit_button.setPreferredSize(new Dimension(200, 50)); // Set preferred size for the button

        credit_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a new tab with credits
                open_Credits();
            }
        });
        

        gradientPanel.setLayout(null); // Set layout to null for absolute positioning
        credit_button.setBounds(280, 480, 250, 50); // Set button position and size
        gradientPanel.add(credit_button); // Add button to the gradient panel


        //create a button to adjust the volume
        // JButton volume_button = new JButton("VOLUME: ON");
        // volume_button.setBackground(new Color(0xe4e0e7));
        // volume_button.setFont(new Font("Garamond", Font.BOLD, 30));
        // volume_button.setForeground(Color.BLACK);
        // volume_button.setPreferredSize(new Dimension(200, 50));

        // volume_button.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         on_volume();
        //     }
        // });

        // gradientPanel.setLayout(null);
        // volume_button.setBounds(280, 550, 250, 50);
        // gradientPanel.add(volume_button);    
    }

    // Function to toggle background music
    // private void on_volume() {
    //     if (is_musicPlaying) {
    //         off_volume(); // Nếu nhạc đang chạy, tắt nó
    //         volume_button.setText("VOLUME: OFF"); // Thay đổi văn bản của nút thành "Volume: Off"
    //     } else {
    //         playBackgroundMusic("C:\\Users\\hoang\\Downloads\\Soduku\\background_music.wav"); // Nếu nhạc đang tắt, bật nó
    //         volume_button.setText("VOLUME: ON"); // Thay đổi văn bản của nút thành "Volume: On"
    //     }
    //     is_musicPlaying = !is_musicPlaying; // Đảo ngược trạng thái của biến cờ
    // }

    // private void off_volume() {
    //     try {
    //         if (clip != null && clip.isOpen()) {
    //             clip.stop(); // Dừng phát nhạc
    //             clip.close(); // Đóng Clip
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    private void open_Guideline() {
        // Create a new tab with credits
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body style='font-family: Garamond; font-size: 14px;'>"
                + "<p>The game is quite simple. </p>"
                + "<p> You just need to fill each row, column, and 3x3 grid with numbers 1 to 9 without repeating any numbers.</p>"
                + "<p>To see the full guideline, please visit our project report <a href=\"https://docs.google.com/document/d/1QOP7-7k8l8jexaKQLv5DWb1PAeppzOvX8EcdD1PB5Y8/edit?usp=sharing\">HERE</a></p>"
                + "</body></html>");
    
        editorPane.setEditable(false);
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    // Open the hyperlink in the default browser
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    
        JScrollPane scrollPane = new JScrollPane(editorPane);
    
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Guideline", scrollPane);
    
        JFrame creditsFrame = new JFrame("Guideline");
        creditsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        creditsFrame.add(tabbedPane);
        creditsFrame.setSize(400, 300);
        creditsFrame.setLocationRelativeTo(null);
        creditsFrame.setVisible(true);
    }
    
    
    private void open_Credits() {
    // Create a new tab with credits
    JEditorPane editorPane = new JEditorPane();
    editorPane.setContentType("text/html");
    editorPane.setText("<html><body style='font-family: Garamond; font-size: 14px;'>"
            + "<p>Credits go to Hoang Nhat Minh, Le Thi Que My, Nguyen Le Na, and Nguyen Hoang Thang.</p>"
            + "<p>This is the project from the course Computer Science II: Data Structure and Algorithm (Professor Linh Huynh).</p>"
            + "<p>The project is inspired from Homework 4: Sudoku.</p>"
            + "<p>To see more information, please visit our project report <a href=\"https://docs.google.com/document/d/1QOP7-7k8l8jexaKQLv5DWb1PAeppzOvX8EcdD1PB5Y8/edit?usp=sharing\">HERE</a></p>"
            + "</body></html>");

    editorPane.setEditable(false);
    editorPane.addHyperlinkListener(new HyperlinkListener() {
        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                // Open the hyperlink in the default browser
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    });

    JScrollPane scrollPane = new JScrollPane(editorPane);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Credits", scrollPane);

    JFrame creditsFrame = new JFrame("Credits");
    creditsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    creditsFrame.add(tabbedPane);
    creditsFrame.setSize(400, 300);
    creditsFrame.setLocationRelativeTo(null);
    creditsFrame.setVisible(true);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Official_SudokuHomeScreen frame = new Official_SudokuHomeScreen();
            //frame.pack();
            frame.setVisible(true);
        });
    }

    // Music function
    public void playBackgroundMusic(String filename) {
        try {
            File audioFile = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(audioStream);
            clip.loop(0);
            // clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Start playing
            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}

class GradientPanel extends JPanel {
    private Color color1;
    private Color color2;

    public GradientPanel(String hexColor1, String hexColor2) {
        // Parse hexadecimal color strings and convert them to Color objects
        this.color1 = Color.decode(hexColor1);
        this.color2 = Color.decode(hexColor2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast Graphics to Graphics2D
        Graphics2D g2d = (Graphics2D) g;

        // Define the gradient start and end points
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradient = new GradientPaint(0, 0, color1, width, 0, color2);

        // Apply the gradient
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(0x102944));
        g2d.fillRect(200, 200, 400, 120);

        // Draw the text "Sudoku"
        g2d.setColor(Color.WHITE);
        Font font = new Font("Garamond", Font.BOLD, 40);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        String text = "SUDOKU";
        int x = 305 + (200 - metrics.stringWidth(text)) / 2; // Center the text horizontally
        int y = 200 + ((120 - metrics.getHeight()) / 2) + metrics.getAscent(); // Center the text vertically
        g2d.drawString(text, x, y);

        // Increase the thickness of the outline
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3)); // Set the thickness to 3 pixels
        g2d.setColor(new Color(0xFFFFFF));
        g2d.drawRect(200, 200, 400, 120); // Draw the outline of the rectangle
        g2d.setStroke(oldStroke); // Restore the old stroke
    }
}
