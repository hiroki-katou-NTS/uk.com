/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.workplace;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.WkpCalSetMonthlyActualFlexGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class UpdateWkpCalSetMonthlyActualFlexCommand.
 */
@Getter
@Setter
public class UpdateWkpCalSetMonthlyActualFlexCommand implements WkpCalSetMonthlyActualFlexGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The workplace id. */
	/* 職場ID. */
	private WorkplaceId workplaceId;

	/** The aggr setting monthly of flx new. */
	/* 集計設定. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpCalSetMonthlyActualFlexGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpCalSetMonthlyActualFlexGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpCalSetMonthlyActualFlexGetMemento#getAggrSettingMonthlyOfFlxNew()
	 */
	@Override
	public AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
