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
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ReflectReferenceTwoWorkTime;

/**
 * The Class FlStampReflectTzDto.
 */
@Value
public class FlStampReflectTzDto implements FlowStampReflectTzGetMemento {

	/** The two times work reflect basic time. */
	private Integer twoTimesWorkReflectBasicTime;

	/** The stamp reflect timezones. */
	private List<StampReflectTimezoneDto> stampReflectTimezones;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTzGetMemento#
	 * getTwoTimesWorkReflectBasicTime()
	 */
	@Override
	public ReflectReferenceTwoWorkTime getTwoTimesWorkReflectBasicTime() {
		return new ReflectReferenceTwoWorkTime(this.twoTimesWorkReflectBasicTime);
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
		return this.stampReflectTimezones.stream().map(item -> new StampReflectTimezone(item))
				.collect(Collectors.toList());
	}

}
