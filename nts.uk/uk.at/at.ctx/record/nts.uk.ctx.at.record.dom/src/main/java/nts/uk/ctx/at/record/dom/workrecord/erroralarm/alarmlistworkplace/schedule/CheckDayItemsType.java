package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 日次職場チェック項目の種類
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum CheckDayItemsType {

    /* 対比 */
    CONTRAST(0, "対比"),

    /* 人数対比 */
    NUMBER_PEOPLE_COMPARISON(1, "人数対比"),

    /* 時間対比 */
    TIME_COMPARISON(2, "時間対比"),

    /* 金額対比 */
    AMOUNT_COMPARISON(3, "金額対比"),

    /* 比率対比 */
    RATIO_COMPARISON(4, "比率対比");

    public final int value;
    public final String nameId;

    public static CheckDayItemsType of(int value) {
        return EnumAdaptor.valueOf(value, CheckDayItemsType.class);
    }
}
