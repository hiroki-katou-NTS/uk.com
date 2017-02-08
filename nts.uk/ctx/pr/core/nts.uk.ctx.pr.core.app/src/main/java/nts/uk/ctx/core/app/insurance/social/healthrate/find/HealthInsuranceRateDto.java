package nts.uk.ctx.core.app.insurance.social.healthrate.find;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;

@Builder
@Getter
@Setter
public class HealthInsuranceRateDto {
	/** The history id. */
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The office code. */
	private String officeCode;

	/** The office name. */
	private String officeName;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The auto calculate. */
	private Boolean autoCalculate;

	/** The max amount. */
	private BigDecimal maxAmount;

	/** The rate items. */
	private List<InsuranceRateItemDto> rateItems;

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
				domain.getOfficeCode().v(), "", domain.getApplyRange().getStartMonth().toString(),
				domain.getApplyRange().getEndMonth().toString(), domain.getAutoCalculate(), domain.getMaxAmount().v(),
				domain.getRateItems().stream()
						.map(insuranceRateItemDomain -> InsuranceRateItemDto.fromDomain(insuranceRateItemDomain))
						.collect(Collectors.toList()),
				domain.getRoundingMethods());

	}
}
