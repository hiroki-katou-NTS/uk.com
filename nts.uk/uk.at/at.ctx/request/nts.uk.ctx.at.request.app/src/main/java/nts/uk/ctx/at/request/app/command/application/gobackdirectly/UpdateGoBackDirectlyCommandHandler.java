package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyUpdateService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateGoBackDirectlyCommandHandler extends CommandHandler<UpdateGoBackDirectlyCommand> {
	@Inject
	private GoBackDirectlyUpdateService goBackDirectlyUpdateService;

	@Override
	protected void handle(CommandHandlerContext<UpdateGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateGoBackDirectlyCommand command = context.getCommand();
		//approval phase
		List<AppApprovalPhase> appApprovalPhases = context.getCommand().getAppApprovalPhaseCmds()
				.stream().map(appApprovalPhaseCmd -> new AppApprovalPhase(
						companyId, 
						"", 
						"", 
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class) , 
						appApprovalPhaseCmd.dispOrder, 
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class) ,
						//Frame
						appApprovalPhaseCmd.getListFrame().stream().map(approvalFrame -> new ApprovalFrame(
								companyId, 
								"", 
								approvalFrame.dispOrder, 
								approvalFrame.listApproveAccepted.stream().map(approveAccepted -> ApproveAccepted.createFromJavaType(
										companyId, 
										"", 
										approveAccepted.approverSID,
										ApprovalAtr.UNAPPROVED.value,
										approveAccepted.confirmATR,
										null,
										approveAccepted.reason,
										approveAccepted.representerSID
										)).collect(Collectors.toList())
								)).collect(Collectors.toList())
						))
				.collect(Collectors.toList());
		// get new Application Item
		Application newApp = Application.createFromJavaType(companyId, command.appCommand.getPrePostAtr(),
				command.appCommand.getInputDate(), command.appCommand.getEnteredPersonSID(),
				command.appCommand.getReversionReason(), command.appCommand.getApplicationDate(),
				command.appCommand.getAppReasonID() + ":" + command.appCommand.getApplicationReason(),
				command.appCommand.getApplicationType(), command.appCommand.getApplicantSID(),
				command.appCommand.getReflectPlanScheReason(), command.appCommand.getReflectPlanTime(),
				command.appCommand.getReflectPerState(), command.appCommand.getReflectPlanEnforce(),
				command.appCommand.getReflectPerScheReason(), command.appCommand.getReflectPerTime(),
				command.appCommand.getReflectPerState(), command.appCommand.getReflectPlanEnforce(),
				command.appCommand.getStartDate(), command.appCommand.getEndDate(), appApprovalPhases);
		// get new Go Back Item
		GoBackDirectly newGoBack = new GoBackDirectly(companyId, command.getAppID(), command.getWorkTypeCD(),
				command.getSiftCd(), command.getWorkChangeAtr(), command.getGoWorkAtr1(), command.getBackHomeAtr1(),
				command.getWorkTimeStart1(), command.getWorkTimeEnd1(), command.workLocationCD1,
				command.getGoWorkAtr2(), command.getBackHomeAtr2(), command.getWorkTimeStart2(),
				command.getWorkTimeEnd2(), command.workLocationCD2);
		// update
		this.goBackDirectlyUpdateService.update(newGoBack, newApp);
	}
}
