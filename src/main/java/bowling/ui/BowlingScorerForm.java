package bowling.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Iskuskov on 15.02.2016.
 */

public class BowlingScorerForm
{
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Bowling Scorer");
        ArrayList<String> players = new ArrayList<String>();
        players.add("Max");
        players.add("Alex");
        //players.add("John");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScoreCard scoreCard = new ScoreCard(players);
        frame.getContentPane().add(scoreCard, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}