package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 固定のチェック月次項目名称
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum FixedCheckMonthlyItemName {

    /* 月次データ未確定 */
    MONTHLY_UNDECIDED(1, "月次データ未確定");

    public final int value;
    public final String nameId;

    public static FixedCheckMonthlyItemName of(int value) {
        return EnumAdaptor.valueOf(value, FixedCheckMonthlyItemName.class);
    }
}
