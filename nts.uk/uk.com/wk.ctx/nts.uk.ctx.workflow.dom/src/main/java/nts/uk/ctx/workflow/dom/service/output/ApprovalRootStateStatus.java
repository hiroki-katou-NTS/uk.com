package nts.uk.ctx.workflow.dom.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.DailyConfirmAtr;
/**
 * 承認ルート状況
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalRootStateStatus {
	/**
	 * 年月日
	 */
	private GeneralDate date;
	/**
	 * 承認対象者
	 */
	private String employeeID;
	/**
	 * 承認状況:  0:"未承認", 1:"承認中 ", 2:"承認済"
	 */
	private DailyConfirmAtr dailyConfirmAtr;
	
}
