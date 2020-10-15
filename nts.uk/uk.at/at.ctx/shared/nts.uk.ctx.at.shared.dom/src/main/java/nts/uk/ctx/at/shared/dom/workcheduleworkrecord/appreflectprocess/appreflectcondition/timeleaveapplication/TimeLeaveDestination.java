package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 時間休暇の反映先
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveDestination {
    /**
     * 出勤前(1回目勤務)
     */
    private NotUseAtr firstBeforeWork;

    /**
     * 出勤前(2回目勤務)
     */
    private NotUseAtr secondBeforeWork;

    /**
     * 私用外出
     */
    private NotUseAtr privateGoingOut;

    /**
     * 組合外出
     */
    private NotUseAtr unionGoingOut;

    /**
     * 退勤後(1回目勤務)
     */
    private NotUseAtr firstAfterWork;

    /**
     * 退勤後(1回目勤務)
     */
    private NotUseAtr secondAfterWork;
}
