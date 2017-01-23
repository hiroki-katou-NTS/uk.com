package nts.uk.ctx.pr.core.app.finder.healthinsurance.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.ChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceType;

@Builder
@Data
public class InsuranceRateItemDto {
	/** The charge rate. */
	private ChargeRateItem chargeRate;

	/** The pay type. */
	private PaymentType payType;

	/** The insurance type. */
	private HealthInsuranceType insuranceType;
}
