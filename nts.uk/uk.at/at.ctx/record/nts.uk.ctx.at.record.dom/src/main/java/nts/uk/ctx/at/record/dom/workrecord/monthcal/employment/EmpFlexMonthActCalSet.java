/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentCalMonthlyFlex.
 */
@Getter
// フレックス雇用別月別実績集計設定.
public class EmpFlexMonthActCalSet extends AggregateRoot implements FlexMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employment code. */
	// 雇用コード
	private EmploymentCode employmentCode;

	/** The aggr setting monthly of flx new. */
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
	 * Instantiates a new employment cal monthly flex.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpFlexMonthActCalSet(EmpFlexMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.aggrSetting = memento.getFlexAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpFlexMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setAggrSetting(this.aggrSetting);
	}

}
