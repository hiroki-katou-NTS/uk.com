package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmpPerformMonthParamAt {
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
	
	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
	
	/**
	 * 社員ID
	 */
	private String employeeID;
}
