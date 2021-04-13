package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;

/**
 * チェック対象の範囲
 */
@AllArgsConstructor
public enum RangeToCheck {
    // 全て
    ALL(0, "全て"),
    // 選択
    CHOICE (1, "選択"),
    // 選択以外
    OTHER(2, "選択以外");

    public final int value;
    public final String nameId;
}
