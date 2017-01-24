package nts.uk.ctx.pr.core.app.finder.pension.dto;

import java.util.Set;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.ChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;

@Data
public class PensionPremiumRateItemDto {
	/** The charge rates. */
	private Set<ChargeRateItem> chargeRates;

	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;
}
