/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto;

import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento;

/**
 * The Class CompensatoryTransferSettingDto.
 */
public class CompensatoryTransferSettingDto implements SubHolTransferSetSetMemento {

	/** The certain time. */
	public long certainTime;

	/** The use division. */
	public boolean useDivision;

	/** The one day time. */
	public long oneDayTime;

	/** The half day time. */
	public long halfDayTime;

	/** The transfer division. */
	public Integer transferDivision;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setCertainTime(nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime)
	 */
	@Override
	public void setCertainTime(OneDayTime certainTime) {
		this.certainTime = certainTime.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setUseDivision(boolean)
	 */
	@Override
	public void setUseDivision(boolean useDivision) {
		this.useDivision = useDivision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setDesignatedTime(nts.uk.ctx.at.shared.dom.worktime.common.
	 * DesignatedTime)
	 */
	@Override
	public void setDesignatedTime(DesignatedTime time) {
		this.oneDayTime = time.getOneDayTime().v();
		this.halfDayTime = time.getHalfDayTime().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setSubHolTransferSetAtr(nts.uk.ctx.at.shared.dom.worktime.common.
	 * SubHolTransferSetAtr)
	 */
	@Override
	public void setSubHolTransferSetAtr(SubHolTransferSetAtr atr) {
		this.transferDivision = atr.value;
	}
}
