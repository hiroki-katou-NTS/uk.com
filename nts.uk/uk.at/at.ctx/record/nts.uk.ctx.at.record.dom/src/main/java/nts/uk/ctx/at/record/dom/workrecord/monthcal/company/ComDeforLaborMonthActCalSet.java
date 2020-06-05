/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.company;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class ComDeforLaborMonthActCalSet.
 */
@Getter
// 変形労働会社別月別実績集計設定
public class ComDeforLaborMonthActCalSet extends AggregateRoot implements DeforLaborMonthActCalSet, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The aggr setting. */
	// 集計設定
	private DeforWorkTimeAggrSet aggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.SetMonthlyCalTransLabor
	 * #getLegalAggrSetOfIrgNew()
	 */
	@Override
	public DeforWorkTimeAggrSet getAggregateSetting() {
		return aggrSetting;
	}

	/**
	 * Instantiates a new com defor labor month act cal set.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComDeforLaborMonthActCalSet(ComDeforLaborMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.aggrSetting = memento.getDeforAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComDeforLaborMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setDeforAggrSetting(this.aggrSetting);
	}

}
