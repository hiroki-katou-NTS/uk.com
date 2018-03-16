/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.DeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyLaborDeforSetMonthly.
 */
@Getter
// 変形労働会社別月別実績集計設定
public class ComDeforLaborMonthActCalSet extends AggregateRoot implements DeforLaborMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The legal aggr set of irg new. */
	// 集計設定
	private DeforWorkTimeAggrSet aggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetMonthlyCalTransLabor
	 * #getLegalAggrSetOfIrgNew()
	 */
	@Override
	public DeforWorkTimeAggrSet getAggregateSetting() {
		return aggrSetting;
	}

	/**
	 * Instantiates a new company labor defor set monthly.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComDeforLaborMonthActCalSet(ComDeforLaborMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.aggrSetting = memento.getAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComDeforLaborMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setAggrSetting(this.aggrSetting);
	}

}
