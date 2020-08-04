/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpCalSetMonthlyActualFlex.
 */
@Getter
// フレックス職場別月別実績集計設定.
public class WkpFlexMonthActCalSet extends AggregateRoot implements FlexMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The workplace id. */
	// 職場ID
	private WorkplaceId workplaceId;

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
	 * Instantiates a new wkp cal set monthly actual flex.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpFlexMonthActCalSet(WkpFlexMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.aggrSetting = memento.getFlexAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WkpFlexMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setAggrSetting(this.aggrSetting);
	}

}
