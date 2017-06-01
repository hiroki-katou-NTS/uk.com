/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Class WorkHierarchy.
 */
@Getter
public class WorkHierarchy extends DomainObject{
	
	/** The company id. */
	//会社ID
	private CompanyId companyId;
	
	/** The history id. */
	//履歴ID
	private HistoryId historyId;

	/** The work place id. */
	//職場ID
	private WorkplaceId workplaceId;
	
	/** The work place code. */
	//階層コード
	private WorkplaceCode workplaceCode;
}
