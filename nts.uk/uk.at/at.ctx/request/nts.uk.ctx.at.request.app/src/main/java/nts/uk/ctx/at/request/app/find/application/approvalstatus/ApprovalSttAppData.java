package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.Value;

@Value
public class ApprovalSttAppData {
	
	private String workplaceId;
	
	private String workplaceName;
	
	private boolean isEnabled;
	
	private boolean isChecked;
	/**
	 * ・申請件数
	 */
	private Integer numOfApp;
	/**
	 * ・承認済件数
	 */
	private Integer approvedNumOfCase;
	/**
	 * ・未反映件数
	 */
	private Integer numOfUnreflected;
	/**
	 * ・未承認件数
	 */
	private Integer numOfUnapproval;
	/**
	 * ・否認件数
	 */
	private Integer numOfDenials;	
}
