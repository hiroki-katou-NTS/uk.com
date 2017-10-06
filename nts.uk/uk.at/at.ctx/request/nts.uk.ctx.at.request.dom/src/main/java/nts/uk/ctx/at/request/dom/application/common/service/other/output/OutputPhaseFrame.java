package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;

@Value
public class OutputPhaseFrame {
		//thoong tin phase
		AppApprovalPhase appApprovalPhase;
		//list frame
		List<ApprovalFrame> listApprovalFrame;
}
