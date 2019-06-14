package nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.socialinsurance.InsuranceFee;

import java.math.BigDecimal;

/**
 * 各負担料
 */
@Getter
@EqualsAndHashCode
public class HealthInsuranceContributionFee extends DomainObject {

    /**
     * 健康保険料
     */
    private InsuranceFee healthInsurancePremium;

    /**
     * 介護保険料
     */
    private InsuranceFee nursingCare;

    /**
     * 特定保険料
     */
    private InsuranceFee specInsurancePremium;

    /**
     * 基本保険料
     */
    private InsuranceFee basicInsurancePremium;

    /**
     * 各負担料
     *
     * @param healthInsurancePremium 健康保険料
     * @param nursingCare            介護保険料
     * @param specInsurancePremium   特定保険料
     * @param basicInsurancePremium  基本保険料
     */
    public HealthInsuranceContributionFee(BigDecimal healthInsurancePremium, BigDecimal nursingCare, BigDecimal specInsurancePremium, BigDecimal basicInsurancePremium) {
        this.healthInsurancePremium = new InsuranceFee(healthInsurancePremium);
        this.nursingCare            = new InsuranceFee(nursingCare);
        this.specInsurancePremium   = new InsuranceFee(specInsurancePremium);
        this.basicInsurancePremium  = new InsuranceFee(basicInsurancePremium);
    }
}
