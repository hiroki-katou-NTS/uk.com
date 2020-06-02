/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.company;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyLaborRegSetMonthlyActual.
 */
@Getter
// 通常勤務労働会社別月別実績集計設定
public class ComRegulaMonthActCalSet extends AggregateRoot implements RegulaMonthActCalSet, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The legal aggr set of reg new. */
	// 集計設定
	private RegularWorkTimeAggrSet aggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * SetRegularActualWorkMonthly#getLegalAggrSetOfRegNew()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegulaAggrSetting() {
		return aggrSetting;
	}

	/**
	 * Instantiates a new company labor reg set monthly actual.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComRegulaMonthActCalSet(ComRegulaMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.aggrSetting = memento.getRegulaAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComRegulaMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setRegulaAggrSetting(this.aggrSetting);
	}
}
