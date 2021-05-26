/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRoundingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;

/**
 * The Class InstantRoundingDto.
 */
@Value
public class InstantRoundingDto implements InstantRoundingGetMemento {

	/** The font rear section. */
	public Integer fontRearSection;

	/** The rounding time unit. */
	public Integer roundingTimeUnit;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.InstantRoundingGetMemento#
	 * getFontRearSection()
	 */
	@Override
	public FontRearSection getFontRearSection() {
		return FontRearSection.valueOf(this.fontRearSection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.InstantRoundingGetMemento#
	 * getRoundingTimeUnit()
	 */
	@Override
	public RoundingTimeUnit getRoundingTimeUnit() {
		return RoundingTimeUnit.valueOf(this.roundingTimeUnit);
	}

}
