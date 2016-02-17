package bowling.ui;

import bowling.Game;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Iskuskov on 17.02.2016.
 */
class PlayerCard extends JPanel implements DocumentListener {

    private ArrayList<JTextField> m_rollFieldList = new ArrayList<JTextField>();
    private ArrayList<JLabel> m_frameLabelList = new ArrayList<JLabel>();
    private JTextField m_totalField = new JTextField(5);
    private Game m_game = new Game();

    public ArrayList<JTextField> rollFieldList() { return m_rollFieldList; }
    public Game game() { return m_game; }

    public PlayerCard(String _playerName, LayoutManager _layoutManager) {
        super(_layoutManager);

        Font largeBoldFont = new Font("Verdana", Font.BOLD, 18);

        // Player field
        JTextField playerField = new JTextField(5);
        playerField.setText(_playerName);
        playerField.setFont(largeBoldFont);
        playerField.setHorizontalAlignment(JTextField.CENTER);
        add(playerField, BowlingScorerFormHelper.gbc(0, 0, 1, 1));

        // Frame fields
        for (int i = 1; i <= 9; i++) {
            add(createFrame(2), BowlingScorerFormHelper.gbc(i, 0, 1, 1));
        }
        add(createFrame(3), BowlingScorerFormHelper.gbc(10, 0, 1, 1));

        // Total field
        m_totalField.setBackground(Color.YELLOW);
        m_totalField.setEditable(false);
        m_totalField.setFont(largeBoldFont);
        m_totalField.setHorizontalAlignment(JTextField.CENTER);
        m_totalField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(m_totalField, BowlingScorerFormHelper.gbc(11, 0, 1, 1));
    }

    private JPanel createFrame(int _entries) {

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        for (int i = 0; i < _entries; i++) {
            JTextField rollField = new JTextField(3);
            rollField.setHorizontalAlignment(JTextField.CENTER);
            rollField.setText("-");
            rollField.setEditable(false);
            p.add(rollField, BowlingScorerFormHelper.gbc(i, 0, 1, 1));
            rollField.getDocument().addDocumentListener(this);
            m_rollFieldList.add(rollField);
        }

        JLabel frameLabel = new JLabel(" ");
        frameLabel.setBackground(Color.GRAY);
        p.add(frameLabel, BowlingScorerFormHelper.gbc(0, 1, _entries, 1));
        m_frameLabelList.add(frameLabel);
        return p;
    }

    public void insertUpdate(DocumentEvent _event) {
        int pins = 0;
        try {
            final Document document = _event.getDocument();
            pins = Integer.parseInt(document.getText(0, document.getLength()));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        updateScore(pins);
    }

    public void removeUpdate(DocumentEvent _event) {
    }

    public void changedUpdate(DocumentEvent _event) {
    }

    private void updateScore(int _pins) {
        // Add score
        m_game.add(_pins);

        // Output scores
        for (int i = 1; i <= m_game.currentFrame(); ++i) {
            m_frameLabelList.get(i - 1).setText(String.valueOf(m_game.scoreForFrame(i)));
        }
        m_totalField.setText(String.valueOf(m_game.score()));
    }
}
