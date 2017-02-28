/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

/**
 * The Class WageTableHist.
 */
@Getter
public class WageTableHistory extends DomainObject {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private WageTableCode code;

	/** The history id. */
	private String historyId;

	/** The apply range. */
	private MonthRange applyRange;

	/** The elements. */
	private List<WageTableAmount> elements;

}
