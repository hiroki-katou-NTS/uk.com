package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

@Builder
@Getter
@Setter
public class PensionRateDto {
	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The office code. */
	private String officeCode;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The max amount. */
	private BigDecimal maxAmount;

	/** The fund rate items. */
	private List<FundRateItemDto> fundRateItems;

	/** The premium rate items. */
	private List<PensionPremiumRateItemDto> premiumRateItems;

	/** The child contribution rate. */
	private BigDecimal childContributionRate;

	/** The rounding methods. */
	private List<PensionRateRounding> roundingMethods;
	
	public static PensionRateDto fromDomain(PensionRate domain) {
		return new PensionRateDto(domain.getHistoryId(), domain.getCompanyCode().v(), domain.getOfficeCode().v(),
				domain.getApplyRange().getStartMonth().toString(), domain.getApplyRange().getEndMonth().toString(),
				domain.getMaxAmount().v(),
				domain.getFundRateItems().stream()
						.map(fundRateItemDomain -> FundRateItemDto.fromDomain(fundRateItemDomain))
						.collect(Collectors.toList()),
				domain.getPremiumRateItems().stream()
						.map(premiumRateItemDomain -> PensionPremiumRateItemDto.fromDomain(premiumRateItemDomain))
						.collect(Collectors.toList()),
				domain.getChildContributionRate().v(), domain.getRoundingMethods());
	}
}
