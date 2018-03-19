/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.company;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComLaborDeforSetMonthlySetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyLaborDeforSetMonthlyDto.
 */
@Getter
public class CompanyLaborDeforSetMonthlyDto implements ComLaborDeforSetMonthlySetMemento{

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The legal aggr set of irg new. */
	/** 変形労働時間勤務の法定内集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;

	@Override
	public void setCompanyId(CompanyId companyId) {
		// 
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.CompanyLaborDeforSetMonthlySetMemento#setLegalAggrSetOfIrgNew(nts.uk.ctx.at.record.dom.workrecord.monthcal.LegalAggrSetOfIrgNew)
	 */
	@Override
	public void setLegalAggrSetOfIrgNew(LegalAggrSetOfIrgNew legalAggrSetOfIrgNew) {
		this.legalAggrSetOfIrgNew = legalAggrSetOfIrgNew;	
	}

}
