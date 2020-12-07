package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttEmpDateParam {
	private String empID;
	
	private String startDate;
	
	private String endDate;
}
