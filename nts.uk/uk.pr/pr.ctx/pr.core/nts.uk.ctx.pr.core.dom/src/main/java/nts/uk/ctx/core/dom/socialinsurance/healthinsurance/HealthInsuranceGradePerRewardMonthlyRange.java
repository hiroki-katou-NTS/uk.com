package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 等級毎報酬月額範囲
 */
@Getter
public class HealthInsuranceGradePerRewardMonthlyRange extends DomainObject {

    /**
     * 健康保険等級
     */
    private int healthInsuranceGrade;

    /**
     * 報酬月額下限
     */
    private long rewardMonthlyLowerLimit;

    /**
     * 報酬月額上限
     */
    private long rewardMonthlyUpperLimit;

    /**
     * 等級毎報酬月額範囲
     *
     * @param healthInsuranceGrade    健康保険等級
     * @param rewardMonthlyLowerLimit 報酬月額下限
     * @param rewardMonthlyUpperLimit 報酬月額上限
     */
    public HealthInsuranceGradePerRewardMonthlyRange(int healthInsuranceGrade, long rewardMonthlyLowerLimit, long rewardMonthlyUpperLimit) {
        this.healthInsuranceGrade = healthInsuranceGrade;
        this.rewardMonthlyLowerLimit = rewardMonthlyLowerLimit;
        this.rewardMonthlyUpperLimit = rewardMonthlyUpperLimit;
    }
}
