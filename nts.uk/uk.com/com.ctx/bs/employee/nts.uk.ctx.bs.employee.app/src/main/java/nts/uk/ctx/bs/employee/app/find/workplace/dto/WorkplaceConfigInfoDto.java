/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigInfoSetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHierarchy;

/**
 * The Class WorkplaceConfigInfoDto.
 */
public class WorkplaceConfigInfoDto implements WorkplaceConfigInfoSetMemento {

	/** The company id. */
	// 会社ID
	public String companyId;

	/** The history id. */
	// 履歴ID
	public String historyId;

	/** The wkp hierarchy. */
	// 階層
	public List<WorkplaceHierarchyDto> wkpHierarchy;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigInfoSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigInfoSetMemento#setHistoryId(nts.uk.ctx.bs.employee.dom.workplace.HistoryId)
	 */
	@Override
	public void setHistoryId(HistoryId historyId) {
		this.historyId = historyId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigInfoSetMemento#setWkpHierarchy(java.util.List)
	 */
	@Override
	public void setWkpHierarchy(List<WorkplaceHierarchy> wkpHierarchy) {
		this.wkpHierarchy = wkpHierarchy.stream().map(item -> {
			WorkplaceHierarchyDto wkph = new WorkplaceHierarchyDto();
			item.saveToMemento(wkph);
			return wkph;
		}).collect(Collectors.toList());
	}
}
