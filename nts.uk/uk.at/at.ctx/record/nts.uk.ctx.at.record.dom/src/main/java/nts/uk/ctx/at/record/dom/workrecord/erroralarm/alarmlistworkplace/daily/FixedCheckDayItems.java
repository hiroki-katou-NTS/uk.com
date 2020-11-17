package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 固定のチェック日別項目
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum FixedCheckDayItems {

    /* 入社者 */
    NEW_EMPLOYEE(1, "入社者"),

    /* 休職・休業者 */
    LEAVE(2, "休職・休業者"),

    /* 退職者 */
    RETIREE(3, "退職者"),

    /* 年休付与対象者 */
    PERSONS_ELIGIBLE_ANNUAL_LEAVE(4, "年休付与対象者"),

    /* 年休未付与者（本年=0の人） */
    PERSONS_NOT_ELIGIBLE(5, "年休未付与者（本年=0の人）"),

    /* 計画データ未登録（人数） */
    PLAN_NOT_REGISTERED_PEOPLE(6, "計画データ未登録（人数）"),

    /* 計画データ未登録（時間） */
    PLAN_NOT_REGISTERED_TIME(7, "計画データ未登録（時間）"),

    /* 計画データ未登録（金額） */
    PLAN_NOT_REGISTERED_AMOUNT(8, "計画データ未登録（金額）"),

    /* カード未登録打刻 */
    CARD_UNREGISTERD_STAMP(9, "カード未登録打刻"),

    /* 入社日よりも前の打刻 */
    STAMPING_BEFORE_JOINING(10, "入社日よりも前の打刻"),

    /* 退職日よりも後の打刻 */
    STAMPING_AFTER_RETIREMENT(11, "退職日よりも後の打刻");

    public final int value;
    public final String nameId;

    public static FixedCheckDayItems of(int value) {
        return EnumAdaptor.valueOf(value, FixedCheckDayItems.class);
    }
}
