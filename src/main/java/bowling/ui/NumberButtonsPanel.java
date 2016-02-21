package bowling.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Iskuskov on 17.02.2016.
 */
class NumberButtonsPanel extends JPanel {

    private ArrayList<JButton> m_numberButtonList = new ArrayList<JButton>();

    public NumberButtonsPanel(int _minNumber, int _maxNumber, ActionListener _listener, LayoutManager _layout) {
        super(_layout);

        for (int i = _minNumber; i <= _maxNumber; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.addActionListener(_listener);
            add(numberButton, BowlingScorerFormHelper.gbc(i, 0, 1, 1));
            m_numberButtonList.add(numberButton);
        }
    }

    public void disabledButtons(int minEnabledButton) {
        for (JButton numberButton: m_numberButtonList) {
            numberButton.setEnabled(Integer.parseInt(numberButton.getText()) <= minEnabledButton);
        }
    }
}
