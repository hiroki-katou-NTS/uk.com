package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttEmpDate {
	/**
	 * 日付
	 */
	private GeneralDate date;
	
	/**
	 * 状態記号
	 */
	private String status;
}
