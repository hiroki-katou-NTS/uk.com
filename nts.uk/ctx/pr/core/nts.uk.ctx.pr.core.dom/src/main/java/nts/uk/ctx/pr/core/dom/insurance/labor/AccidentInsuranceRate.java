/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

@Getter
public class AccidentInsuranceRate extends AggregateRoot {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The short name. */
	private Set<UnemployeeInsuranceRateItem> rateItems;

}
