/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class LegalAggrSetOfRegNew.
 */
@Getter
// 通常勤務の法定内集計設定
public class LegalAggrSetOfRegNew extends DomainObject{

	/** The aggregate time set. */
	/** 割増集計方法. */ 
	private ExcessOutsideTimeSetReg aggregateTimeSet;
	
	/** The excess outside time set. */
	/** 割増集計方法. */ 
	private ExcessOutsideTimeSetReg excessOutsideTimeSet; 
	
	/**
	 * Instantiates a new legal aggr set of reg new.
	 *
	 * @param memento the memento
	 */
	public LegalAggrSetOfRegNew (LegalAggrSetOfRegNew memento) {
		this.aggregateTimeSet  = memento.getAggregateTimeSet();
		this.excessOutsideTimeSet = memento.getExcessOutsideTimeSet();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (LegalAggrSetOfRegNewSetMemento memento) {
		memento.setAggregateTimeSet(this.aggregateTimeSet);
		memento.setExcessOutsideTimeSetting(this.excessOutsideTimeSet);
	}

	
}
