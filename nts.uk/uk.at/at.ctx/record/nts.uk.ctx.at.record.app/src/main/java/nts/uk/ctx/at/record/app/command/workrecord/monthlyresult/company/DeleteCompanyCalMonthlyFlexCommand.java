/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.common.AggrSettingMonthlyOfFlxNewDto;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComCalMonthlyFlexGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class DeleteCompanyCalMonthlyFlexCommand.
 */
@Getter
@Setter
public class DeleteCompanyCalMonthlyFlexCommand implements ComCalMonthlyFlexGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The aggr setting monthly of flx new. */
	/* 集計設定. */
	private AggrSettingMonthlyOfFlxNewDto aggrSettingMonthlyOfFlxNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyCalMonthlyFlexGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyCalMonthlyFlexGetMemento#getAggrSettingMonthlyOfFlxNew()
	 */
	@Override
	public AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
