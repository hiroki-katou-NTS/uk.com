package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 月次職場チェック項目の種類
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum CheckMonthlyItemsType {

    /* 平均時間 */
    AVERAGE_TIME(1, "平均時間"),
    /* 平均日数 */
    AVERAGE_NUMBER_DAY(2, "平均日数"),
    /* 平均回数 */
    AVERAGE_NUMBER_TIME(3, "平均回数"),
    /* 平均比率 */
    AVERAGE_RATIO(4, "平均比率"),
    /* 平均時間自由 */
    TIME_FREEDOM(5, "平均時間自由"),
    /* 平均日数自由 */
    AVERAGE_DAY_FREE(6, "平均日数自由"),
    /* 平均回数自由 */
    AVERAGE_TIME_FREE(7, "平均回数自由");

    public final int value;
    public final String nameId;

    public static CheckMonthlyItemsType of(int value) {
        return EnumAdaptor.valueOf(value, CheckMonthlyItemsType.class);
    }
}
