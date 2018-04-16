/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmpRegulaMonthActCalSet.
 */
@Getter
// 通常勤務雇用別月別実績集計設定.
public class EmpRegulaMonthActCalSet extends AggregateRoot implements RegulaMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employment code. */
	// 雇用コード
	private EmploymentCode employmentCode;

	/** The aggr setting. */
	// 集計設定
	private RegularWorkTimeAggrSet aggrSetting;

	/**
	 * Instantiates a new emp regula month act cal set.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpRegulaMonthActCalSet(EmpRegulaMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.aggrSetting = memento.getRegularAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpRegulaMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setAggrSetting(this.aggrSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.RegulaMonthActCalSet#
	 * getRegulaAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegulaAggrSetting() {
		return this.aggrSetting;
	}

}
