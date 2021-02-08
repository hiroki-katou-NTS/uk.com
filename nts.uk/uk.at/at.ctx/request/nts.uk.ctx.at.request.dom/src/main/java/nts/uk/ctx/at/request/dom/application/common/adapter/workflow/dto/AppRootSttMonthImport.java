package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootSttMonthImport {
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
