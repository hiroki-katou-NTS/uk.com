/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.company;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComCalMonthlyFlexSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyCalMonthlyFlexDto.
 */
@Getter
public class CompanyCalMonthlyFlexDto implements ComCalMonthlyFlexSetMemento {
	
	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The aggr setting monthly of flx new. */
	/** 変形労働時間勤務の法定内集計設定. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.CompanyCalMonthlyFlexSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// 
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.CompanyCalMonthlyFlexSetMemento#setAggrSettingMonthlyOfFlxNew(nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew)
	 */
	@Override
	public void setAggrSettingMonthlyOfFlxNew(AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew) {
		this.aggrSettingMonthlyOfFlxNew = aggrSettingMonthlyOfFlxNew;
		
	}

}
