package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.Getter;
import lombok.Setter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class ApprSttExecutionOutput {
	
	private String wkpID;
	
	private String wkpCD;
	
	private String wkpName;
	
	private Integer countEmp;
	
	private Integer countUnApprApp;
	
	public ApprSttExecutionOutput(String wkpID, String wkpCD) {
		this.wkpID = wkpID;
		this.wkpCD = wkpCD;
		this.wkpName = "";
		this.countEmp = 0;
		this.countUnApprApp = 0;
	}
	
}
