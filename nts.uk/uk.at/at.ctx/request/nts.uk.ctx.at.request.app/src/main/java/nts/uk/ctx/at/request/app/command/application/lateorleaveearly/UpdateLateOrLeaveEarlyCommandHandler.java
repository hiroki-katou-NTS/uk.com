package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
@Transactional
public class UpdateLateOrLeaveEarlyCommandHandler extends CommandHandler<UpdateLateOrLeaveEarlyCommand> {
	
	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;

	@Inject
	private FactoryLateOrLeaveEarly factoryLateOrLeaveEarly;
	
	@Inject
	private DetailBeforeUpdate detailBeforeProcessRegisterService;
	
	@Inject DetailAfterUpdate afterProcessDetailSerivce;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateLateOrLeaveEarlyCommand> context) {
		UpdateLateOrLeaveEarlyCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String appReason = "";
		if(!command.getReasonTemp().isEmpty() || !command.getAppReason().isEmpty()) {
			appReason = !command.getReasonTemp().isEmpty() ? command.getReasonTemp() +  System.lineSeparator() + command.getAppReason() : command.getAppReason();
		}
		List<AppApprovalPhase> appApprovalPhases = context.getCommand().getAppApprovalPhaseCmds().stream()
				.map(appApprovalPhaseCmd -> new AppApprovalPhase(companyId, command.getAppID(), appApprovalPhaseCmd.getPhaseID(),
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class),
						appApprovalPhaseCmd.dispOrder,
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class),
						appApprovalPhaseCmd.getListFrame().stream()
								.map(approvalFrame -> new ApprovalFrame(companyId, approvalFrame.frameID, approvalFrame.dispOrder,
										approvalFrame.listApproveAccepted.stream()
												.map(approveAccepted -> ApproveAccepted.createFromJavaType(companyId,
														approveAccepted.appAcceptedID, approveAccepted.approverSID, ApprovalAtr.UNAPPROVED.value,
														approveAccepted.confirmATR, null, approveAccepted.reason,
														approveAccepted.representerSID))
												.collect(Collectors.toList())))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
		LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(
				command.getAppID(),
        		command.getApplicationDate(),
        		command.getPrePostAtr(),
        		appReason,
        		null,
        		command.getEarly1(),
        		command.getEarlyTime1(),
        		command.getLate1(),
        		command.getLateTime1(),
        		command.getEarly2(),
        		command.getEarlyTime2(),
        		command.getLate2(),
        		command.getLateTime2());
		domainLateOrLeaveEarly.setVersion(command.getVersion());
		domainLateOrLeaveEarly.setListPhase(appApprovalPhases);
		
		
		//「4-1.詳細画面登録前の処理」を実行する 
		detailBeforeProcessRegisterService.processBeforeDetailScreenRegistration(
				domainLateOrLeaveEarly.getCompanyID(),
				//ApplicantSID = EmployeeID
				domainLateOrLeaveEarly.getApplicantSID(),
				domainLateOrLeaveEarly.getApplicationDate(), 9,
				domainLateOrLeaveEarly.getApplicationID(),
				
				domainLateOrLeaveEarly.getPrePostAtr());
		
		//ドメインモデル「遅刻早退取消申請」の更新する
		//Update the domain model 'Cancellation for late arrival cancellation'
		lateOrLeaveEarlyService.updateLateOrLeaveEarly(domainLateOrLeaveEarly);
		
		//「4-2.詳細画面登録後の処理」を実行する
		afterProcessDetailSerivce.processAfterDetailScreenRegistration(domainLateOrLeaveEarly);
		
	}
	

}
