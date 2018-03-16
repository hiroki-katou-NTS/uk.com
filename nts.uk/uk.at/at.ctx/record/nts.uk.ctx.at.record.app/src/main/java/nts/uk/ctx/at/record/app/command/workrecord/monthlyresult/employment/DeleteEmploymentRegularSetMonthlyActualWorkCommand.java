/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmpRegularSetMonthlyActualWorkGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class DeleteEmploymentRegularSetMonthlyActualWorkCommand.
 */
@Getter
@Setter
public class DeleteEmploymentRegularSetMonthlyActualWorkCommand
		implements EmpRegularSetMonthlyActualWorkGetMemento {

	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	/* 雇用コード. */
	private EmploymentCode employmentCode;

	/** The legal aggr set of reg new. */
	/* 集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.
	 * EmploymentRegularSetMonthlyActualWorkGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.
	 * EmploymentRegularSetMonthlyActualWorkGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.
	 * EmploymentRegularSetMonthlyActualWorkGetMemento#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
