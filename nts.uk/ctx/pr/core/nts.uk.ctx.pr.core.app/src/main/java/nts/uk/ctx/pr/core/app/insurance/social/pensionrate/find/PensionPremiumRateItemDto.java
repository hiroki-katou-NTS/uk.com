package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;

@Data
@AllArgsConstructor
public class PensionPremiumRateItemDto {

	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;

	private BigDecimal personalRate;

	private BigDecimal companyRate;

	public static PensionPremiumRateItemDto fromDomain(PensionPremiumRateItem domain) {
		return new PensionPremiumRateItemDto(domain.getPayType(), domain.getGenderType(),
				domain.getChargeRate().getPersonalRate().v(),domain.getChargeRate().getCompanyRate().v());
	}
}
