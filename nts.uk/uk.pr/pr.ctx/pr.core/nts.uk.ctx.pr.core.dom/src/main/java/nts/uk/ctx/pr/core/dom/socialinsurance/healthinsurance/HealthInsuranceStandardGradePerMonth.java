package nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 等級毎標準月額
 */
@Getter
public class HealthInsuranceStandardGradePerMonth extends DomainObject {

    /**
     * 健康保険等級
     */
    private int healthInsuranceGrade;

    /**
     * 標準月額
     */
    private long standardMonthlyFee;

    public HealthInsuranceStandardGradePerMonth(int healthInsuranceGrade, long standardMonthlyFee) {
        this.healthInsuranceGrade = healthInsuranceGrade;
        this.standardMonthlyFee = standardMonthlyFee;
    }
}
