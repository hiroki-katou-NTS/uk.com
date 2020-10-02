package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.休暇系申請.振休申請
 * 振休申請の反映
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubstituteLeaveAppReflect {
    /**
     * 勤務情報、出退勤を反映する
     */
    private VacationAppReflectOption workInfoAttendanceReflect;
}
