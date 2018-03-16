/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComLaborRegSetMonthlyActualGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class UpdateCompanyLaborRegSetMonthlyActualCommand.
 */
@Getter
@Setter
public class UpdateCompanyLaborRegSetMonthlyActualCommand implements ComLaborRegSetMonthlyActualGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The legal aggr set of reg new. */
	/* 集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyLaborRegSetMonthlyActualGetMemento#getCompanyId()
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
	 * CompanyLaborRegSetMonthlyActualGetMemento#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
