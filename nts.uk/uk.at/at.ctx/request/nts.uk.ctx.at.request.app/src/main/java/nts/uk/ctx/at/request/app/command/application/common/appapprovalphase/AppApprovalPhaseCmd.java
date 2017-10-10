package nts.uk.ctx.at.request.app.command.application.common.appapprovalphase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import nts.uk.ctx.at.request.app.command.application.common.approvalframe.ApprovalFrameCmd;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
public class AppApprovalPhaseCmd {
	public String appID;
	public String phaseID;
	public int approvalForm;
	public int dispOrder;
	public int approvalATR;
	public List<ApprovalFrameCmd> approvalFrameCmds;
}
