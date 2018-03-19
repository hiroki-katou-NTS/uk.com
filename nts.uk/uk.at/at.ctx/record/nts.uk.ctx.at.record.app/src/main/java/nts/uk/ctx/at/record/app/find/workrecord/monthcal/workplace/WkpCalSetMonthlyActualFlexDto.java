/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.workplace;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpCalSetMonthlyActualFlexSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpCalSetMonthlyActualFlexDto.
 */
@Getter
public class WkpCalSetMonthlyActualFlexDto implements WkpCalSetMonthlyActualFlexSetMemento {

	/** The company id. */
	private CompanyId companyId;

	/** The workplace id. */
	private WorkplaceId workplaceId;

	/** The aggr setting monthly of flx new. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpCalSetMonthlyActualFlexSetMemento#setCompanyId(nts.uk.ctx.at.shared.
	 * dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpCalSetMonthlyActualFlexSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.
	 * dom.common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpCalSetMonthlyActualFlexSetMemento#setAggrSettingMonthlyOfFlxNew(nts.uk
	 * .ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew)
	 */
	@Override
	public void setAggrSettingMonthlyOfFlxNew(AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew) {
		this.aggrSettingMonthlyOfFlxNew = aggrSettingMonthlyOfFlxNew;
	}

}
