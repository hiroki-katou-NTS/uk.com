package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 事前残業申請の反映
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BeforeOvertimeWorkAppReflect {
    private BreakApplication breakLeaveApplication;
    /**
     * 勤務情報、出退勤を反映する
     */
    private NotUseAtr reflectWorkInfoAtr;

    /**
     * 残業時間を実績項目へ反映する
     */
    private NotUseAtr reflectActualOvertimeHourAtr;
}
