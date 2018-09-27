package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.socialinsurance.InsuranceFee;

import java.math.BigDecimal;

/**
 * 各負担料
 */
@Getter
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basicInsurancePremium == null) ? 0 : basicInsurancePremium.hashCode());
		result = prime * result + ((healthInsurancePremium == null) ? 0 : healthInsurancePremium.hashCode());
		result = prime * result + ((nursingCare == null) ? 0 : nursingCare.hashCode());
		result = prime * result + ((specInsurancePremium == null) ? 0 : specInsurancePremium.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HealthInsuranceContributionFee other = (HealthInsuranceContributionFee) obj;
		if (basicInsurancePremium == null) {
			if (other.basicInsurancePremium != null)
				return false;
		} else if (!basicInsurancePremium.equals(other.basicInsurancePremium))
			return false;
		if (healthInsurancePremium == null) {
			if (other.healthInsurancePremium != null)
				return false;
		} else if (!healthInsurancePremium.equals(other.healthInsurancePremium))
			return false;
		if (nursingCare == null) {
			if (other.nursingCare != null)
				return false;
		} else if (!nursingCare.equals(other.nursingCare))
			return false;
		if (specInsurancePremium == null) {
			if (other.specInsurancePremium != null)
				return false;
		} else if (!specInsurancePremium.equals(other.specInsurancePremium))
			return false;
		return true;
	}
}
