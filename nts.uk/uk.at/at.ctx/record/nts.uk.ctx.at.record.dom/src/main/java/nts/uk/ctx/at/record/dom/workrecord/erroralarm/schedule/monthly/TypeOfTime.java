package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;

/**
 * 時間の種類
 */
@AllArgsConstructor
public enum TypeOfTime {
    // 予定時間＋総労働時間
    SCHETIME_WORKING_HOURS(0);

    public final int value;
}
