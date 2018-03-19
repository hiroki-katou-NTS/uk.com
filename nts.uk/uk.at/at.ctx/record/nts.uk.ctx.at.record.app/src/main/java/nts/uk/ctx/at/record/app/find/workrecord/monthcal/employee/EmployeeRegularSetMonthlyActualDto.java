/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShainRegularSetMonthlyActualSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class EmployeeRegularSetMonthlyActualDto.
 */
@Getter
public class EmployeeRegularSetMonthlyActualDto implements ShainRegularSetMonthlyActualSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employee id. */
	/** 社員ID. */
	private EmployeeId employeeId;

	/** The legal aggr set of reg new. */
	/** 通常勤務の法定内集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * EmployeeRegularSetMonthlyActualSetMemento#setCompanyId(nts.uk.ctx.at.
	 * shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * EmployeeRegularSetMonthlyActualSetMemento#setEmployeeId(nts.uk.ctx.at.
	 * shared.dom.common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * EmployeeRegularSetMonthlyActualSetMemento#setLegalAggrSetOfRegNew(nts.uk.
	 * ctx.at.record.dom.workrecord.monthcal.LegalAggrSetOfRegNew)
	 */
	@Override
	public void setLegalAggrSetOfRegNew(LegalAggrSetOfRegNew legalAggrSetOfRegNew) {
		this.legalAggrSetOfRegNew = legalAggrSetOfRegNew;

	}

}
