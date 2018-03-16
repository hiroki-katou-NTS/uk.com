/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.company;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComLaborRegSetMonthlyActualSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyLaborRegSetMonthlyActualDto.
 */
@Getter
public class CompanyLaborRegSetMonthlyActualDto implements ComLaborRegSetMonthlyActualSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The legal aggr set of reg new. */
	/** 通常勤務の法定内集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyLaborRegSetMonthlyActualSetMemento#setCompanyId(nts.uk.ctx.at.
	 * shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// no 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.
	 * CompanyLaborRegSetMonthlyActualSetMemento#setLegalAggrSetOfRegNew(nts.uk.
	 * ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew)
	 */
	@Override
	public void setLegalAggrSetOfRegNew(LegalAggrSetOfRegNew legalAggrSetOfRegNew) {
		this.legalAggrSetOfRegNew = legalAggrSetOfRegNew;
	}

}
