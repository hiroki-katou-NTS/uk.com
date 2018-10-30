package nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.math.BigDecimal;

/**
 * 等級毎健康保険料
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class HealthInsurancePerGradeFee extends DomainObject {

    /**
     * 健康保険等級
     */
    private int healthInsuranceGrade;

    /**
     * 事業主負担
     */
    private HealthInsuranceContributionFee employeeBurden;

    /**
     * 被保険者負担
     */
    private HealthInsuranceContributionFee insuredBurden;

    /**
     * @param healthInsuranceGrade           健康保険等級
     * @param employeeHealthInsurancePremium 事業主負担健康保険料
     * @param employeeNursingCare            事業主負担介護保険料
     * @param employeeSpecInsurancePremium   事業主負担特定保険料
     * @param employeeBasicInsurancePremium  事業主負担基本保険料
     * @param insuredHealthInsurancePremium  被保険者負担健康保険料
     * @param insuredNursingCare             被保険者負担介護保険料
     * @param insuredSpecInsurancePremium    被保険者負担特定保険料
     * @param insuredBasicInsurancePremium   被保険者負担基本保険料
     */
    public HealthInsurancePerGradeFee(int healthInsuranceGrade,
                                      BigDecimal employeeHealthInsurancePremium, BigDecimal employeeNursingCare, BigDecimal employeeSpecInsurancePremium, BigDecimal employeeBasicInsurancePremium,
                                      BigDecimal insuredHealthInsurancePremium, BigDecimal insuredNursingCare, BigDecimal insuredSpecInsurancePremium, BigDecimal insuredBasicInsurancePremium) {
        this.healthInsuranceGrade = healthInsuranceGrade;
        this.employeeBurden       = new HealthInsuranceContributionFee(employeeHealthInsurancePremium, employeeNursingCare, employeeSpecInsurancePremium, employeeBasicInsurancePremium);
        this.insuredBurden        = new HealthInsuranceContributionFee(insuredHealthInsurancePremium, insuredNursingCare, insuredSpecInsurancePremium, insuredBasicInsurancePremium);
    }
}
