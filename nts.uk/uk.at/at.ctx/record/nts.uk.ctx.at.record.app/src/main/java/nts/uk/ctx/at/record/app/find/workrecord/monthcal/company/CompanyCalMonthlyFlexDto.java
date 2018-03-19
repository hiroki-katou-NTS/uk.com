/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.company;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComCalMonthlyFlexSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyCalMonthlyFlexDto.
 */
@Getter
public class CompanyCalMonthlyFlexDto implements ComCalMonthlyFlexSetMemento {
	
	/** The company id. */
	private CompanyId companyId;

	/** The aggr setting monthly of flx new. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.CompanyCalMonthlyFlexSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// 
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.CompanyCalMonthlyFlexSetMemento#setAggrSettingMonthlyOfFlxNew(nts.uk.ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew)
	 */
	@Override
	public void setAggrSettingMonthlyOfFlxNew(AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew) {
		this.aggrSettingMonthlyOfFlxNew = aggrSettingMonthlyOfFlxNew;
		
	}

}
