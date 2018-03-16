/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.workplace;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.WkpCalSetMonthlyActualFlexSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpCalSetMonthlyActualFlexDto.
 */
@Getter
public class WkpCalSetMonthlyActualFlexDto implements WkpCalSetMonthlyActualFlexSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The workplace id. */
	/** 職場ID. */
	private WorkplaceId workplaceId;

	/** The aggr setting monthly of flx new. */
	/** フレックス職場別月別実績集計設定. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpCalSetMonthlyActualFlexSetMemento#setCompanyId(nts.uk.ctx.at.shared.
	 * dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpCalSetMonthlyActualFlexSetMemento#setAggrSettingMonthlyOfFlxNew(nts.uk
	 * .ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew)
	 */
	@Override
	public void setAggrSettingMonthlyOfFlxNew(AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew) {
		this.aggrSettingMonthlyOfFlxNew = aggrSettingMonthlyOfFlxNew;
	}

}
