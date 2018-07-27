package nts.uk.ctx.at.request.pub.application.recognition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.AttendanceClock;

@Data
@AllArgsConstructor
public class HolidayInstructExport {
	/**
	 * 作業内容
	 */
	private String workContent;
	/**
	 * 入力日付
	 */
	private GeneralDate inputDate;
	/**
	 * 対象者
	 */
	private String targetPerson;
	/**
	 * 指示日付
	 */
	private GeneralDate instructDate;
	/**
	 * 指示者
	 */
	private String instructor;
	/**
	 * 休出指示理由
	 */
	private String holidayInstructionReason;
	/**
	 * 休出時間
	 */
	private int hoilidayWorkHour;
	/**
	 * 開始時刻
	 */
	private AttendanceClock startClock;
	/**
	 * 終了時刻
	 */
	private AttendanceClock endClock;
	
	public static HolidayInstructExport createFromJavaType(String workContent, GeneralDate inputDate, String targetPerson, GeneralDate instructDate,
			String instructor, String holidayInstructionReason, int hoilidayWorkHour, AttendanceClock startClock, AttendanceClock endClock) {
		
				return new HolidayInstructExport(
						workContent,
						inputDate,
						targetPerson,
						instructDate,
						instructor,
						holidayInstructionReason, 
						hoilidayWorkHour, 
						startClock,
						endClock);
	}
}
