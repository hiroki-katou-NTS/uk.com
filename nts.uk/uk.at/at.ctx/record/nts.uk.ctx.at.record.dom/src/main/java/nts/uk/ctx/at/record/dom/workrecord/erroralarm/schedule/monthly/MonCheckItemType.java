package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;

/**
 * チェック項目の種類
 */
@AllArgsConstructor
public enum MonCheckItemType {
    // 対比
    CONTRAST(0, "対比"),
    // 時間
    TIME(1, "時間"),
    // 日数
    NUMBER_DAYS(2, "日数"),
    // 残数チェック
    REMAIN_NUMBER(3, "残数チェック");

    public final int value;
    public final String nameId;
}
