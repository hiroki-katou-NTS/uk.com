package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;

/**
 * 残数チェックの休暇の種類
 */
@AllArgsConstructor
public enum TypeOfVacations {
    // 公休
    PUBLIC_HOLIDAY(0, "公休"),
    // 年休残数
    ANNUAL_LEAVE_NUMBER(1, "年休残数"),
    // 積立年休
    ACC_ANNUAL_LEAVE(2, "積立年休"),
    // 振休
    HOLIDAY(3, "振休"),
    // 代休
    SUB_HOLIDAY(4, "代休"),
    // 時間代休残数
    HOURS_LEFT(5, "時間代休残数");

    public final int value;
    public final String nameId;
}
