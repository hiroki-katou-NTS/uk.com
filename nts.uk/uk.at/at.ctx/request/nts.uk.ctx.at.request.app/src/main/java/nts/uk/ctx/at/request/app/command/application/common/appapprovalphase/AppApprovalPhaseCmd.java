package nts.uk.ctx.at.request.app.command.application.common.appapprovalphase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.approvalframe.ApprovalFrameCmd;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
public class AppApprovalPhaseCmd {
	public String phaseID;
	public int approvalForm;
	public int dispOrder;
	public int approvalATR;
	public List<ApprovalFrameCmd> approvalFrameCmds;
}
