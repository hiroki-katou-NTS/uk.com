package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@AllArgsConstructor
@Getter
public class AppRootSttMonthEmpImport {
	/**
	 * 承認対象者
	 */
	private String employeeID;
	/**
	 * 承認状況
	 */
	private ApprovalStatusForEmployee approvalStatus;
	
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
