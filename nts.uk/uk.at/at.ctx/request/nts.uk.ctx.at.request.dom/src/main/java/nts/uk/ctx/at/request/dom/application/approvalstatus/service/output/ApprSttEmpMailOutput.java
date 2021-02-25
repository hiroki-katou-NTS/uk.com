package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttEmpMailOutput {
	private String empID;
	
	@Setter
	private String empName;
	
	@Setter
	private String empMail;
}
