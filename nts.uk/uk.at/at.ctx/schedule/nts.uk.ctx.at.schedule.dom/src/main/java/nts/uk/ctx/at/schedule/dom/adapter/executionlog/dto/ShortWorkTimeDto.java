package nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
@Setter
public class ShortWorkTimeDto {
	/** The employee id. */
	private String employeeId;

	/** The period. */
	private DatePeriod period;

	/** The child care atr. */
	private ChildCareAtr childCareAtr;

	/** The time slot. */
	private List<ShortChildCareFrameDto> lstTimeSlot;
}
