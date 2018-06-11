package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

@Value
@AllArgsConstructor
public class ApprovalStatusPeriorDto {
	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	/**
	 * 終了日
	 */
	private GeneralDate endDate;
	/**
	 * list employee code
	 */
	private List<String> employeesCode;
	
	private int yearMonth;
	
	private String closureName;
}
