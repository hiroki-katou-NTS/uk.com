package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;

/**
 * 時間のチェック項目
 */
@AllArgsConstructor
public enum CheckTimeType {
    // 予約時間
    RESERVATION_TIME(0);

    public final int value;
}
