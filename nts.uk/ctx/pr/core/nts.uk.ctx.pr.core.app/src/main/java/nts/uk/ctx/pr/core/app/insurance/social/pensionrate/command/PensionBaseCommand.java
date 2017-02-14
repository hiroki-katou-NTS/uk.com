package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.FundRateItemDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionPremiumRateItemDto;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

@Getter
@Setter
public abstract class PensionBaseCommand {
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
}
