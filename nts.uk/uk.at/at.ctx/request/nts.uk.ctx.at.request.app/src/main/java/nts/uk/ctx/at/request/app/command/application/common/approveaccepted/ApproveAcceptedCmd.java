package nts.uk.ctx.at.request.app.command.application.common.approveaccepted;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class ApproveAcceptedCmd {
	public String appAcceptedID ;
	public String approverSID ;
	public int approvalATR;
	public int confirmATR;
	public String approvalDate;
	public String reason;
	public String representerSID;
}
