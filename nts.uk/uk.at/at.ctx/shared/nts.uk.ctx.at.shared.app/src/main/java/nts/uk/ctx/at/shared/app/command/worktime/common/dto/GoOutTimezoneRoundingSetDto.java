/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSet;

/**
 * The Class GoOutTimezoneRoundingSetDto.
 */
@Getter
@Setter
public class GoOutTimezoneRoundingSetDto implements GoOutTimezoneRoundingSetGetMemento{
	
	/** The pub hol work timezone. */
	private GoOutTypeRoundingSetDto pubHolWorkTimezone;
	
	/** The work timezone. */
	private GoOutTypeRoundingSetDto workTimezone;
	
	/** The OT timezone. */
	private GoOutTypeRoundingSetDto ottimezone;


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSetGetMemento#getPubHolWorkTimezone()
	 */
	@Override
	public GoOutTypeRoundingSet getPubHolWorkTimezone() {
		return new GoOutTypeRoundingSet(this.pubHolWorkTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSetGetMemento#getWorkTimezone()
	 */
	@Override
	public GoOutTypeRoundingSet getWorkTimezone() {
		return new GoOutTypeRoundingSet(this.workTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSetGetMemento#getOTTimezone()
	 */
	@Override
	public GoOutTypeRoundingSet getOttimezone() {
		return new GoOutTypeRoundingSet(this.ottimezone);
	}

	
}
