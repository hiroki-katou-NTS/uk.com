/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.dto;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceHierarchySetMemento;

public class WorkplaceHierarchyDto implements WorkplaceHierarchySetMemento {

	/** The workplace id. */
	// 職場ID
	public String workplaceId;

	/** The hierarchy code. */
	// 階層コード
	public String hierarchyCode;

	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId.v();
	}

	@Override
	public void setHierarchyCode(String hierarchyCode) {
		this.hierarchyCode = hierarchyCode;
	}

}
