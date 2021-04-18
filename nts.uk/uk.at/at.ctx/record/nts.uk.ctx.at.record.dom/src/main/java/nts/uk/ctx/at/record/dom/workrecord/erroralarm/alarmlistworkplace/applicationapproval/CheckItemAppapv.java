package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 申請承認のチェック項目名称
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum CheckItemAppapv {
    /* 未承認１ */
    UNAPPROVED_1(1, "未承認１"),

    /* 未承認２ */
    UNAPPROVED_2(2, "未承認２"),

    /* 未承認３ */
    UNAPPROVED_3(3, "未承認３"),

    /* 未承認４ */
    UNAPPROVED_4(4, "未承認４"),

    /* 未承認５ */
    UNAPPROVED_5(5, "未承認５"),

    /* 未承認（反映条件未達） */
    UNAPPROVED(6, "未承認（反映条件未達）"),

    /* 否認 */
    DENIAL(7, "否認"),

    /* 未反映（反映条件達成） */
    NOT_REFLECTED(8, "未反映（反映条件達成）"),

    /* 代理承認 */
    PROXY_APPROVAL(9, "代理承認");

    public final int value;
    public final String nameId;

    public static CheckItemAppapv of(int value) {
        return EnumAdaptor.valueOf(value, CheckItemAppapv.class);
    }
}
