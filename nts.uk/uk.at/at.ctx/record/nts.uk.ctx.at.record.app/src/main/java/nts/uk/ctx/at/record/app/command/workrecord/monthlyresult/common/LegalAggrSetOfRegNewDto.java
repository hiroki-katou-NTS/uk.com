/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.common;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNewGetMemento;

/**
 * The Class LegalAggrSetOfRegNewDto.
 */
@Getter
@Setter
public class LegalAggrSetOfRegNewDto implements LegalAggrSetOfRegNewGetMemento {

	/** The aggregate time set. */
	/* 集計時間設定. */
	private ExcessOutsideTimeSetRegDto aggregateTimeSet;

	/** The excess outside time set. */
	/* 時間外超過設定. */
	private ExcessOutsideTimeSetRegDto excessOutsideTimeSet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.
	 * LegalAggrSetOfRegNewGetMemento#getAggregateTimeSet()
	 */
	@Override
	public ExcessOutsideTimeSetReg getAggregateTimeSet() {
		return new ExcessOutsideTimeSetReg(this.aggregateTimeSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.
	 * LegalAggrSetOfRegNewGetMemento#getExcessOutsideTimeSetting()
	 */
	@Override
	public ExcessOutsideTimeSetReg getExcessOutsideTimeSetting() {
		return new ExcessOutsideTimeSetReg(this.excessOutsideTimeSet);
	}
}
