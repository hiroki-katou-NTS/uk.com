/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class ShainFlexMonthActCalSet.
 */
@Getter
// フレックス月別実績集計設定.
public class ShaFlexMonthActCalSet extends AggregateRoot implements FlexMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;

	/** The aggr setting. */
	// 集計設定
	private FlexMonthWorkTimeAggrSet aggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.SetCalMonthlyFlex#
	 * getAggrSettingMonthlyOfFlxNew()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggregateSetting() {
		return aggrSetting;
	}

	/**
	 * Instantiates a new shain flex month act cal set.
	 *
	 * @param memento
	 *            the memento
	 */
	public ShaFlexMonthActCalSet(ShaFlexMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.aggrSetting = memento.getFlexAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ShaFlexMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setAggrSetting(this.aggrSetting);
	}

}
