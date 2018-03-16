/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.common.AggrSettingMonthlyOfFlxNewDto;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.ShainCalSetMonthlyFlexGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateEmployeeCalSetMonthlyFlexCommand.
 */
@Getter
@Setter
public class UpdateEmployeeCalSetMonthlyFlexCommand implements ShainCalSetMonthlyFlexGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The employee id. */
	/* 社員ID. */
	private EmployeeId employeeId;

	/** The aggr setting monthly of flx new. */
	/* フレックス時間勤務の月の集計設定. */
	private AggrSettingMonthlyOfFlxNewDto aggrSettingMonthlyOfFlxNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.
	 * EmployeeCalSetMonthlyFlexGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.
	 * EmployeeCalSetMonthlyFlexGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return this.employeeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.
	 * EmployeeCalSetMonthlyFlexGetMemento#getAggrSettingMonthlyOfFlxNew()
	 */
	@Override
	public AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew() {
		return new AggrSettingMonthlyOfFlxNew(this.aggrSettingMonthlyOfFlxNew);
	}

}
