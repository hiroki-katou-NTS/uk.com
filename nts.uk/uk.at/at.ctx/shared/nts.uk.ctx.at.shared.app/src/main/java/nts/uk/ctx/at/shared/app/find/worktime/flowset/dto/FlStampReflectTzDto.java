/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTzSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ReflectReferenceTwoWorkTime;

/**
 * The Class FlowStampReflectTimezoneDto.
 */
@Getter
@Setter
public class FlStampReflectTzDto implements FlowStampReflectTzSetMemento {

	/** The two times work reflect basic time. */
	private Integer twoTimesWorkReflectBasicTime;

	/** The stamp reflect timezones. */
	private List<StampReflectTimezoneDto> stampReflectTimezones;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowStampReflectTimezoneSetMemento#setTwoTimesWorkReflectBasicTime(nts.uk
	 * .ctx.at.shared.dom.worktime.flowset.ReflectReferenceTwoWorkTime)
	 */
	@Override
	public void setTwoTimesWorkReflectBasicTime(ReflectReferenceTwoWorkTime rtwt) {
		this.twoTimesWorkReflectBasicTime = rtwt.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowStampReflectTimezoneSetMemento#setStampReflectTimezone(java.util.
	 * List)
	 */
	@Override
	public void setStampReflectTimezone(List<StampReflectTimezone> lstRtz) {
		this.stampReflectTimezones = lstRtz.stream().map(item -> {
			StampReflectTimezoneDto dto = new StampReflectTimezoneDto();
			dto.setWorkNo(item.getWorkNo());
			dto.setClassification(item.getClassification());
			dto.setStartTime(item.getStartTime());
			dto.setEndTime(item.getEndTime());
			return dto;
		}).collect(Collectors.toList());
	}
}
