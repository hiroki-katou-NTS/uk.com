package nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 条件値の種別
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum ConditionType {

    /* 固定値 */
    FIXED_VALUE(0, "固定値"),
    /* 勤怠項目 */
    ATTENDANCE_ITEM(1, "勤怠項目");

    public final int value;
    public final String nameId;

    public static ConditionType of(int value) {
        return EnumAdaptor.valueOf(value, ConditionType.class);
    }
}
