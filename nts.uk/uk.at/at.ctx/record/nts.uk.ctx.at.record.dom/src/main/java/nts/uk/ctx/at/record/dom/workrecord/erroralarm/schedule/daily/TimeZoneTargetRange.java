package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;

/**
 * 時間帯チェック対象の範囲
 */
@AllArgsConstructor
public enum TimeZoneTargetRange {
    // 選択
    CHOICE (0, "選択"),
    // 選択以外
    OTHER(1, "選択以外");

    public final int value;
    public final String nameId;
}
