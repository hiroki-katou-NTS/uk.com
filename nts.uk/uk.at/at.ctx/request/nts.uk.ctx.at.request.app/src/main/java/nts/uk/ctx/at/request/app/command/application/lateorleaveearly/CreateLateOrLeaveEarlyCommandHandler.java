package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.find.application.common.ApprovalRootOfSubjectRequestDto;
import nts.uk.ctx.at.request.app.find.application.common.GetDataApprovalRootOfSubjectRequest;
import nts.uk.ctx.at.request.app.find.application.common.ObjApprovalRootInput;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegisterImpl;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional

public class CreateLateOrLeaveEarlyCommandHandler extends CommandHandler<CreateLateOrLeaveEarlyCommand> {

	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;

	@Inject
	private FactoryLateOrLeaveEarly factoryLateOrLeaveEarly;

	@Inject
	private NewAfterRegister newAfterRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService registerService;

	@Inject
	private NewBeforeRegister newBeforeRegister;
	@Override
	protected void handle(CommandHandlerContext<CreateLateOrLeaveEarlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		String appID = IdentifierUtil.randomUniqueId();
		String appReason = "";
		List<AppApprovalPhase> appApprovalPhases = context.getCommand().getAppApprovalPhaseCmds().stream()
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

		CreateLateOrLeaveEarlyCommand command = context.getCommand();
		if(!command.getReasonTemp().isEmpty() || !command.getAppReason().isEmpty()) {
			appReason = !command.getReasonTemp().isEmpty() ? command.getReasonTemp() +  System.lineSeparator() + command.getAppReason() : command.getAppReason();
		}
		LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(appID,
				command.getApplicationDate(),
				command.getPrePostAtr(), 
				appReason, 
				appApprovalPhases,
				command.getEarly1(), 
				command.getEarlyTime1(),
				command.getLate1(),
				command.getLateTime1(),
				command.getEarly2(), 
				command.getEarlyTime2(),
				command.getLate2(), 
				command.getLateTime2());
		domainLateOrLeaveEarly.setListPhase(appApprovalPhases);
		domainLateOrLeaveEarly.setStartDate(domainLateOrLeaveEarly.getApplicationDate());
		domainLateOrLeaveEarly.setEndDate(domainLateOrLeaveEarly.getApplicationDate());
		// 共通アルゴリズム「2-1.新規画面登録前の処理」を実行する (Thực thi 共通アルゴリズム「2-1.新規画面登録前の処理」)
		newBeforeRegister.processBeforeRegister(domainLateOrLeaveEarly);
		// 2-2.新規画面登録時承認反映情報の整理
		registerService.newScreenRegisterAtApproveInfoReflect(domainLateOrLeaveEarly.getApplicantSID(),
				domainLateOrLeaveEarly);
		//事前制約をチェックする (Kiểm tra 事前制約)
		//ドメインモデル「遅刻早退取消申請」の新規登録する
		lateOrLeaveEarlyService.createLateOrLeaveEarly(domainLateOrLeaveEarly);
		//共通アルゴリズム「2-3.新規画面登録後の処理」を実行する (Thực thi 共通アルゴリズム「2-3.新規画面登録後の処理」)
		newAfterRegister.processAfterRegister(domainLateOrLeaveEarly);

	}
}
