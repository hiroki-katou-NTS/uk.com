package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;

/**
 * 週次のチェック項目の種類
 */
@AllArgsConstructor
public enum WeeklyCheckItemType {
    // アラーム
    Alarm(0),
    // 時間
    TIME(1),
    // 日数
    DAY_NUMBER(2),
    // 回数
    TIMES(3),
    // 連続時間
    CONTINUOUS_TIME(4),
    // 連続時間
    CONTINUOUS_DAY(5),
    // 連続回数
    CONTINUOUS_TIMES(6);

    public final int value;
}
