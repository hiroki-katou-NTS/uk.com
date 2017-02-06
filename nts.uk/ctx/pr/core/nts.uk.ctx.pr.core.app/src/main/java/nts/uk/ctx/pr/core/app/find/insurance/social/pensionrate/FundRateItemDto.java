package nts.uk.ctx.pr.core.app.find.insurance.social.pensionrate;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.ChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;

@Data
public class FundRateItemDto {
	/** The burden charge rate. */
	private ChargeRateItem burdenChargeRate;

	/** The pay type. */
	private PaymentType payType;

	/** The exemption charge rate. */
	private ChargeRateItem exemptionChargeRate;

	/** The gender type. */
	private InsuranceGender genderType;
}
