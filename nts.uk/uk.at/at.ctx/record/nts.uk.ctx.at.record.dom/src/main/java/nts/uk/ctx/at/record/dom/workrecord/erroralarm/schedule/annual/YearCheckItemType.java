package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import lombok.AllArgsConstructor;

/**
 * チェック項目の種類
 */
@AllArgsConstructor
public enum YearCheckItemType {
    // 時間
    TIME(0, "時間"),
    // 日数
    DAY_NUMBER(1, "日数");

    public final int value;
    public final String nameId;
}
