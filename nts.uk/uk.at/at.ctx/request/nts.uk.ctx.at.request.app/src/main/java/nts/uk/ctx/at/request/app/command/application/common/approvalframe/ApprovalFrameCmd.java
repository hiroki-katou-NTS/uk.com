package nts.uk.ctx.at.request.app.command.application.common.approvalframe;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.approveaccepted.ApproveAcceptedCmd;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class ApprovalFrameCmd {
	public String phaseID ;
	public int dispOrder;
	public String approverSID;
	public int approvalATR;
	public int confirmATR;
	public String approvalDate;
	public String reason;
	public String representerSID;
	public List<ApproveAcceptedCmd> approveAcceptedCmds;
}
