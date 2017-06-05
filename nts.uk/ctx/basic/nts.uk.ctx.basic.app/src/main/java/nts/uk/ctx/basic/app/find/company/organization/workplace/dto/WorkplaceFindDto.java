/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.workplace.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceCode;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceName;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento;

/**
 * The Class WorkplaceFindDto.
 */

@Getter
@Setter
public class WorkplaceFindDto implements WorkplaceSetMemento{
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setCompanyId(nts.uk.ctx.basic.dom.company.organization.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setPeriod(nts.uk.ctx.basic.dom.common.history.Period)
	 */
	@Override
	public void setPeriod(Period period) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setWorkplaceId(nts.uk.ctx.basic.dom.company.organization.workplace.
	 * WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setWorkplaceCode(nts.uk.ctx.basic.dom.company.organization.workplace.
	 * WorkplaceCode)
	 */
	@Override
	public void setWorkplaceCode(WorkplaceCode workplaceCode) {
		this.code = workplaceCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setWorkplaceName(nts.uk.ctx.basic.dom.company.organization.workplace.
	 * WorkplaceName)
	 */
	@Override
	public void setWorkplaceName(WorkplaceName workplaceName) {
		this.name = workplaceName.v();
	}

}
