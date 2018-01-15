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
	private GoOutTypeRoundingSetDto ottimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setPubHolWorkTimezone(nts.uk.ctx.at.
	 * shared.dom.worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setPubHolWorkTimezone(GoOutTypeRoundingSet pubHolWorkTimezone) {
		if (pubHolWorkTimezone != null) {
			this.pubHolWorkTimezone = new GoOutTypeRoundingSetDto();
			pubHolWorkTimezone.saveToMemento(this.pubHolWorkTimezone);
		}
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
		if (workTimezone != null) {
			this.workTimezone = new GoOutTypeRoundingSetDto();
			workTimezone.saveToMemento(this.workTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setOTTimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setOttimezone(GoOutTypeRoundingSet ottimezone) {
		if (ottimezone != null) {
			this.ottimezone = new GoOutTypeRoundingSetDto();
			ottimezone.saveToMemento(this.ottimezone);
		}
	}

	
}
