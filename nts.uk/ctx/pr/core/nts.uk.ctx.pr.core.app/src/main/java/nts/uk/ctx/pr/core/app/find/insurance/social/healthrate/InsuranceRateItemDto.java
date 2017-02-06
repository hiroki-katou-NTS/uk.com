package nts.uk.ctx.pr.core.app.find.insurance.social.healthrate;

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
