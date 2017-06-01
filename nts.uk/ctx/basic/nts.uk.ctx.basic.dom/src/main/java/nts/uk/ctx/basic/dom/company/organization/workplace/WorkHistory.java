/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

@Getter
public class WorkHistory extends AggregateRoot{
	
	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The history id. */
	//履歴ID
	private HistoryId historyId;
	
	
	/** The period. */
	//期間
	private Period period;
	
}
