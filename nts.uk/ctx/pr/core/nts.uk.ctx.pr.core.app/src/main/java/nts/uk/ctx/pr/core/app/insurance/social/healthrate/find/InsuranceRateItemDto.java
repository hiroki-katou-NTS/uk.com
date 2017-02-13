package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;

@Builder
@Getter
public class InsuranceRateItemDto {
	/** The pay type. */
	private PaymentType payType;

	/** The insurance type. */
	private HealthInsuranceType insuranceType;

	/** The company rate. */
	private BigDecimal companyRate;

	/** The personal rate. */
	private BigDecimal personalRate;

	public static InsuranceRateItemDto fromDomain(InsuranceRateItem domain) {
		return new InsuranceRateItemDto(domain.getPayType(), domain.getInsuranceType(),
				domain.getChargeRate().getCompanyRate().v(), domain.getChargeRate().getPersonalRate().v());
	}
}
