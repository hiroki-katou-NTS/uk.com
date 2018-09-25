package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class BasicScheduleScreenDto {
	private String employeeId;
	private GeneralDate date;
	private String workTypeCode;
	private String workTimeCode;
	private Integer confirmedAtr;
	private Integer scheduleCnt;
	private Integer scheduleStartClock;
	private Integer scheduleEndClock;
	private Integer bounceAtr;

	public BasicScheduleScreenDto(String employeeId, GeneralDate date, String workTypeCode, String workTimeCode,
			Integer confirmedAtr) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.confirmedAtr = confirmedAtr;
	}

	public BasicScheduleScreenDto(String employeeId, GeneralDate date, Integer scheduleCnt, Integer scheduleStartClock,
			Integer scheduleEndClock, Integer bounceAtr) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.scheduleCnt = scheduleCnt;
		this.scheduleStartClock = scheduleStartClock;
		this.scheduleEndClock = scheduleEndClock;
		this.bounceAtr = bounceAtr;
	}
}
