/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ReflectRefTwoWT;

/**
 * The Class FlStampReflectTzDto.
 */
@Value
public class FlStampReflectTzDto implements FlStampReflectTzGetMemento {

	/** The two times work reflect basic time. */
	private Integer twoTimesWorkReflectBasicTime;

	/** The stamp reflect timezone. */
	private List<StampReflectTimezoneDto> stampReflectTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTzGetMemento#
	 * getTwoTimesWorkReflectBasicTime()
	 */
	@Override
	public ReflectRefTwoWT getTwoTimesWorkReflectBasicTime() {
		return new ReflectRefTwoWT(this.twoTimesWorkReflectBasicTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTzGetMemento#
	 * getStampReflectTimezone()
	 */
	@Override
	public List<StampReflectTimezone> getStampReflectTimezone() {
		return this.stampReflectTimezone.stream().map(item -> new StampReflectTimezone(item))
				.collect(Collectors.toList());
	}

}
