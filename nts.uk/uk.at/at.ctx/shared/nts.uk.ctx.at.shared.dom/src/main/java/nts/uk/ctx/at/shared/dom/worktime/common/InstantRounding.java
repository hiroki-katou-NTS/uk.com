/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class RoundingTime.
 */
// 時刻丸め
@Getter
@AllArgsConstructor
public class InstantRounding extends DomainObject {

	/** The font rear section. */
	// 前後区分
	private FontRearSection fontRearSection;

	/** The rounding time unit. */
	// 時刻丸め単位
	private RoundingTimeUnit roundingTimeUnit;
	
	/**
	 * Instantiates a new instant rounding.
	 *
	 * @param memento the memento
	 */
	public InstantRounding(InstantRoundingGetMemento memento) {
		this.fontRearSection = memento.getFontRearSection();
		this.roundingTimeUnit = memento.getRoundingTimeUnit();
	}
	
	/**
	 * Save to mememto.
	 *
	 * @param memento the memento
	 */
	public void saveToMememto(InstantRoundingSetMemento memento){
		memento.setFontRearSection(this.fontRearSection);
		memento.setRoundingTimeUnit(this.roundingTimeUnit);
	}
}
