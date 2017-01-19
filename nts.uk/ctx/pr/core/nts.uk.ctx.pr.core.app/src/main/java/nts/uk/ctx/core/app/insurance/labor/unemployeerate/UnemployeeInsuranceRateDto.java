package nts.uk.ctx.core.app.insurance.labor.unemployeerate;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;

@Getter
@Setter
public class UnemployeeInsuranceRateDto {
	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private String companyCode;

	// private MonthRange applyRange;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItem> rateItems;
}
