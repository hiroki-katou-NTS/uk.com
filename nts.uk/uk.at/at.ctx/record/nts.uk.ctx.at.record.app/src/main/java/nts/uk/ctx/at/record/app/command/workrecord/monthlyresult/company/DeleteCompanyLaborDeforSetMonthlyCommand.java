/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComLaborDeforSetMonthlyGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class DeleteCompanyLaborDeforSetMonthlyCommand.
 */
@Getter
@Setter
public class DeleteCompanyLaborDeforSetMonthlyCommand implements ComLaborDeforSetMonthlyGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The legal aggr set of irg new. */
	/* 集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyLaborDeforSetMonthlyGetMemento#getCompanyId()
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
	 * CompanyLaborDeforSetMonthlyGetMemento#getLegalAggrSetOfIrgNew()
	 */
	@Override
	public LegalAggrSetOfIrgNew getLegalAggrSetOfIrgNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
