/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.config.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchySetMemento;

@Getter
@Setter
public class WorkplaceHierarchyDto implements WorkplaceHierarchySetMemento {

	/** The workplace id. */
	public String workplaceId;

	/** The hierarchy code. */
	public String hierarchyCode;
	
	/** The name. */
	public String name;
	
	/** The code. */
	public String code;

	/** The childs. */
	private List<WorkplaceHierarchyDto> childs;
	
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId.v();
	}

	@Override
	public void setHierarchyCode(HierarchyCode hierarchyCode) {
		this.hierarchyCode = hierarchyCode.v();
	}
}
