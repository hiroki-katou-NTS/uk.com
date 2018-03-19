/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.workplace;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpTransLaborSetMonthlySetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpTransLaborSetMonthlyDto.
 */
@Getter
public class WkpTransLaborSetMonthlyDto implements WkpTransLaborSetMonthlySetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The workplace id. */
	/** 職場ID. */
	private WorkplaceId workplaceId;

	/** The legal aggr set of irg new. */
	/** 変形労働時間勤務の法定内集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpTransLaborSetMonthlySetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpTransLaborSetMonthlySetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom
	 * .common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpTransLaborSetMonthlySetMemento#setLegalAggrSetOfIrgNew(nts.uk.ctx.at.
	 * record.dom.workrecord.monthcal.LegalAggrSetOfIrgNew)
	 */
	@Override
	public void setLegalAggrSetOfIrgNew(LegalAggrSetOfIrgNew legalAggrSetOfIrgNew) {
		this.legalAggrSetOfIrgNew = legalAggrSetOfIrgNew;

	}

}
