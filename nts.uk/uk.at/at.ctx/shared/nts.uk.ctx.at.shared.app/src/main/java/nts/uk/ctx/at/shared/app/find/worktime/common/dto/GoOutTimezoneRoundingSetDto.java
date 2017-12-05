/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSet;

/**
 * The Class GoOutTimezoneRoundingSetDto.
 */
@Getter
@Setter
public class GoOutTimezoneRoundingSetDto implements GoOutTimezoneRoundingSetSetMemento{
	
	/** The pub hol work timezone. */
	private GoOutTypeRoundingSetDto pubHolWorkTimezone;
	
	/** The work timezone. */
	private GoOutTypeRoundingSetDto workTimezone;
	
	/** The OT timezone. */
	private GoOutTypeRoundingSetDto oTTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setPubHolWorkTimezone(nts.uk.ctx.at.
	 * shared.dom.worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setPubHolWorkTimezone(GoOutTypeRoundingSet pubHolWorkTimezone) {
		pubHolWorkTimezone.saveToMemento(this.pubHolWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setWorkTimezone(nts.uk.ctx.at.shared.
	 * dom.worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setWorkTimezone(GoOutTypeRoundingSet workTimezone) {
		workTimezone.saveToMemento(this.workTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setOTTimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setOTTimezone(GoOutTypeRoundingSet OTTimezone) {
		OTTimezone.saveToMemento(this.oTTimezone);
	}

	
}
