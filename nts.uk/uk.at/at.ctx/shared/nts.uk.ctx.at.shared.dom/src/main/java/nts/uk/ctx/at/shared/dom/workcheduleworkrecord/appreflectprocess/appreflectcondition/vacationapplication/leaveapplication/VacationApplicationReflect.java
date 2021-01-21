package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;

/**
 * refactor4 refactor 4
 * 休暇申請の反映
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.休暇系申請.休暇申請
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VacationApplicationReflect extends AggregateRoot {

    private String companyId;

    /**
     * 勤務情報、出退勤を反映する
     */
    private VacationAppReflectOption workAttendanceReflect;

    /**
     * 時間休暇を反映する
     */
    private TimeLeaveAppReflectCondition timeLeaveReflect;
}
