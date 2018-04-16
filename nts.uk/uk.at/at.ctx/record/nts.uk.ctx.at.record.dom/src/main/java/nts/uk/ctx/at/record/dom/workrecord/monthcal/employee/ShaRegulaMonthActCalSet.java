/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class EmployeeRegularSetMonthlyActual.
 */
@Getter
// 通常勤務社員別月別実績集計設定.
public class ShaRegulaMonthActCalSet extends AggregateRoot implements RegulaMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;

	/** The legal aggr set of reg new. */
	// 集計設定
	private RegularWorkTimeAggrSet aggrSetting;

	/**
	 * Instantiates a new employee regular set monthly actual.
	 *
	 * @param memento
	 *            the memento
	 */
	public ShaRegulaMonthActCalSet(ShaRegulaMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.aggrSetting = memento.getRegularAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ShaRegulaMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setAggrSetting(this.aggrSetting);
	}

	@Override
	public RegularWorkTimeAggrSet getRegulaAggrSetting() {
		return this.aggrSetting;
	}

}
