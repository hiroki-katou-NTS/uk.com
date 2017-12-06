/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;

/**
 * The Class RoundingSetDto.
 */
@Value
public class RoundingSetDto implements RoundingSetGetMemento {

	/** The rounding set. */
	private InstantRoundingDto roundingSet;

	/** The section. */
	private Integer section;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetGetMemento#
	 * getRoundingSet()
	 */
	@Override
	public InstantRounding getRoundingSet() {
		return new InstantRounding(this.roundingSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetGetMemento#getSection
	 * ()
	 */
	@Override
	public Superiority getSection() {
		return Superiority.valueOf(this.section);
	}
}
