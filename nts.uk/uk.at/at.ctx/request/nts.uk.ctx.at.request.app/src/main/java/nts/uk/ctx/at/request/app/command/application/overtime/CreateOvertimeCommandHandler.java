package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IFactoryOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreateOvertimeCommandHandler extends CommandHandler<CreateOvertimeCommand> {

	@Inject
	private NewBeforeRegister newBeforeRegister;
	
	@Inject
	private IFactoryOvertime factoryOvertime;
	
	@Inject
	private OvertimeService overTimeService;
	
	@Override
	protected void handle(CommandHandlerContext<CreateOvertimeCommand> context) {
		
		//
		CreateOvertimeCommand command = context.getCommand();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		//Phase list
		List<AppApprovalPhase> pharseList = getAppApprovalPhaseList(command, companyId, appID);		
		// Create Application
		Application appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(), command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(), pharseList);
		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime();
		// 2-1.新規画面登録前の処理を実行する
		newBeforeRegister.processBeforeRegister(appRoot);
		
		//ドメインモデル「残業申請」の登録処理を実行する(INSERT)
		overTimeService.CreateOvertime(overTimeDomain);
		
	}
	/**
	 * Convert Phase command list to Approve Phase list
	 * @param command : create command
	 * @param companyId: 会社ID
	 * @param appID: 申請ID
	 * @return
	 */
	private List<AppApprovalPhase> getAppApprovalPhaseList(CreateOvertimeCommand command, String companyId, String appID)
	{
		return command.getAppApprovalPhaseCmds().stream()
				.map(appApprovalPhaseCmd -> new AppApprovalPhase(companyId, appID, IdentifierUtil.randomUniqueId(),
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class),
						appApprovalPhaseCmd.dispOrder,
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class),
						appApprovalPhaseCmd.getListFrame().stream()
								.map(approvalFrame -> new ApprovalFrame(companyId, IdentifierUtil.randomUniqueId(), approvalFrame.dispOrder,
										approvalFrame.listApproveAccepted.stream()
												.map(approveAccepted -> ApproveAccepted.createFromJavaType(companyId,
														IdentifierUtil.randomUniqueId(), approveAccepted.approverSID, ApprovalAtr.UNAPPROVED.value,
														approveAccepted.confirmATR, null, approveAccepted.reason,
														approveAccepted.representerSID))
												.collect(Collectors.toList())))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
	}
}
