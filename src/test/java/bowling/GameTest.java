package bowling;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Iskuskov on 15.02.2016.
 */
public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void testTwoRollsNoMark() throws Exception {
        game.add(5);
        game.add(4);
        assertEquals(9, game.score());
        assertEquals(false, game.isGameFinished());
    }

    @Test
    public void testFourRollsNoMark() throws Exception {
        game.add(5);
        game.add(4);

        game.add(7);
        game.add(2);

        assertEquals(18, game.score());
        assertEquals(9, game.scoreForFrame(1));
        assertEquals(18, game.scoreForFrame(2));
    }

    @Test
    public void testStartNewFrame() throws Exception {
        game.add(5);
        assertEquals(false, game.isStartOfFrame());
        game.add(4);

        assertEquals(true, game.isStartOfFrame());
        game.add(7);
        game.add(2);

        assertEquals(true, game.isStartOfFrame());
        game.add(10);

        assertEquals(true, game.isStartOfFrame());
        game.add(5);
        game.add(3);
    }

    @Test
    public void testSimpleSpare() throws Exception {
        game.add(3);
        game.add(7);

        game.add(3);
        assertEquals(13, game.scoreForFrame(1));
    }

    @Test
    public void testSimpleFrameAfterSpare() throws Exception {
        game.add(3);
        game.add(7);

        game.add(3);
        game.add(2);

        assertEquals(13, game.scoreForFrame(1));
        assertEquals(18, game.scoreForFrame(2));
        assertEquals(18, game.score());
    }

    @Test
    public void testSimpleStrike() throws Exception {
        game.add(10);

        game.add(3);
        game.add(6);
        assertEquals(19, game.scoreForFrame(1));
        assertEquals(28, game.score());
    }

    @Test
    public void testPerfectGame() throws Exception {
        for (int i = 0; i < 12; ++i) {
            game.add(10);
        }
        assertEquals(300, game.score());
        assertEquals(true, game.isGameFinished());
    }

    @Test
    public void testEndOfArray() throws Exception {
        for (int i = 0; i < 9; ++i) {
            game.add(0);
            game.add(0);
        }
        game.add(2);
        game.add(8);    // Spare is in 10th frame
        game.add(10);   // Strike is in the end of array
        assertEquals(20, game.score());
    }

    @Test
    public void testSampleGame() throws Exception {

        game.add(1);
        assertEquals(9, game.remainingPinsInFrame());
        game.add(4);
        assertEquals(1, game.currentFrame());

        assertEquals(10, game.remainingPinsInFrame());
        game.add(4);
        game.add(5);

        game.add(6);
        game.add(4);

        game.add(5);
        game.add(5);
        assertEquals(4, game.currentFrame());

        game.add(10);

        game.add(0);
        game.add(1);
        assertEquals(6, game.currentFrame());

        game.add(7);
        game.add(3);

        game.add(6);
        game.add(4);

        game.add(10);
        assertEquals(9, game.currentFrame());

        game.add(2);
        assertEquals(8, game.remainingPinsInFrame());
        game.add(8);
        assertEquals(10, game.remainingPinsInFrame());
        game.add(6);
        assertEquals(10, game.currentFrame());

        assertEquals(133, game.score());
    }

    @Test
    public void testHeartBreak() throws Exception {
        for (int i = 0; i < 11; ++i) {
            game.add(10);
        }
        assertEquals(10, game.remainingPinsInFrame());
        assertEquals(20, game.currentRollPositionNumber());
        game.add(9);
        assertEquals(299, game.score());
    }

    @Test
    public void testTenthFrameSpare() throws Exception {
        for (int i = 0; i < 9; ++i) {
            game.add(10);
        }
        game.add(9);
        game.add(1);
        game.add(1);
        assertEquals(270, game.score());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalPins() {

        game.add(9);
        game.add(2); // Oops!
    }

    @Test
    public void testMockedMethod() throws Exception {

        game.add(3);
        game.add(7);

        game.add(3);
        game.add(2);

        Scorer scorer = mock(Scorer.class);
        when(scorer.scoreForFrame(1)).thenReturn(13);

        assertEquals(13, game.scoreForFrame(scorer, 1));
        verify(scorer).scoreForFrame(1);
    }

}