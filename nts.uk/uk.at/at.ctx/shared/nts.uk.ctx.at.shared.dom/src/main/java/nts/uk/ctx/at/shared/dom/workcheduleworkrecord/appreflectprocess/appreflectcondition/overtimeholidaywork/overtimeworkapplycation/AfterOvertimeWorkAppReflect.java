package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 事後残業申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AfterOvertimeWorkAppReflect {
    /**
     * その他項目を反映する
     */
    private OthersReflect othersReflect;

    /**
     * 休憩・外出を申請反映する
     */
    private BreakApplication breakLeaveApplication;

    /**
     * 出退勤を反映する
     */
    private NotUseAtr workReflect;
}
