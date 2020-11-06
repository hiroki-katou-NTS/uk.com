package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 平均時間
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum AverageTime {

    /* 遅刻時間 */
    LATE_TIME(1, "遅刻時間"),
    /* 早退時間 */
    EARLY_LEAVE_TIME(2, "早退時間"),
    /* 外出時間合計 */
    TOTAL_OUTING_TIME(3, "外出時間合計"),
    /* 総労働時間 */
    TOTAL_WORKING_HOURS(4, "総労働時間"),
    /* 残業時間合計 */
    TOTAL_OVERTIME_HOURS(5, "残業時間合計"),
    /* 休出時間合計 */
    TOTAL_VÂCTION_HOURS(6, "休出時間合計"),
    /* 深夜合計（内、外） */
    TOTAL_LATE_NIGHT(7, "深夜合計（内、外）"),
    /* フレックス時間 */
    FLEX_TIME(8, "フレックス時間"),
    /* 総拘束時間 */
    TOTAL_RESTRAINT_TIME(9, "総拘束時間"),
    /* 総拘束時間－総労働時間 */
    TOTAL_RESTRAINT_WORKING_TIME(10, "総拘束時間－総労働時間"),
    /* 時間年休使用時間 */
    TIME_ANNUAL_LEAVE_USE_TIME(11, "時間年休使用時間");


    public final int value;
    public final String nameId;

    public static AverageTime of(int value) {
        return EnumAdaptor.valueOf(value, AverageTime.class);
    }
}
