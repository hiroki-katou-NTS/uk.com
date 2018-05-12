package nts.uk.ctx.at.record.dom.workrecord.managectualsituation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 社員の締め情報
 */
@Data
@AllArgsConstructor
public class EmployeeClosingInfo {
	/**
	 * 社員ID
	 */
	String employeeId;
	/**
	 * 締めID
	 */
	Integer closureId;
	/**
	 * 締め日
	 */
	GeneralDate closureDate;
	/**
	 * 年月
	 */
	Integer processDateYM;
	/**
	 * 期間
	 */
	DatePeriod duration;
}
