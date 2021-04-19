package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 平均比率
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum AverageRatio {

    /* 出勤率 */
    ATTENDANCE(1, "出勤率"),
    /* 休日・休暇率 */
    HOLIDAY_VACATION_RATE(2, "休日・休暇率"),
    /* 年休消化率（繰越含む） */
    ANNUAL_LEAVE_DIGESTION_RATE(3, "年休消化率（繰越含む）"),
    /* 時間年休消化率（繰越含む） */
    TIME_ANNUAL_BREAK_DIG(4, "時間年休消化率（繰越含む）"),
    /* 年休消化率（繰越含まない） */
    ANNUAL_HOLIDAY_DIGESTIBILITY(5, "年休消化率（繰越含まない）"),
    /* 時間年休消化率（繰越含まない） */
    TIME_ABD_NOT_INC(6, "時間年休消化率（繰越含まない）");

    public final int value;
    public final String nameId;

    public static AverageRatio of(int value) {
        return EnumAdaptor.valueOf(value, AverageRatio.class);
    }
}
