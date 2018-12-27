package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
@AllArgsConstructor
@Getter
public class EmpPerformMonthParamImport {
	
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
