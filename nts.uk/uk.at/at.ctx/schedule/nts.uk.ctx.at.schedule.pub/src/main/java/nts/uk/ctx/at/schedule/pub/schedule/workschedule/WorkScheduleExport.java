package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkScheduleExport  {

	private String employeeId;

	private int confirmedATR;
	
	private String workType;
	
	private String workTime;
	
	private Optional<NumberOfDaySuspensionExport> numberDaySuspension = Optional.empty();
	
	/*0 : しない区分   1: する区分*/
	private int goStraightAtr;
	/*0 : しない区分   1: する区分*/
	private int backStraightAtr;
	
	private Optional<TimeLeavingOfDailyAttdExport> timeLeavingOfDailyAttd = Optional.empty();
	
	private Optional<BreakTimeOfDailyAttdExport> listBreakTimeOfDaily = Optional.empty();
	
	/** 社員ID(年月日(YMD) */
	private GeneralDate ymd;
	
	/** 勤怠時間 */
	private Optional<AttendanceTimeOfDailyAttendanceExport> optAttendanceTime;
	
	public WorkScheduleExport(String employeeId, int confirmedATR, String workType, String workTime, int goStraightAtr, int backStraightAtr,
			TimeLeavingOfDailyAttdExport timeLeavingOfDailyAttd,Optional<BreakTimeOfDailyAttdExport> listBreakTimeOfDaily,Optional<NumberOfDaySuspensionExport> numberDaySuspension) {
		super();
		this.employeeId = employeeId;
		this.confirmedATR = confirmedATR;
		this.workType = workType;
		this.workTime = workTime;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.timeLeavingOfDailyAttd = Optional.ofNullable(timeLeavingOfDailyAttd);
		this.listBreakTimeOfDaily = listBreakTimeOfDaily;
		this.numberDaySuspension = numberDaySuspension;
	}

	
	
}
