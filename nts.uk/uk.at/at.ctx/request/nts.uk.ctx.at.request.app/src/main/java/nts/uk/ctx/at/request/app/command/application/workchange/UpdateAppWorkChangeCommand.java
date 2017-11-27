package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;

@Value
public class UpdateAppWorkChangeCommand {
	
	/**
	 * 勤務変更申請
	 */
	AppWorkChangeCommand workChangeCommand;
	
	/**
	 * 申請
	 */
	UpdateApplicationCommand appCommand;
	
	/**
	 * Phase list
	 */
	List<AppApprovalPhaseCmd> appApprovalPhaseCmds;
}
