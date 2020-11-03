package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 固定のチェック日次項目名称
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum FixedCheckDayItemName {

    /* スケジュール未確定 */
    SCHEDULE_UNDECIDED(1, "スケジュール未確定");

    public final int value;
    public final String nameId;

    public static FixedCheckDayItemName of(int value) {
        return EnumAdaptor.valueOf(value, FixedCheckDayItemName.class);
    }
}
