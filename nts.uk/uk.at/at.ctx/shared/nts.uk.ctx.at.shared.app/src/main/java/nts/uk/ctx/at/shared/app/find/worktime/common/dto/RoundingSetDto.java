/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;

/**
 * The Class RoundingSetDto.
 */
@Getter
@Setter
@AllArgsConstructor
public class RoundingSetDto implements RoundingSetSetMemento {

	/** The rounding set. */
	private InstantRoundingDto roundingSet;

	/** The section. */
	private Integer section;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetSetMemento#
	 * setRoundingSet(nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding)
	 */
	@Override
	public void setRoundingSet(InstantRounding rounding) {
		if (rounding != null) {
			this.roundingSet = new InstantRoundingDto();
			rounding.saveToMememto(this.roundingSet);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetSetMemento#setSection
	 * (nts.uk.ctx.at.shared.dom.worktime.common.Superiority)
	 */
	@Override
	public void setSection(Superiority sec) {
		this.section = sec.value;
	}
}
