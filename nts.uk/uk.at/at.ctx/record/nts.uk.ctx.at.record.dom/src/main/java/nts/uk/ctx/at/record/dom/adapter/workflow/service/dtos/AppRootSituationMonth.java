package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApproverEmployeeState;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@AllArgsConstructor
@Getter
public class AppRootSituationMonth {
	/**
	 * ルートの状況
	 */
	private ApproverEmployeeState approvalAtr;
	
	private YearMonth yearMonth;
	private Integer closureID;
	private ClosureDate closureDate;
	/**
	 * 対象者
	 */
	private String targetID;
	/**
	 * 承認状況
	 */
	private ApprovalStatus approvalStatus; 
}
