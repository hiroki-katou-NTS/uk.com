/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.common.LegalAggrSetOfRegNewDto;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComLaborRegSetMonthlyActualGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveCompanyLaborRegSetMonthlyActualCommand.
 */
@Getter
@Setter
public class SaveCompanyLaborRegSetMonthlyActualCommand implements ComLaborRegSetMonthlyActualGetMemento {

	/** The legal aggr set of reg new. */
	/* 集計設定. */
	private LegalAggrSetOfRegNewDto legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyLaborRegSetMonthlyActualGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyLaborRegSetMonthlyActualGetMemento#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		return new LegalAggrSetOfRegNew(this.legalAggrSetOfRegNew);
	}
}
