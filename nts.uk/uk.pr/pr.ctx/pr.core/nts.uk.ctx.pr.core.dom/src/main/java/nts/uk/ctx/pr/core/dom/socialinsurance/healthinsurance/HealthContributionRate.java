package nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.socialinsurance.InsuranceRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;

import java.math.BigDecimal;

/**
 * 健康保険各保険負担率
 */
@Getter
public class HealthContributionRate extends DomainObject {

    /**
     * 介護保険料率
     */
    private InsuranceRate longCareInsuranceRate;

    /**
     * 基本保険料率
     */
    private InsuranceRate basicInsuranceRate;

    /**
     * 健康保険料率
     */
    private InsuranceRate healthInsuranceRate;

    /**
     * 端数区分
     */
    private InsurancePremiumFractionClassification fractionCls;

    /**
     * 特定保険料率
     */
    private InsuranceRate specialInsuranceRate;

    /**
     * 健康保険各保険負担率
     *
     * @param longCareInsuranceRate 介護保険料率
     * @param basicInsuranceRate    基本保険料率
     * @param healthInsuranceRate   健康保険料率
     * @param individualFractionCls 端数区分
     * @param specialInsuranceRate  特定保険料率
     */
    public HealthContributionRate(BigDecimal longCareInsuranceRate, BigDecimal basicInsuranceRate, BigDecimal healthInsuranceRate, int individualFractionCls, BigDecimal specialInsuranceRate) {
        this.longCareInsuranceRate = new InsuranceRate(longCareInsuranceRate);
        this.basicInsuranceRate    = new InsuranceRate(basicInsuranceRate);
        this.healthInsuranceRate   = new InsuranceRate(healthInsuranceRate);
        this.fractionCls           = EnumAdaptor.valueOf(individualFractionCls, InsurancePremiumFractionClassification.class);
        this.specialInsuranceRate  = new InsuranceRate(specialInsuranceRate);
    }
}
