/**
 * Created by Iskuskov on 15.02.2016.
 */

package bowling;

public class Game {

    private int m_currentFrame = 0;
    private int m_rollNumberInFrame = 0;
    private int m_remainingPinsInFrame = Scorer.c_maxFrameScore;
    private boolean m_extraAttempt = false;
    private Scorer m_scorer = new Scorer();

    public int currentFrame() { return m_currentFrame; }
    public int remainingPinsInFrame() {return m_remainingPinsInFrame; }

    public int score() {
        return scoreForFrame(m_currentFrame);
    }

    public int scoreForFrame(int _frame) {
        return m_scorer.scoreForFrame(_frame);
    }

    // For testMockedMethod()
    public int scoreForFrame(Scorer scorer, int _frame) {
        return scorer.scoreForFrame(_frame);
    }

    public void add(int _pins) throws IllegalArgumentException {

        if (_pins >= 0 && _pins <= m_remainingPinsInFrame) {
            m_scorer.addRoll(_pins);
            adjustCurrentFrame(_pins);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void adjustCurrentFrame(int _pins) {
        processCurrentFrame(_pins);
        if (isLastBallInFrame()) {
            moveToNextFrame();
        }
    }

    private void processCurrentFrame(int _pins) {
        ++m_rollNumberInFrame;
        m_remainingPinsInFrame -= _pins;
        if (isLastFrame() && m_remainingPinsInFrame == 0) {
            m_remainingPinsInFrame = Scorer.c_maxFrameScore;
            m_extraAttempt = true;
        }
    }

    private void moveToNextFrame() {
        m_currentFrame = Math.min(10, m_currentFrame + 1);
        m_rollNumberInFrame = 0;
        m_remainingPinsInFrame = Scorer.c_maxFrameScore;
    }

    public boolean isStartOfFrame() {
        return m_rollNumberInFrame == 0  /* && m_remainingPinsInFrame = Scorer.c_maxFrameScore */;
    }

    public boolean isLastBallInFrame() {
        if (isLastFrame()) {
            return ((m_rollNumberInFrame == 2 && !m_extraAttempt) || (m_rollNumberInFrame == 3));
        } else {
            return (isStrike() || m_rollNumberInFrame == 2);
        }
    }

    public boolean isGameFinished() {
        return m_currentFrame == 10;
    }

    public int currentRollPositionNumber() {
        return m_currentFrame * 2 + m_rollNumberInFrame;
    }

    private boolean isLastFrame() {
        return m_currentFrame == 9;
    }

    private boolean isStrike() {
        return (m_rollNumberInFrame == 1 && m_remainingPinsInFrame == 0);
    }
}
