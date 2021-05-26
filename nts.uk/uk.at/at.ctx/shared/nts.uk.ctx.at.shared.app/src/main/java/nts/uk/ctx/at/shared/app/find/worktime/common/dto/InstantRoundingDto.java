/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRoundingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;

/**
 * The Class InstantRoundingDto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstantRoundingDto implements InstantRoundingSetMemento{
	
	/** The font rear section. */
	private Integer fontRearSection;

	/** The rounding time unit. */
	private Integer roundingTimeUnit;

	/**
	 * Sets the font rear section.
	 *
	 * @param fontRearSection the new font rear section
	 */
	@Override
	public void setFontRearSection(FontRearSection fontRearSection) {
		this.fontRearSection = fontRearSection.value;
	}

	/**
	 * Sets the rounding time unit.
	 *
	 * @param roundingTimeUnit the new rounding time unit
	 */
	@Override
	public void setRoundingTimeUnit(RoundingTimeUnit roundingTimeUnit) {
		this.roundingTimeUnit = roundingTimeUnit.value;
	}	
	
}
