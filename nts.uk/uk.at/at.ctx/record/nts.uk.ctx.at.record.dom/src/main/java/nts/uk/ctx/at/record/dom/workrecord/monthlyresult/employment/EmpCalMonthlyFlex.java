/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetCalMonthlyFlex;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentCalMonthlyFlex.
 */
@Getter
// * フレックス雇用別月別実績集計設定.
public class EmpCalMonthlyFlex extends AggregateRoot implements SetCalMonthlyFlex {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	/** 雇用コード. */
	private EmploymentCode employmentCode;

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
	 * Instantiates a new employment cal monthly flex.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpCalMonthlyFlex(EmpCalMonthlyFlex memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.aggrSettingMonthlyOfFlxNew = memento.getAggrSettingMonthlyOfFlxNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpCalMonthlyFlexSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setAggrSettingMonthlyOfFlxNew(this.aggrSettingMonthlyOfFlxNew);
	}

}
