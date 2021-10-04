package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.AllArgsConstructor;

/**
 * 時間項目の入力単位
 */
@AllArgsConstructor
public enum TimeItemInputUnit {
    //  0:1分
    ONE_MINUTE(0),

    //  1:5分
    FIVE_MINUTES(1),

    //  2:10分
    TEN_MINUTES(2),

    //  3:15分
    FIFTEEN_MINUTES(3),

    //  4:30分
    THIRTY_MINUTES(4),

    //  4:60分
    SIXTY_MINUTES(5);

    public int value;
}
