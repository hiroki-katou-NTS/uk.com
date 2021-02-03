package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;

/**
 * チェック対象の範囲
 */
@AllArgsConstructor
public enum RangeToCheck {
    // 全て
    ALL(0),
    // 選択
    CHOICE (1),
    // 選択以外
    OTHER(2);

    public final int value;
}
