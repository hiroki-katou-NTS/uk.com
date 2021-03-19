package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;

/**
 * 週次のチェック項目の種類
 */
@AllArgsConstructor
public enum WeeklyCheckItemType {
    // 時間
    TIME(1, "時間"),
    // 日数
    DAY_NUMBER(2, "日数"),
    // 回数
    TIMES(3, "回数"),
    // 連続時間
    CONTINUOUS_TIME(4, "連続時間"),
    // 連続日数
    CONTINUOUS_DAY(5, "連続日数"),
    // 連続回数
    CONTINUOUS_TIMES(6, "連続回数");

    public final int value;
    public final String nameId;
}
