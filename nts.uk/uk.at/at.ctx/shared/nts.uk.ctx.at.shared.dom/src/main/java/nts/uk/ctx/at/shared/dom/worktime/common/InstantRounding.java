/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class RoundingTime.
 */
// 時刻丸め
@Getter
public class InstantRounding extends WorkTimeDomainObject {

	/** The font rear section. */
	// 前後区分
	private FontRearSection fontRearSection;

	/** The rounding time unit. */
	// 時刻丸め単位
	private RoundingTimeUnit roundingTimeUnit;

	/**
	 * Instantiates a new instant rounding.
	 *
	 * @param fontRearSection
	 *            the font rear section
	 * @param roundingTimeUnit
	 *            the rounding time unit
	 */
	public InstantRounding(FontRearSection fontRearSection, RoundingTimeUnit roundingTimeUnit) {
		super();
		this.fontRearSection = fontRearSection;
		this.roundingTimeUnit = roundingTimeUnit;
	}

	/**
	 * Instantiates a new instant rounding.
	 *
	 * @param memento
	 *            the memento
	 */
	public InstantRounding(InstantRoundingGetMemento memento) {
		this.fontRearSection = memento.getFontRearSection();
		this.roundingTimeUnit = memento.getRoundingTimeUnit();
	}

	/**
	 * Save to mememto.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMememto(InstantRoundingSetMemento memento) {
		memento.setFontRearSection(this.fontRearSection);
		memento.setRoundingTimeUnit(this.roundingTimeUnit);
	}

}
