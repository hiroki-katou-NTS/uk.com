/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.ShainRegularSetMonthlyActualGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class DeleteEmployeeRegularSetMonthlyActualCommand.
 */
@Getter
@Setter
public class DeleteEmployeeRegularSetMonthlyActualCommand implements ShainRegularSetMonthlyActualGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The employee id. */
	/* 社員ID. */
	private EmployeeId employeeId;

	/** The legal aggr set of reg new. */
	/* 通常勤務の法定内集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.
	 * EmployeeRegularSetMonthlyActualGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.
	 * EmployeeRegularSetMonthlyActualGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.
	 * EmployeeRegularSetMonthlyActualGetMemento#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
