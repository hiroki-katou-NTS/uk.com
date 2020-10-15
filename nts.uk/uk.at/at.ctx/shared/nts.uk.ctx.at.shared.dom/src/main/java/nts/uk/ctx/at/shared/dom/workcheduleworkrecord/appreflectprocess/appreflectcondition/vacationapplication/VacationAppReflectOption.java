package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4 refactor4
 * 休暇系申請の反映
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VacationAppReflectOption extends DomainObject {
    /**
     * 1日休暇の場合は出退勤を削除
     */
    private NotUseAtr oneDayLeaveDeleteAttendance;

    /**
     * 出退勤を反映する
     */
    private NotUseAtr reflectAttendance;

    /**
     * 就業時間帯を反映する
     */
    private ReflectWorkHourCondition reflectWorkHour;
}
