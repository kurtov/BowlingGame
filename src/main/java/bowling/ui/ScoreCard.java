package bowling.ui;

import bowling.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Iskuskov on 17.02.2016.
 */
public class ScoreCard extends JPanel implements ActionListener {

    private ArrayList<PlayerCard> m_playerCardList = new ArrayList<PlayerCard>();
    private int m_currentPlayerCardIndex = 0;
    NumberButtonsPanel m_numberButtonsPanel;

    public ScoreCard(ArrayList<String> players) {
        super(new GridBagLayout());

        JPanel scorecardHeader = createScorecardHeader(getLayout());
        //add(scorecardHeader, BowlingScorerFormHelper.gbc(0, 0, 1, 1));

        for (int playerIx = 0; playerIx < players.size(); ++playerIx) {
            PlayerCard playerCard = new PlayerCard(players.get(playerIx), getLayout());
            add(playerCard, BowlingScorerFormHelper.gbc(0, playerIx + 1, 1, 1));
            m_playerCardList.add(playerCard);
        }

        m_numberButtonsPanel = new NumberButtonsPanel(0, 10, this, getLayout());
        add(m_numberButtonsPanel, BowlingScorerFormHelper.gbc(0, players.size() + 1, 1, 1));
    }

    private JPanel createScorecardHeader(LayoutManager _layoutManager) {

        JPanel scorecardHeader = new JPanel(_layoutManager);

        scorecardHeader.add(new JLabel("Player"), BowlingScorerFormHelper.gbc(0, 0, 1, 1));
        for (int x = 0; x <= 10; x++) {
            scorecardHeader.add(new JLabel(Integer.toString(x)), BowlingScorerFormHelper.gbc(x, 0, 1, 1));
        }
        scorecardHeader.add(new JLabel("Total"), BowlingScorerFormHelper.gbc(11, 0, 1, 1));
        return scorecardHeader;
    }

    public void actionPerformed(ActionEvent e) {

        // Проверяем, не завершена ли игра
        if (isGameFinished()) {
            endGame();
            return;
        }

        PlayerCard currentPlayerCard = m_playerCardList.get(m_currentPlayerCardIndex);
        Game currentGame = currentPlayerCard.game();

        // Устанавливаем пины
        String pinsString = e.getActionCommand();
        int currentRollPositionNumber = currentGame.currentRollPositionNumber();
        currentPlayerCard.rollFieldList().get(currentRollPositionNumber).setText(pinsString);

        // Скрываем лишние кнопки
        disableInvalidPinButtons(currentGame.remainingPinsInFrame());

        // Переход к следующему игроку или конец игры
        if (currentGame.isStartOfFrame()) {
            if (isGameFinished()) {
                endGame();
            } else {
                moveToNextPlayer();
            }
        }
    }

    private void disableInvalidPinButtons(int _minEnabledButton) {
        m_numberButtonsPanel.disabledButtons(_minEnabledButton);
    }

    private boolean isGameFinished() {
        return m_currentPlayerCardIndex == m_playerCardList.size() - 1 &&
                m_playerCardList.get(m_currentPlayerCardIndex).game().isGameFinished();
    }

    private void endGame() {
        JOptionPane.showMessageDialog(null, "Спасибо за игру!", "Игра окончена", JOptionPane.INFORMATION_MESSAGE);
    }

    private void moveToNextPlayer() {
        m_currentPlayerCardIndex = (m_currentPlayerCardIndex + 1) % m_playerCardList.size();
    }
}
