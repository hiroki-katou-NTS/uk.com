/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento;

/**
 * The Class WorkplaceDto.
 */
public class WorkplaceDto implements WorkplaceSetMemento {

	/** The company id. */
	// 会社ID
	public String companyId;

	/** The workplace id. */
	//職場ID
	public String workplaceId;
	
	/** The workplace history. */
	// 履歴
	public List<WorkplaceHistoryDto> workplaceHistory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento#setCompanyId(
	 * java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento#
	 * setWorkplaceHistory(java.util.List)
	 */
	@Override
	public void setWorkplaceHistory(List<WorkplaceHistory> workplaceHistory) {
		this.workplaceHistory = workplaceHistory.stream().map(item -> {
			WorkplaceHistoryDto wkpDto = new WorkplaceHistoryDto();
			item.saveToMemento(wkpDto);
			return wkpDto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceSetMemento#setWorkplaceId(
	 * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(String workplaceId) {
		this.workplaceId = workplaceId;
	}
}
