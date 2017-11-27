package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;

@Value
public class AddAppWorkChangeCommand {
	/**
	 * 勤務変更申請
	 */
	AppWorkChangeCommand workChangeCommand;
		
	/**
	 * 申請
	 */
	CreateApplicationCommand appCommand;

	/**
	 * Approval Phase
	 */
	List<AppApprovalPhaseCmd> appApprovalPhaseCmds;
}
