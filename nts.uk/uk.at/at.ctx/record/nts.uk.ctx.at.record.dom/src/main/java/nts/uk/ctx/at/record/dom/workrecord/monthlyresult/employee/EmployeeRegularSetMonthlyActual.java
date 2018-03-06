/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetRegularActualWorkMonthly;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class EmployeeRegularSetMonthlyActual.
 */
@Getter
// * 通常勤務社員別月別実績集計設定.
public class EmployeeRegularSetMonthlyActual extends AggregateRoot implements SetRegularActualWorkMonthly {

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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.
	 * SetRegularActualWorkMonthly#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		return legalAggrSetOfRegNew;
	}

	/**
	 * Instantiates a new employee regular set monthly actual.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmployeeRegularSetMonthlyActual(EmployeeRegularSetMonthlyActual memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.legalAggrSetOfRegNew = memento.getLegalAggrSetOfRegNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmployeeRegularSetMonthlyActualSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setLegalAggrSetOfRegNew(this.legalAggrSetOfRegNew);
	}

}
