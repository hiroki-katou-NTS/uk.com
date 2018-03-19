/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShainCalSetMonthlyFlexSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class EmployeeCalSetMonthlyFlexDto.
 */
@Getter
public class EmployeeCalSetMonthlyFlexDto implements ShainCalSetMonthlyFlexSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employee id. */
	/** 社員ID. */
	private EmployeeId employeeId;

	/** The aggr setting monthly of flx new. */
	/** フレックス時間勤務の月の集計設定. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * EmployeeCalSetMonthlyFlexSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom
	 * .common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * EmployeeCalSetMonthlyFlexSetMemento#setEmployeeId(nts.uk.ctx.at.shared.
	 * dom.common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * EmployeeCalSetMonthlyFlexSetMemento#setAggrSettingMonthlyOfFlxNew(nts.uk.
	 * ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew)
	 */
	@Override
	public void setAggrSettingMonthlyOfFlxNew(AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew) {
		this.aggrSettingMonthlyOfFlxNew = aggrSettingMonthlyOfFlxNew;
	}

}
