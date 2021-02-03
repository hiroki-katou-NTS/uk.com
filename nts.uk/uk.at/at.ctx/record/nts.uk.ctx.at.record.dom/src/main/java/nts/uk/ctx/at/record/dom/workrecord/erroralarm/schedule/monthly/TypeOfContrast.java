package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;

/**
 * 対比の種類
 */
@AllArgsConstructor
public enum TypeOfContrast {
    // 所定公休日数比
    WORKING_DAY_NUMBER(0),
    // 基準時間比（通常）
    HOLIDAY_NUMBER(1),
    // 基準時間比（変形労働）
    DAYOFF_NUMBER(2),
    // 基準時間比（フレックス）
    PUBLIC_HOLIDAY_NUMBER(3);

    public final int value;
}
