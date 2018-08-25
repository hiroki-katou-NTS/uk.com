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
	private int confirmedAtr;
	private int scheduleCnt;
	private int scheduleStartClock;
	private int scheduleEndClock;
	private int bounceAtr;
	private String employmentCd;
	private String classificationCd;
	private String businessTypeCd;
	private String jobId;
	private String workplaceId;

	public BasicScheduleScreenDto(String employeeId, GeneralDate date, String workTypeCode, String workTimeCode,
			int confirmedAtr) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.confirmedAtr = confirmedAtr;
	}

	public BasicScheduleScreenDto(String employeeId, GeneralDate date, int scheduleCnt, int scheduleStartClock,
			int scheduleEndClock, int bounceAtr) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.scheduleCnt = scheduleCnt;
		this.scheduleStartClock = scheduleStartClock;
		this.scheduleEndClock = scheduleEndClock;
		this.bounceAtr = bounceAtr;
	}
}
