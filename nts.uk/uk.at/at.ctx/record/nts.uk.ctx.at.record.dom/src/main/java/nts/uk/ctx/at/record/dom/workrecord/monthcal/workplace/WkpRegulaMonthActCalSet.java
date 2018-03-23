/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpRegulaMonthActCalSet.
 */
@Getter
// 通常勤務職場別月別実績集計設定
public class WkpRegulaMonthActCalSet extends AggregateRoot implements RegulaMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The workplace id. */
	// 職場ID
	private WorkplaceId workplaceId;

	/** The aggr setting. */
	// 集計設定
	private RegularWorkTimeAggrSet aggrSetting;

	/**
	 * Instantiates a new wkp regula month act cal set.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpRegulaMonthActCalSet(WkpRegulaMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.aggrSetting = memento.getRegularAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WkpRegulaMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setAggrSetting(this.aggrSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.RegulaMonthActCalSet#getRegulaAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegulaAggrSetting() {
		return this.aggrSetting;
	}

}
