package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanScheReason;
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
public class UpdateGoBackDirectlyCommandHandler extends CommandHandler<UpdateApplicationGoBackDirectlyCommand> {
	@Inject
	private GoBackDirectlyUpdateService goBackDirectlyUpdateService;

	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateApplicationGoBackDirectlyCommand command = context.getCommand();
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
		Application updateApp =  new  Application(
				companyId, 
				command.goBackCommand.getAppID(),
				EnumAdaptor.valueOf(command.appCommand.getPrePostAtr(),PrePostAtr.class),
				command.appCommand.getInputDate(), 
				command.appCommand.getEnteredPersonSID(), 
				new AppReason(command.appCommand.getReversionReason()), 
				command.appCommand.getApplicationDate(), 
				new AppReason(command.appCommand.getApplicationReason()), 
				EnumAdaptor.valueOf(command.appCommand.getApplicationType(),ApplicationType.class), 
				command.appCommand.getApplicantSID(), 
				EnumAdaptor.valueOf(command.appCommand.getReflectPlanScheReason(),ReflectPlanScheReason.class), 
				command.appCommand.getReflectPlanTime(),
				EnumAdaptor.valueOf(command.appCommand.getReflectPlanState(),ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(command.appCommand.getReflectPlanEnforce(),ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(command.appCommand.getReflectPerScheReason(),ReflectPerScheReason.class), 
				command.appCommand.getReflectPerTime(),
				EnumAdaptor.valueOf(command.appCommand.getReflectPerState(),ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(command.appCommand.getReflectPerEnforce(),ReflectPlanPerEnforce.class),
				command.appCommand.getStartDate(),
				command.appCommand.getEndDate(),
				appApprovalPhases);
		
		// get new Go Back Item
		GoBackDirectly updateGoBack = new GoBackDirectly(companyId, 
				command.goBackCommand.getAppID(),
				command.goBackCommand.getWorkTypeCD(),
				command.goBackCommand.getSiftCD(), 
				command.goBackCommand.getWorkChangeAtr(), 
				command.goBackCommand.getGoWorkAtr1(), 
				command.goBackCommand.getBackHomeAtr1(),
				command.goBackCommand.getWorkTimeStart1(), 
				command.goBackCommand.getWorkTimeEnd1(), 
				command.goBackCommand.workLocationCD1,
				command.goBackCommand.getGoWorkAtr2(), 
				command.goBackCommand.getBackHomeAtr2(), 
				command.goBackCommand.getWorkTimeStart2(),
				command.goBackCommand.getWorkTimeEnd2(), 
				command.goBackCommand.workLocationCD2);
		// update
		
		this.goBackDirectlyUpdateService.update(updateGoBack, updateApp);
	}
}
