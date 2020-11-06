package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 平均回数
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum AverageNumberOfTimes {

    /* 遅刻回数 */
    NUMBER_LATE_ARRIVALS(1, "遅刻回数"),
    /* 早退回数 */
    NUMBER_EARLY_DEPARTURES(2, "早退回数"),
    /* 外出回数 */
    NUMBER_OUTINGS(3, "外出回数");

    public final int value;
    public final String nameId;

    public static AverageNumberOfTimes of(int value) {
        return EnumAdaptor.valueOf(value, AverageNumberOfTimes.class);
    }
}
