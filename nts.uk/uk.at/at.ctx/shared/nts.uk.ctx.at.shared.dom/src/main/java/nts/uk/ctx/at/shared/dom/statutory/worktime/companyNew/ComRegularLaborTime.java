/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.StatutoryWorkTimeSet;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;

/**
 * The Class CompanyRegularLaborHour.
 */
// 会社別通常勤務労働時間
@Getter
public class ComRegularLaborTime extends AggregateRoot implements StatutoryWorkTimeSet {

	// 会社ID
	/** The company id. */
	private CompanyId companyId;

	// 会社労働時間設定
	/** The working time setting new. */
	private WorkingTimeSetting workingTimeSet;

	/**
	 * Instantiates a new company regular labor hour.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComRegularLaborTime(ComRegularLaborTimeGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workingTimeSet = memento.getWorkingTimeSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComRegularLaborTimeSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkingTimeSet(this.workingTimeSet);
	}
		
}
