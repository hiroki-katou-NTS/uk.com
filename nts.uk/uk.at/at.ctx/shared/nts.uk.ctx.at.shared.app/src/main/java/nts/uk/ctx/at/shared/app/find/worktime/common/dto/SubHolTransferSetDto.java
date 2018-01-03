/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento;

/**
 * The Class SubHolTransferSetDto.
 */
@Getter
@Setter
public class SubHolTransferSetDto implements SubHolTransferSetSetMemento{

	/** The certain time. */
	private Integer certainTime;
	
	/** The use division. */
	private boolean useDivision;
	
	/** The designated time. */
	private DesignatedTimeDto designatedTime;
	
	/** The sub hol transfer set atr. */
	private Integer subHolTransferSetAtr;

	public SubHolTransferSetDto() {
		this.designatedTime = new DesignatedTimeDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setCertainTime(nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime)
	 */
	@Override
	public void setCertainTime(OneDayTime time) {
		this.certainTime = time.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setUseDivision(boolean)
	 */
	@Override
	public void setUseDivision(boolean val) {
		this.useDivision = val;
	}

	@Override
	public void setDesignatedTime(DesignatedTime time) {
		time.saveToMemento(this.designatedTime);
	}

	@Override
	public void setSubHolTransferSetAtr(SubHolTransferSetAtr atr) {
		this.subHolTransferSetAtr = atr.value;
	}

	

}
