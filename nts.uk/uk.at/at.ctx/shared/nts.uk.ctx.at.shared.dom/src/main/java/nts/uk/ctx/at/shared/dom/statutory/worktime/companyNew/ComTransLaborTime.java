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
 * The Class CompanyTransLaborHour.
 */
// 会社別変形労働労働時間
@Getter
public class ComTransLaborTime extends AggregateRoot implements StatutoryWorkTimeSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The working time setting new. */
	// 会社労働時間設定
	private WorkingTimeSetting workingTimeSet;

	/**
	 * Instantiates a new company trans labor hour.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComTransLaborTime(ComTransLaborTimeGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workingTimeSet = memento.getWorkingTimeSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComTransLaborTimeSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkingTimeSet(this.workingTimeSet);
	}

}
