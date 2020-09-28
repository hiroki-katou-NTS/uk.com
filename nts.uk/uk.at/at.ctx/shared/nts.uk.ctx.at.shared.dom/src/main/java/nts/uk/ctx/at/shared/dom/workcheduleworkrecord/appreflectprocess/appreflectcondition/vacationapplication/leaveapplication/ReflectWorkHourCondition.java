package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
@AllArgsConstructor
public enum ReflectWorkHourCondition {

    /** 反映しない. */
    NOT_REFLECT(0, "反映しない"),

    /** 反映する. */
    REFLECT(1, "反映する"),

    /** 半日勤務の場合は反映する. */
    REFLECT_IF_HALF_DAY(2, "半日勤務の場合は反映する");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String name;

}
