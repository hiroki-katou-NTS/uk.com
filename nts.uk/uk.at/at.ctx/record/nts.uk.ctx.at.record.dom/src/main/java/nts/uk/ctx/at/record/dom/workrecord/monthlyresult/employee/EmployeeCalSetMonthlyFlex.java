/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetCalMonthlyFlex;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class EmployeeCalSetMonthlyFlex.
 */
@Getter
// * フレックス月別実績集計設定.
public class EmployeeCalSetMonthlyFlex extends AggregateRoot implements SetCalMonthlyFlex {

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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetCalMonthlyFlex#
	 * getAggrSettingMonthlyOfFlxNew()
	 */
	@Override
	public AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew() {
		return aggrSettingMonthlyOfFlxNew;
	}

	/**
	 * Instantiates a new employee cal set monthly flex.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmployeeCalSetMonthlyFlex(EmployeeCalSetMonthlyFlex memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.aggrSettingMonthlyOfFlxNew = memento.getAggrSettingMonthlyOfFlxNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmployeeCalSetMonthlyFlexSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setAggrSettingMonthlyOfFlxNew(this.aggrSettingMonthlyOfFlxNew);
	}

}
