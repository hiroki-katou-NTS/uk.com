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
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class InsertGoBackDirectlyCommandHandler extends CommandHandler<InsertApplicationGoBackDirectlyCommand> {
	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;
	
	@Inject 
	private NewAfterRegister newAfterRegister;

	@Override
	protected void handle(CommandHandlerContext<InsertApplicationGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertApplicationGoBackDirectlyCommand command = context.getCommand();
		//get new Application Item
		Application newApp = Application.createFromJavaType(
				companyId, 
				command.appCommand.getPrePostAtr(),
				command.appCommand.getInputDate(), 
				command.appCommand.getEnteredPersonSID(),
				command.appCommand.getReversionReason(), 
				command.appCommand.getApplicationDate(),
				command.appCommand.getAppReasonID() + ":" + command.appCommand.getApplicationReason(),
				command.appCommand.getApplicationType(), 
				command.appCommand.getApplicantSID(),
				command.appCommand.getReflectPlanScheReason(), 
				command.appCommand.getReflectPlanTime(),
				command.appCommand.getReflectPerState(), 
				command.appCommand.getReflectPlanEnforce(),
				command.appCommand.getReflectPerScheReason(), 
				command.appCommand.getReflectPerTime(),
				command.appCommand.getReflectPerState(), 
				command.appCommand.getReflectPlanEnforce(),
				command.appCommand.getStartDate(), 
				command.appCommand.getEndDate(), 
				null);
		
		// get new GoBack Direct Item
		GoBackDirectly newGoBack = new GoBackDirectly(
				companyId, 
				newApp.getApplicationID(),
				command.goBackCommand.workTypeCD, 
				command.goBackCommand.siftCD, 
				command.goBackCommand.workChangeAtr,
				command.goBackCommand.goWorkAtr1, 
				command.goBackCommand.backHomeAtr1,
				command.goBackCommand.workTimeStart1, 
				command.goBackCommand.workTimeEnd1,
				command.goBackCommand.workLocationCD1, 
				command.goBackCommand.goWorkAtr2,
				command.goBackCommand.backHomeAtr2, 
				command.goBackCommand.workTimeStart2,
				command.goBackCommand.workTimeEnd2, 
				command.goBackCommand.workLocationCD2);
		//approval phase
		List<AppApprovalPhase> appApprovalPhases = context.getCommand().getAppApprovalPhaseCmds()
				.stream().map(appApprovalPhaseCmd -> new AppApprovalPhase(
						companyId, 
						"", 
						"", 
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class) , 
						appApprovalPhaseCmd.dispOrder, 
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class) , 
						appApprovalPhaseCmd.getApprovalFrameCmds().stream().map(approvalFrame -> new ApprovalFrame(
								companyId, 
								"", 
								approvalFrame.dispOrder, 
								approvalFrame.approveAcceptedCmds.stream().map(approveAccepted -> ApproveAccepted.createFromJavaType(
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
		
		//登録ボタンをクリックする
		goBackDirectlyRegisterService.register(newGoBack, newApp,appApprovalPhases);
		//アルゴリズム「2-3.新規画面登録後の処理」を実行する 
		newAfterRegister.processAfterRegister(companyId, newApp.getApplicationID());
	}
}
