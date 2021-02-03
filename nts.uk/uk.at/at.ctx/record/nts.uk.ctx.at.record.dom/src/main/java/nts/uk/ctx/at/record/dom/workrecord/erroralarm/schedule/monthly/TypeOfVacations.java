package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;

/**
 * 残数チェックの休暇の種類
 */
@AllArgsConstructor
public enum TypeOfVacations {
    // 公休
    PUBLIC_HOLIDAY(0),
    // 年休残数
    ANNUAL_LEAVE_NUMBER(1),
    // 積立年休
    ACC_ANNUAL_LEAVE(2),
    // 振休
    HOLIDAY(3),
    // 代休
    SUB_HOLIDAY(4),
    // 時間代休残数
    HOURS_LEFT(5);

    public final int value;
}
