/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.workplace;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.WkpRegularSetMonthlyActualWorkGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class UpdateWkpRegularSetMonthlyActualWorkCommand.
 */
@Getter
@Setter
public class UpdateWkpRegularSetMonthlyActualWorkCommand implements WkpRegularSetMonthlyActualWorkGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The workplace id. */
	/* 職場ID. */
	private WorkplaceId workplaceId;

	/** The legal aggr set of reg new. */
	/* 集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpRegularSetMonthlyActualWorkGetMemento#getCompanyId()
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
	 * WkpRegularSetMonthlyActualWorkGetMemento#getWorkplaceId()
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
	 * WkpRegularSetMonthlyActualWorkGetMemento#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
