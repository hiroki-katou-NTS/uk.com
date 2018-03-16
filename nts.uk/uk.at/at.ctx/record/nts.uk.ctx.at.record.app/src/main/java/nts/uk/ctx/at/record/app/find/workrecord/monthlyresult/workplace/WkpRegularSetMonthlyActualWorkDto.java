/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.workplace;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.WkpRegularSetMonthlyActualWorkSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpRegularSetMonthlyActualWorkDto.
 */
@Getter
public class WkpRegularSetMonthlyActualWorkDto implements WkpRegularSetMonthlyActualWorkSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The workplace id. */
	/** 職場ID. */
	private WorkplaceId workplaceId;

	/** The legal aggr set of reg new. */
	/** 通常勤務の法定内集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpRegularSetMonthlyActualWorkSetMemento#setCompanyId(nts.uk.ctx.at.
	 * shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpRegularSetMonthlyActualWorkSetMemento#setWorkplaceId(nts.uk.ctx.at.
	 * shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.
	 * WkpRegularSetMonthlyActualWorkSetMemento#setLegalAggrSetOfRegNew(nts.uk.
	 * ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew)
	 */
	@Override
	public void setLegalAggrSetOfRegNew(LegalAggrSetOfRegNew legalAggrSetOfRegNew) {
		this.legalAggrSetOfRegNew = legalAggrSetOfRegNew;
	}
}
