package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;

/**
 * 日数チェック条件
 */
@AllArgsConstructor
public enum TypeOfDays {
    // 出勤日数
    WORKING_DAY_NUMBER(0),
    // 休日日数
    HOLIDAY_NUMBER(1),
    // 休出日数
    DAYOFF_NUMBER(2),
    //休出日数
    PUBLIC_HOLIDAY_NUMBER(3),
    // 休出日数
    SPECIAL_HOLIDAY_NUMBER(4),
    // 欠勤日数合計
    ABSENTEEDAY_NUMBER(5),
    // 年休使用数
    ANNUAL_LEAVE_NUMBER(6),
    // 積立年休使用数
    ACC_ANNUAL_LEAVE_NUMBER(7),
    // 予定勤務日数＋勤務日数
    SCHE_WORKINGD_AND_WORKINGD(8);

    public final int value;
}
