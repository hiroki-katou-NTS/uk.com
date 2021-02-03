package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;

/**
 * チェック項目の種類
 */
@AllArgsConstructor
public enum DaiCheckItemType {
    // 時間
    TIME(0),
    // 連続時間
    CONTINUOUS_TIME(1),
    // 連続勤務
    CONTINUOUS_WORK(2),
    //連続時間帯
    CONTINUOUS_TIMEZONE(3);

    public final int value;
}
