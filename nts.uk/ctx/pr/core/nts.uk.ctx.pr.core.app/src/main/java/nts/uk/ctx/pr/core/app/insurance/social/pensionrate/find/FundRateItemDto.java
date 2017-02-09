package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;

@Data
@AllArgsConstructor
public class FundRateItemDto {
	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;

	/** The burden charge rate. */
	private BigDecimal burdenChargePersonalRate;
	
	/** The burden charge rate. */
	private BigDecimal burdenChargeCompanyRate;

	/** The exemption charge rate. */
	private BigDecimal exemptionChargePersonalRate;
	
	/** The exemption charge rate. */
	private BigDecimal exemptionChargeCompanyRate;
	public static FundRateItemDto fromDomain(FundRateItem domain)
	{
		return new FundRateItemDto(domain.getPayType(),domain.getGenderType(),domain.getBurdenChargeRate().getPersonalRate().v(),domain.getBurdenChargeRate().getCompanyRate().v(),
				domain.getExemptionChargeRate().getPersonalRate().v(),domain.getExemptionChargeRate().getCompanyRate().v());
	}
}
