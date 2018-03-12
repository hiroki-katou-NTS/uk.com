package nts.uk.ctx.at.request.dom.application.holidayinstruction;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue.OvertimeHour;
import nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue.OvertimeInstructReason;
import nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue.WorkContent;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author loivt
 * 休出指示
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HolidayInstruct{
	
	/**
	 * 作業内容
	 */
	private WorkContent workContent;
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
	private OvertimeInstructReason holidayInstructionReason;
	/**
	 * 休出時間
	 */
	private OvertimeHour hoilidayWorkHour;
	/**
	 * 開始時刻
	 */
	private AttendanceClock startClock;
	/**
	 * 終了時刻
	 */
	private AttendanceClock endClock;
	
	public static HolidayInstruct createSimpleFromJavaType(
			String workContent,Date inputDate, String targetPerson, Date instructDate,
			String instructor,String holidayInstructionReason,int hoilidayWorkHour,
			int startClock,int endClock) {
				return new HolidayInstruct(
						new WorkContent(workContent),
						GeneralDate.legacyDate(inputDate),
						targetPerson,
						GeneralDate.legacyDate(instructDate),
						instructor,
						new OvertimeInstructReason(holidayInstructionReason), 
						new OvertimeHour(hoilidayWorkHour), 
						new AttendanceClock(startClock),
						new AttendanceClock(endClock));
	}
}
