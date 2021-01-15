package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.annual;

import lombok.AllArgsConstructor;

/**
 * チェック項目の種類
 */
@AllArgsConstructor
public enum YearCheckItemType {
    // 時間
    TIME(0),
    // 日数
    DAY_NUMBER(3);

    public final int value;
}
