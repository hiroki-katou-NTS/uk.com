package nts.uk.screen.at.app.dailyperformance.correction.dto.month;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendenceTimeMonthDto {

	/** 社員ID */
	private  String employeeId;
	/** 年月 */
	private  YearMonth yearMonth;
	/** 締めID */
	private  ClosureId closureId;
	/** 締め日付 */
	private  ClosureDate closureDate;
}
