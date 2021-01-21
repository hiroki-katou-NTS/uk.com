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
	
	/**
	 * 職場名
	 */
	private String wkpName;
	
	/**
	 * 対象人数
	 */
	private Integer countEmp;
	
	/**
	 * 申請未承認
	 */
	private Integer countUnApprApp;
	
	public ApprSttExecutionOutput(String wkpID, String wkpCD) {
		this.wkpID = wkpID;
		this.wkpCD = wkpCD;
		this.wkpName = "";
		this.countEmp = 0;
		this.countUnApprApp = 0;
	}
	
}
