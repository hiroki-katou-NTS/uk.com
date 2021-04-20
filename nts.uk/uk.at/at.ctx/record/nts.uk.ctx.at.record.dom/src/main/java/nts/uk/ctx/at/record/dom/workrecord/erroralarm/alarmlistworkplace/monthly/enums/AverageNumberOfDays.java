package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 平均日数
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum AverageNumberOfDays {

    /* 出勤日数 */
    NUMBER_WORK_DAYS(1, "出勤日数"),
    /* 休日日数 */
    HOLIDAY_DAYS(2, "休日日数"),
    /* 休出日数 */
    NUMBER_DAYS_OFF(3, "休出日数"),
    /* 公休日数 */
    NUMBER_HOLIDAYS(4, "公休日数"),
    /* 特休日数合計 */
    TOTAL_SPECIAL_HOLIDAYS(5, "特休日数合計"),
    /* 欠勤日数合計 */
    TOTAL_ABSENTEE_DAYS(6, "欠勤日数合計"),
    /* 年休使用数 */
    ANNUAL_REST_USE(7, "年休使用数"),
    /* 積立年休使用数 */
    ACCUMULATED_ANNUAL_LEAVE_USED(8, "積立年休使用数");

    public final int value;
    public final String nameId;

    public static AverageNumberOfDays of(int value) {
        return EnumAdaptor.valueOf(value, AverageNumberOfDays.class);
    }
}
