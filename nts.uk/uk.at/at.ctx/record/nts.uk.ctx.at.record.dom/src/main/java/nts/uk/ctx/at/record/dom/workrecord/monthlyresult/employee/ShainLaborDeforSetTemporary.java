/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetMonthlyCalTransLabor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class EmployeeLaborDeforSetTemporary.
 */
@Getter
// 変形労働社員別月別実績集計設定.
public class ShainLaborDeforSetTemporary extends AggregateRoot implements SetMonthlyCalTransLabor {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;

	/** The legal aggr set of irg new. */
	// 変形労働時間勤務の法定内集計設定
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetMonthlyCalTransLabor
	 * #getLegalAggrSetOfIrgNew()
	 */
	@Override
	public LegalAggrSetOfIrgNew getLegalAggrSetOfIrgNew() {
		return legalAggrSetOfIrgNew;
	}

	/**
	 * Instantiates a new employee labor defor set temporary.
	 *
	 * @param memento
	 *            the memento
	 */
	public ShainLaborDeforSetTemporary(ShainLaborDeforSetTemporary memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.legalAggrSetOfIrgNew = memento.getLegalAggrSetOfIrgNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ShainLaborDeforSetTemporarySetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setLegalAggrSetOfIrgNew(this.legalAggrSetOfIrgNew);
	}

}
