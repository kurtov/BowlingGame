package bowling.ui;

import java.awt.*;

/**
 * Created by Iskuskov on 17.02.2016.
 */
class BowlingScorerFormHelper {
    static public GridBagConstraints gbc(int _x, int _y, int _colspan, int _rowspan) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = _x;
        gbc.gridy = _y;
        gbc.gridwidth = _colspan;
        gbc.gridheight = _rowspan;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        return gbc;
    }
}
