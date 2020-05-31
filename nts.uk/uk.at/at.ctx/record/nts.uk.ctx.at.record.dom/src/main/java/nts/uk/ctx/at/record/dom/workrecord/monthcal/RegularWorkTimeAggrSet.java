/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class LegalAggrSetOfRegNew.
 */
@Getter
// 通常勤務の法定内集計設定
public class RegularWorkTimeAggrSet extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The aggregate time set. */
	// 集計時間設定
	private ExcessOutsideTimeSetReg aggregateTimeSet;

	/** The excess outside time set. */
	// 時間外超過設定
	private ExcessOutsideTimeSetReg excessOutsideTimeSet;

	/**
	 * Instantiates a new aggr setting monthly of flx new.
	 *
	 * @param memento
	 *            the memento
	 */
	public RegularWorkTimeAggrSet(RegularWorkTimeAggrSetGetMemento memento) {
		this.aggregateTimeSet = memento.getAggregateTimeSet();
		this.excessOutsideTimeSet = memento.getExcessOutsideTimeSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(RegularWorkTimeAggrSetSetMemento memento) {
		memento.setAggregateTimeSet(this.aggregateTimeSet);
		memento.setExcessOutsideTimeSet(this.excessOutsideTimeSet);
	}
}
