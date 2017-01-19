/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.core.app.insurance.labor.accidentrate;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

// TODO: Auto-generated Javadoc
@Getter
@Setter	
public class AccidentInsuranceRateDto {
	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The short name. */
	private List<InsuBizRateItem> rateItems;

}
