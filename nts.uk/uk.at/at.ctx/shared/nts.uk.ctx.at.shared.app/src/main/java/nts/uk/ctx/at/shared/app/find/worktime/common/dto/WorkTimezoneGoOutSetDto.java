/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetSetMemento;

/**
 * The Class WorkTimezoneGoOutSetDto.
 */
@Getter
@Setter
public class WorkTimezoneGoOutSetDto implements WorkTimezoneGoOutSetSetMemento{
	
	/** The rounding method. */
	private Integer roundingMethod;
	
	/** The diff timezone setting. */
	private GoOutTimezoneRoundingSetDto diffTimezoneSetting;

	/**
	 * Instantiates a new work timezone go out set dto.
	 */
	public WorkTimezoneGoOutSetDto() {
		this.roundingMethod = 0;
		this.diffTimezoneSetting = new GoOutTimezoneRoundingSetDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetGetMemento#
	 * getTotalRoundingSet()
	 */
	public TotalRoundingSetDto getTotalRoundingSet() {
		return new TotalRoundingSetDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetSetMemento#
	 * setRoundingMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimeRoundingMethod)
	 */
	@Override
	public void setRoundingMethod(GoOutTimeRoundingMethod set) {
		this.roundingMethod = set.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetSetMemento#
	 * setDiffTimezoneSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSet)
	 */
	@Override
	public void setDiffTimezoneSetting(GoOutTimezoneRoundingSet set) {
		set.saveToMememto(this.diffTimezoneSetting);
	}

	

}
