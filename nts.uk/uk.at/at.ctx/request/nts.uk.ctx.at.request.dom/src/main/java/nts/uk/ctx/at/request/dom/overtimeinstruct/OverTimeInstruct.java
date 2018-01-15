package nts.uk.ctx.at.request.dom.overtimeinstruct;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue.OvertimeHour;
import nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue.OvertimeInstructReason;
import nts.uk.ctx.at.request.dom.overtimeinstruct.primitivevalue.WorkContent;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author loivt
 * 残業指示
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeInstruct extends AggregateRoot{
	
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	
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
	 * 残業指示理由
	 */
	private OvertimeInstructReason overtimeInstructReason;
	/**
	 * 残業時間
	 */
	private OvertimeHour overtimeHour;
	/**
	 * 開始時刻
	 */
	private AttendanceClock startClock;
	/**
	 * 終了時刻
	 */
	private AttendanceClock endClock;
	
	public static OverTimeInstruct createSimpleFromJavaType(String companyID,
			String workContent,Date inputDate, String targetPerson, Date instructDate,
			String instructor,String overtimeInstructReason,int overtimeHour,
			int startClock,int endClock) {
				return new OverTimeInstruct(companyID,
						new WorkContent(workContent),
						GeneralDate.legacyDate(inputDate),
						targetPerson,
						GeneralDate.legacyDate(instructDate),
						instructor,
						new OvertimeInstructReason(overtimeInstructReason), 
						new OvertimeHour(overtimeHour), 
						new AttendanceClock(startClock),
						new AttendanceClock(endClock));
	}
}
