/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTimeSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;

/**
 * The Class DesignatedTimeDto.
 */
@Getter
@Setter
public class DesignatedTimeDto implements DesignatedTimeSetMemento {

	/** The one day time. */
	private Integer oneDayTime;

	/** The half day time. */
	private Integer halfDayTime;

	/**
	 * Instantiates a new designated time dto.
	 */
	public DesignatedTimeDto() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTimeSetMemento#
	 * setOneDayTime(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave
	 * .OneDayTime)
	 */
	@Override
	public void setOneDayTime(OneDayTime time) {
		this.oneDayTime = time.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTimeSetMemento#
	 * setHalfDayTime(nts.uk.ctx.at.shared.dom.vacation.setting.
	 * compensatoryleave.OneDayTime)
	 */
	@Override
	public void setHalfDayTime(OneDayTime time) {
		this.halfDayTime = time.v();
	}

}
