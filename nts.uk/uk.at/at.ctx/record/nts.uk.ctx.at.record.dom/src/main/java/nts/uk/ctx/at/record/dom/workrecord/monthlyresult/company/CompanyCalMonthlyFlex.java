/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetCalMonthlyFlex;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyCalMonthlyFlex.
 */
@Getter
// * フレックス会社別月別実績集計設定.
public class CompanyCalMonthlyFlex extends AggregateRoot implements SetCalMonthlyFlex {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The aggr setting monthly of flx new. */
	/** 変形労働時間勤務の法定内集計設定. */
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
	 * Instantiates a new company cal monthly flex.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompanyCalMonthlyFlex(CompanyCalMonthlyFlex memento) {
		this.companyId = memento.getCompanyId();
		this.aggrSettingMonthlyOfFlxNew = memento.getAggrSettingMonthlyOfFlxNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompanyCalMonthlyFlexSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setAggrSettingMonthlyOfFlxNew(this.aggrSettingMonthlyOfFlxNew);
	}

}
