import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Final extends JFrame {

    public Final() {

        setTitle("meomeomeo");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Insert image
        ImageIcon originalIcon = new ImageIcon("logoooo.png"); 
        
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();
        
        int desiredWidth = 500; // Change this to your desired width
        int desiredHeight = 500; // Change this to your desired height
        
        double scaleWidth = (double) desiredWidth / originalWidth;
        double scaleHeight = (double) desiredHeight / originalHeight;
        
        int scaledWidth = (int) (originalWidth * Math.min(scaleWidth, scaleHeight));
        int scaledHeight = (int) (originalHeight * Math.min(scaleWidth, scaleHeight));
        
        Image image = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        
        JLabel imageLabel = new JLabel(scaledIcon);
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(imageLabel);

        // Create three buttons
        JButton button1 = new JButton("Easy");
        JButton button2 = new JButton("Medium");
        JButton button3 = new JButton("Hard");

        Dimension buttonSize = new Dimension(200, 50); // thay width and height of buttons 
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        Font buttonFont = new Font("Monospaced", Font.BOLD, 20); // thay font voi size chu trong button 
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        button3.setFont(buttonFont);

        // action cho button1: Easy
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "easy peasy :D");
            }
        });

        // action cho button2: Medium
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "adu kho z :(");
            }
        });

        // action cho button3: Hard
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "kho qua bo qua TvT");
            }
        });

        // panel cho buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1000; 
        gbc.insets = new Insets(10, 0, 10, 0); // vertical spacing giua buttons
        buttonPanel.add(button1, gbc);
        gbc.gridy++;
        buttonPanel.add(button2, gbc);
        gbc.gridy++;
        buttonPanel.add(button3, gbc);

        // Add components to the frame
        getContentPane().add(imagePanel, BorderLayout.CENTER); // Add image in the center
        getContentPane().add(buttonPanel, BorderLayout.SOUTH); // Add buttons at the bottom

        setVisible(true);
    }

    public static void main(String[] args) {
        Final frame = new Final();
    }
}
