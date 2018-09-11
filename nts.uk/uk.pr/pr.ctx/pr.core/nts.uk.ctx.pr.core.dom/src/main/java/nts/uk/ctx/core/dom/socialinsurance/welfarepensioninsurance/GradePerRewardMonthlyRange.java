package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 等級毎報酬月額範囲
 */
@Getter
public class GradePerRewardMonthlyRange extends DomainObject {

    /**
     * 厚生年金等級
     */
    private int welfarePensionGrade;

    /**
     * 報酬月額下限
     */
    private long rewardMonthlyLowerLimit;

    /**
     * 報酬月額上限
     */
    private long rewardMonthlyUpperLimit;

    public GradePerRewardMonthlyRange(int welfarePensionGrade, long rewardMonthlyLowerLimit, long rewardMonthlyUpperLimit) {
        this.welfarePensionGrade = welfarePensionGrade;
        this.rewardMonthlyLowerLimit = rewardMonthlyLowerLimit;
        this.rewardMonthlyUpperLimit = rewardMonthlyUpperLimit;
    }

}
