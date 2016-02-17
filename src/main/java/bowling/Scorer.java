package bowling;

/**
 * Created by Iskuskov on 15.02.2016.
 */
public class Scorer {

    final public static int c_maxFrameScore = 10;
    final public static int c_maxRollsCount = 21;

    private int m_ball;
    private int m_rolls[] = new int[c_maxRollsCount];
    private int m_currentRoll = 0;

    public void addRoll(int _pins) {
        m_rolls[m_currentRoll++] = _pins;
    }

    public int scoreForFrame(int _frame) {
        m_ball = 0;
        int score = 0;
        for (int currentFrameIx = 0; currentFrameIx < _frame; currentFrameIx++) {
            // 1. Strike
            if (isStrike()) {
                score += c_maxFrameScore + nextTwoBallsForStrike();
                m_ball++;
            // 2. Spare
            } else if (isSpare()) {
                score += c_maxFrameScore + nextBallForSpare();
                m_ball += 2;
            // 3. Simple
            } else {
                score += twoBallsInFrame();
                m_ball += 2;
            }
        }
        return score;
    }

    private int nextTwoBallsForStrike() {
        return m_rolls[m_ball + 1] + m_rolls[m_ball + 2];
    }

    private int nextBallForSpare() {
        return m_rolls[m_ball + 2];
    }

    private int twoBallsInFrame() {
        return m_rolls[m_ball] + m_rolls[m_ball + 1];
    }

    private boolean isStrike() {
        return m_rolls[m_ball] == c_maxFrameScore;
    }

    private boolean isSpare() {
        return (m_rolls[m_ball] + m_rolls[m_ball + 1]) == c_maxFrameScore;
    }
}
