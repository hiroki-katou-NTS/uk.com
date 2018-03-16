/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.common.AggrSettingMonthlyOfFlxNewDto;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company.ComCalMonthlyFlexGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanyCalMonthlyFlexCommand.
 */
@Getter
@Setter
public class SaveCompanyCalMonthlyFlexCommand implements ComCalMonthlyFlexGetMemento{
	
	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The aggr setting monthly of flx new. */
	/* 集計設定. */
	private AggrSettingMonthlyOfFlxNewDto aggrSettingMonthlyOfFlxNew;


	@Override 
	public CompanyId getCompanyId() {	
		return new CompanyId(AppContexts.user().companyId());
	}

	@Override
	public AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew() {
		return null;
	}

}
