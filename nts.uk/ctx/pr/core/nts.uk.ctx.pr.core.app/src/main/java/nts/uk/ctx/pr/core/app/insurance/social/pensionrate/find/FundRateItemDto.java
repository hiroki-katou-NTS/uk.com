package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionChargeRateItem;

@Data
public class FundRateItemDto {
	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;

	/** The burden charge rate. */
	private PensionChargeRateItem burdenChargeRate;

	/** The exemption charge rate. */
	private PensionChargeRateItem exemptionChargeRate;
}
