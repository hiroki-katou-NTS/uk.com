package nts.uk.ctx.workflow.pub.resultrecord.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@AllArgsConstructor
@Getter
public class AppRootSttMonthExport {
	/**
	 * 承認対象者
	 */
	private String employeeID;
	/**
	 * 承認状況
	 */
	private Integer dailyConfirmAtr;
	
	/**
	 * 年月
	 */
	private YearMonth yearMonth;
	
	/**
	 * 締めID
	 */
	private Integer closureID;
	
	/**
	 * 締め日
	 */
	private ClosureDate closureDate;
}
