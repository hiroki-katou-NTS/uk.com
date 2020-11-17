package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 対比種類
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum ContrastType {

    /* 人数対比（予定） */
    PLAN_NUMBER_PEOPLE(1, "人数対比（予定）"),

    /* 時間対比（予定） */
    PLAN_TIME_COMPARISON(2, "時間対比（予定）"),

    /* 金額対比（予定） */
    PLAN_AMOUNT_COMPARISON(3, "金額対比（予定）"),

    /* 人数対比（実績） */
    PERFORMANCE_NUMBER_PEOPLE(4, "人数対比（実績）"),

    /* 時間対比（実績） */
    PERFORMANCE_TIME_COMPARISON(5, "時間対比（実績）"),

    /* 金額対比（実績） */
    PERFORMANCE_AMOUNT_COMPARISON(6, "金額対比（実績）"),

    /* 人数対比（予定＋実績） */
    SCHE_ACHI_NUMBER_PEOPLE(7, "人数対比（予定＋実績）"),

    /* 時間対比（予定＋実績） */
    SCHE_ACHI_TIME_COMPARISON(8, "時間対比（予定＋実績）"),

    /* 金額対比（予定＋実績）*/
    SCHE_ACHI_AMOUNT_COMPARISON(9, "金額対比（予定＋実績）");

    public final int value;
    public final String nameId;

    public static ContrastType of(int value) {
        return EnumAdaptor.valueOf(value, ContrastType.class);
    }
}
