/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpTransLaborSetMonthly.
 */
@Getter
// 変形労働職場別月別実績集計設定
public class WkpDeforLaborMonthActCalSet extends AggregateRoot implements DeforLaborMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The workplace id. */
	// 職場ID
	private WorkplaceId workplaceId;

	/** The legal aggr set of irg new. */
	// 集計設定
	private DeforWorkTimeAggrSet aggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.monthcal.SetMonthlyCalTransLabor
	 * #getLegalAggrSetOfIrgNew()
	 */
	@Override
	public DeforWorkTimeAggrSet getAggregateSetting() {
		return aggrSetting;
	}

	/**
	 * Instantiates a new wkp trans labor set monthly.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpDeforLaborMonthActCalSet(WkpDeforLaborMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.aggrSetting = memento.getDeforAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WkpDeforLaborMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setAggrSetting(this.aggrSetting);
	}

}
