package nts.uk.ctx.at.request.pub.application.recognition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.AttendanceClock;

@Data
@AllArgsConstructor
public class OverTimeInstructExport {
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	
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
	 * 残業指示理由
	 */
	private String overtimeInstructReason;
	/**
	 * 残業時間
	 */
	private int overtimeHour;
	/**
	 * 開始時刻
	 */
	private AttendanceClock startClock;
	/**
	 * 終了時刻
	 */
	private AttendanceClock endClock;
	
	public static OverTimeInstructExport createFromJavaType(String companyID, String workContent, GeneralDate inputDate, String targetPerson,
			GeneralDate instructDate, String instructor, String overtimeInstructReason, int overtimeHour,
			AttendanceClock startClock, AttendanceClock endClock) {
		
		return new OverTimeInstructExport(companyID,
				workContent,
				inputDate,
				targetPerson,
				instructDate,
				instructor,
				overtimeInstructReason, 
				overtimeHour, 
				startClock,
				endClock);
	}
}
