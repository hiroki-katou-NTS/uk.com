/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.company;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyCalMonthlyFlex.
 */
@Getter
// フレックス会社別月別実績集計設定
public class ComFlexMonthActCalSet extends AggregateRoot implements FlexMonthActCalSet, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The aggr setting monthly of flx new. */
	// 集計設定
	private FlexMonthWorkTimeAggrSet aggrSetting;

	/**
	 * Instantiates a new company cal monthly flex.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComFlexMonthActCalSet(ComFlexMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.aggrSetting = memento.getFlexAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComFlexMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setFlexAggrSetting(this.aggrSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthActCalSet#
	 * getAggrSettings()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggregateSetting() {
		return this.aggrSetting;
	}

}
