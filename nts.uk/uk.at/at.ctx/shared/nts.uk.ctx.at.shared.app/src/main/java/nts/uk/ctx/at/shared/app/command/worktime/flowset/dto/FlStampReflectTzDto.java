/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ReflectRefTwoWT;

/**
 * The Class FlowStampReflectTimezoneDto.
 */
public class FlStampReflectTzDto implements FlStampReflectTzGetMemento {

	/** The two times work reflect basic time. */
	private Integer twoTimesWorkReflectBasicTime;

	// TODO: private List<StampReflectTimezoneDto> stampReflectTimezone;

	@Override
	public ReflectRefTwoWT getTwoTimesWorkReflectBasicTime() {
		return new ReflectRefTwoWT(this.twoTimesWorkReflectBasicTime);
	}

	@Override
	public List<StampReflectTimezone> getStampReflectTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

}
