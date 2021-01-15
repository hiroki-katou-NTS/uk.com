package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.monthly;

import lombok.AllArgsConstructor;

/**
 * チェック項目の種類
 */
@AllArgsConstructor
public enum MonCheckItemType {
    // 対比
    CONTRAST(0),
    // 時間
    TIME(1),
    // 日数
    NUMBER_DAYS(2),
    // 残数チェック
    REMAIN_NUMBER(3);

    public final int value;
}
