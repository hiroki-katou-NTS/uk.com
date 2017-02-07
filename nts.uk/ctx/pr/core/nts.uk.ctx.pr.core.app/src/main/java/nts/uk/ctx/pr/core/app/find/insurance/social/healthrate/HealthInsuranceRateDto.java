package nts.uk.ctx.pr.core.app.find.insurance.social.healthrate;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;

@Builder
@Getter
public class HealthInsuranceRateDto {
	/** The history id. */
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The office code. */
	private String officeCode;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The auto calculate. */
	private Boolean autoCalculate;

	/** The max amount. */
	private Long maxAmount;

	/** The rate items. */
	private List<InsuranceRateItem> rateItems;

	/** The rounding methods. */
	private List<HealthInsuranceRounding> roundingMethods;

	/**
	 * From domain.
	 *
	 * @param domain
	 *            the domain
	 * @return the health insurance rate dto
	 */
	public static HealthInsuranceRateDto fromDomain(HealthInsuranceRate domain) {
		return new HealthInsuranceRateDto(domain.getHistoryId(), domain.getCompanyCode().v(),
				domain.getOfficeCode().v(), domain.getApplyRange().getStartMonth().toString(),
				domain.getApplyRange().getEndMonth().toString(), domain.getAutoCalculate(), domain.getMaxAmount(),
				domain.getRateItems(), domain.getRoundingMethods());

	}
}
