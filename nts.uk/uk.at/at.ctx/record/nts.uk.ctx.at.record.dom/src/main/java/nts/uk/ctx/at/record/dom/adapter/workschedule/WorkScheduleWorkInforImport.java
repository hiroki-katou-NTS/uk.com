package nts.uk.ctx.at.record.dom.adapter.workschedule;

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
public class WorkScheduleWorkInforImport  {

	private String employeeId;

	private int confirmedATR;

	/**
	 * 勤務情報．勤務情報．勤務種類コード
	 */
	private String workType;
	
	private String workTime;
	
	/*0 : しない区分   1: する区分*/
	private int goStraightAtr;
	/*0 : しない区分   1: する区分*/
	private int backStraightAtr;
	
	private BreakTimeOfDailyAttdImport breakTime;
	
	///** 日別勤怠の出退勤**/
	private Optional<TimeLeavingOfDailyAttdImport> timeLeaving = Optional.empty();
	
	/** 社員ID(年月日(YMD) */
	private GeneralDate ymd;
	
	/** 勤怠時間 */
	private Optional<AttendanceTimeOfDailyAttendanceImport> optAttendanceTime;
	
	private Optional<NumberOfDaySuspensionImport> numberOfDaySuspension;

	public WorkScheduleWorkInforImport(String employeeId, int confirmedATR, String workType, String workTime, int goStraightAtr, int backStraightAtr,
			TimeLeavingOfDailyAttdImport timeLeavingOfDailyAttd,
			BreakTimeOfDailyAttdImport listBreakTimeOfDailyAttdImport,
			GeneralDate ymd,
			AttendanceTimeOfDailyAttendanceImport attendanceTime,
			Optional<NumberOfDaySuspensionImport> numberOfDaySuspension) {
		super();
		this.employeeId = employeeId;
		this.confirmedATR = confirmedATR;
		this.workType = workType;
		this.workTime = workTime;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.timeLeaving = Optional.ofNullable(timeLeavingOfDailyAttd);
		this.breakTime = listBreakTimeOfDailyAttdImport;
		this.ymd = ymd;
		this.optAttendanceTime = Optional.ofNullable(attendanceTime);
		this.numberOfDaySuspension = numberOfDaySuspension;
	}
	
}
