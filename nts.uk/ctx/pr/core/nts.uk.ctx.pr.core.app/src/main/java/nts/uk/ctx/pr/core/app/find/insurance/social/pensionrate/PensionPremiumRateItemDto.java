package nts.uk.ctx.pr.core.app.find.insurance.social.pensionrate;

import java.util.Set;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionChargeRateItem;

@Data
public class PensionPremiumRateItemDto {
	/** The charge rates. */
	private Set<PensionChargeRateItem> chargeRates;

	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;
}
