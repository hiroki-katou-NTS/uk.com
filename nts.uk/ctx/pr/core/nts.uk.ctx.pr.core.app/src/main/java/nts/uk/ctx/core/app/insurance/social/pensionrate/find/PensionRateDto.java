package nts.uk.ctx.core.app.insurance.social.pensionrate.find;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

@Data
public class PensionRateDto {
	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The office code. */
	private OfficeCode officeCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The max amount. */
	private CommonAmount maxAmount;

	/** The fund rate items. */
	private List<FundRateItem> fundRateItems;

	/** The premium rate items. */
	private List<PensionPremiumRateItem> premiumRateItems;

	/** The child contribution rate. */
	private Ins2Rate childContributionRate;

	/** The rounding methods. */
	private List<PensionRateRounding> roundingMethods;

}
