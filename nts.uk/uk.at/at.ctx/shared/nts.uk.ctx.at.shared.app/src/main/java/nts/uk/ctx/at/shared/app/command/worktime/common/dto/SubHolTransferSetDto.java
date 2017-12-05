/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento;

/**
 * The Class SubHolTransferSetDto.
 */
@Value
public class SubHolTransferSetDto implements SubHolTransferSetGetMemento {

	/** The certain time. */
	private Integer certainTime;

	/** The use division. */
	private boolean useDivision;

	/** The designated time. */
	private DesignatedTimeDto designatedTime;

	/** The sub hol transfer set atr. */
	private Integer subHolTransferSetAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getCertainTime()
	 */
	@Override
	public OneDayTime getCertainTime() {
		return new OneDayTime(this.certainTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getUseDivision()
	 */
	@Override
	public boolean getUseDivision() {
		return this.useDivision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getDesignatedTime()
	 */
	@Override
	public DesignatedTime getDesignatedTime() {
		return new DesignatedTime(this.designatedTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getSubHolTransferSetAtr()
	 */
	@Override
	public SubHolTransferSetAtr getSubHolTransferSetAtr() {
		return SubHolTransferSetAtr.valueOf(this.subHolTransferSetAtr);
	}

}
