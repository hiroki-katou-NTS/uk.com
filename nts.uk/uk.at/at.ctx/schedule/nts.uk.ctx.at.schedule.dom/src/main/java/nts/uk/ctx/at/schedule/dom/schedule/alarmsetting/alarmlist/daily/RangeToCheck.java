package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import lombok.AllArgsConstructor;

/**
 * チェック対象の範囲
 */
@AllArgsConstructor
public enum RangeToCheck {
    // 全て
    ALL(0),
    // 全て
    CHOICE (1),
    // 選択以外
    OTHER(2);

    public final int value;
}
