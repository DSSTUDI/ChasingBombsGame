import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

/**
 * Creates a game in which the player's goal is to reveal as much panels as they can 
 * without revealing the one with the bomb.
 * 
 *
 * @author Dominykas Sliuzas
 * @version April-2020
 */
public class Game extends JFrame
{
    private JFrame frame = new JFrame("Evade the bomb");
    private JPanel main = new JPanel(new GridLayout(1, 1));
    private JPanel left = new JPanel(new GridLayout(2, 5));
    private JPanel center = new JPanel();
    private JPanel right = new JPanel();
    private JButton [] panel = new JButton[10];

    private JButton play = new JButton("Play the game");
    private JButton exit = new JButton("Exit");
    private JButton easy = new JButton("Easy");
    private JButton medium = new JButton("Medium");
    private JButton hard = new JButton("Hard");

    private JLabel message = new JLabel("");

    private String dif;
    private int pointsToWin;
    private int points;
    private boolean inProgress = false;
    private boolean canClick = false;
    private int bombIndex;
    private ArrayList<Integer> panelsClicked = new ArrayList();

    /**
     * Creates the JFrame in which the game is contained.
     */
    public Game()
    {
        frame.setSize(1500, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeFrame();
    }

    /**
     * Adds all the elements of the game into the JFrame.
     */
    public void makeFrame()
    {
        left.setBackground(Color.RED);
        center.setBackground(Color.BLUE);
        right.setBackground(Color.GREEN);

        message.setForeground(Color.white);

        for (int c = 0; c < 10; c++) {
            final int index = c;
            panel[c] = new JButton();
            panel[c].setBackground(Color.RED);
            panel[c].setOpaque(true);
            panel[c].setBorder(BorderFactory.createLineBorder(Color.white));
            panel[c].putClientProperty("index", c);
            left.add(panel[c]);
            panel[c].addActionListener(e -> panelClicked(index));
        }

        center.add(play);
        center.add(exit);
        center.add(message);

        right.add(easy);
        right.add(medium);
        right.add(hard);

        main.add(left);
        main.add(center);
        main.add(right);

        frame.add(main);

        frame.setContentPane(main);
        frame.pack();

        frame.setVisible(true);

        easy.addActionListener(e -> setEasy());
        medium.addActionListener(e -> setMedium());
        hard.addActionListener(e -> setHard());
        play.addActionListener(e -> play());
        exit.addActionListener(e -> exit());

    }

    /**
     * Sets game difficulty to easy.
     */
    public void setEasy() 
    {   
        if (!inProgress) {
            dif = "EASY"; 
        }
    }

    /**
     * Sets game difficulty to medium.
     */
    public void setMedium() 
    {
        if (!inProgress) {
            dif = "MEDIUM"; 
        }
    }

    /**
     * Sets game difficulty to hard.
     */
    public void setHard() 
    {
        if (!inProgress) {
            dif = "HARD"; 
        }
    }

    /**
     * Checks if a difficulty is selected, assigns the bomb to a random panel
     * and moves the game into the "inProgress" state.
     */
    public void play()
    {
        if (inProgress) {
            reset();
            inProgress = false;
        }
        else {
            if (dif == null) {
                message.setText("Please select difficulty");
            }
            else {
                inProgress = true;
                canClick = true;
                message.setText("");
                play.setText("Reset");
                Random rand = new Random();
                bombIndex = rand.nextInt(10);
                switch (dif) {
                    case "EASY":
                        pointsToWin = 5;
                        break;
                    case "MEDIUM":
                        pointsToWin = 7;
                        break;
                    case "HARD":
                        pointsToWin = 9;
                        break;
                }
            }
        }
        
    }

    /**
     * Checks the win and loss conditions of the game and outputs the respective message
     * if neccessary. If game was not lost it increments the points value.
     */
    public void panelClicked(int index) 
    {
        if (inProgress && !panelsClicked.contains(index) && canClick) {
            if (index == bombIndex) {
                message.setText("Game Over! Points: " + points);
                panel[index].setBackground(Color.BLACK);
                canClick = false;
            }
            else {
                panel[index].setBackground(Color.YELLOW);
                panelsClicked.add(index);
                points++;
                if (points == pointsToWin) {
                    message.setText("You Win! Points: " + points);
                    canClick = false;
                }
            }
        }
    }

    /**
     * Resets the entire state of the game, including the selected difficulty.
     */
    public void reset() 
    {
        for (int c = 0; c < 10; c++) {
            panel[c].setBackground(Color.RED);
        }
        play.setText("Play the game");
        message.setText("");
        points = 0;
        panelsClicked.clear();
        dif = null;
    }
    
    /**
     * Quits the game.
     */
    public void exit() 
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

}