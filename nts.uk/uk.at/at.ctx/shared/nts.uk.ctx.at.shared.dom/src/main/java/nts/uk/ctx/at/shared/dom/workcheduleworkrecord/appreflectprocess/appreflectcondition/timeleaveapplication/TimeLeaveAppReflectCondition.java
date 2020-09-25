package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 時間休暇の申請反映条件
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveAppReflectCondition extends DomainObject {
    /**
     * 60H超休
     */
    private NotUseAtr superHoliday60H;

    /**
     * 介護
     */
    private NotUseAtr nursing;

    /**
     * 子看護
     */
    private NotUseAtr childNursing;

    /**
     * 時間代休
     */
    private NotUseAtr substituteLeaveTime;

    /**
     * 時間年休
     */
    private NotUseAtr annualVacationTime;

    /**
     * 時間特別休暇
     */
    private NotUseAtr specialVacationTime;
}
