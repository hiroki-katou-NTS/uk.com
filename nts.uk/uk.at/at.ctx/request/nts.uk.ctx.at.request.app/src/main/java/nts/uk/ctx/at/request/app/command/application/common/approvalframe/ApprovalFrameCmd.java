package nts.uk.ctx.at.request.app.command.application.common.approvalframe;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.approveaccepted.ApproveAcceptedCmd;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted.ApproveAccepted;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class ApprovalFrameCmd {
	public String frameID ;
	public int dispOrder;
	public List<ApproveAcceptedCmd> listApproveAccepted;
}
