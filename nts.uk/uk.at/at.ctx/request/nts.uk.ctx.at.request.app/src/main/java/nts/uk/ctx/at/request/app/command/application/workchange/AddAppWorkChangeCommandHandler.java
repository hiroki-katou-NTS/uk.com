package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;

@Stateless
@Transactional
public class AddAppWorkChangeCommandHandler extends CommandHandler<AddAppWorkChangeCommand> {

	@Inject
	private IWorkChangeRegisterService workChangeRegisterService;

	@Override
	protected void handle(CommandHandlerContext<AddAppWorkChangeCommand> context) {
		AddAppWorkChangeCommand addCommand = context.getCommand();

		// Application command
		CreateApplicationCommand appCommand = addCommand.getAppCommand();
		// Work change command
		AppWorkChangeCommand workChangeCommand = addCommand.getWorkChangeCommand();
		// Phase command
		List<AppApprovalPhaseCmd> approvalPhaseCommand = addCommand.getAppApprovalPhaseCmds();

		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		// 入力者
		String persionId = AppContexts.user().personId();
		// 申請者
		String applicantSID = AppContexts.user().employeeId();

		// Phase list
		List<AppApprovalPhase> pharseList = getAppApprovalPhaseList(approvalPhaseCommand, companyId, appID);

		// Create Application domain
		Application app = new Application(companyId, appID,
				EnumAdaptor.valueOf(appCommand.getPrePostAtr(), PrePostAtr.class), GeneralDateTime.now(), persionId,
				new AppReason(appCommand.getReversionReason()), appCommand.getApplicationDate(),
				new AppReason(appCommand.getApplicationReason()), ApplicationType.WORK_CHANGE_APPLICATION, applicantSID,
				EnumAdaptor.valueOf(0, ReflectPlanScheReason.class), null,
				EnumAdaptor.valueOf(0, ReflectPlanPerState.class), EnumAdaptor.valueOf(0, ReflectPlanPerEnforce.class),
				EnumAdaptor.valueOf(0, ReflectPerScheReason.class), null,
				EnumAdaptor.valueOf(0, ReflectPlanPerState.class), EnumAdaptor.valueOf(0, ReflectPlanPerEnforce.class),
				appCommand.getApplicationDate(), appCommand.getApplicationDate(), pharseList);

		// Work Change Domain
		AppWorkChange workChangeDomain = AppWorkChange.createFromJavaType(workChangeCommand.getCid(),
				workChangeCommand.getAppId(), workChangeCommand.getWorkTypeCd(), workChangeCommand.getWorkTimeCd(),
				workChangeCommand.getExcludeHolidayAtr(), workChangeCommand.getWorkChangeAtr(),
				workChangeCommand.getGoWorkAtr1(), workChangeCommand.getBackHomeAtr1(),
				workChangeCommand.getBreakTimeStart1(), workChangeCommand.getBreakTimeEnd1(),
				workChangeCommand.getWorkTimeStart1(), workChangeCommand.getWorkTimeEnd1(),
				workChangeCommand.getWorkTimeStart2(), workChangeCommand.getWorkTimeEnd2(),
				workChangeCommand.getGoWorkAtr2(), workChangeCommand.getBackHomeAtr2());

		workChangeRegisterService.registerData(workChangeDomain, app);
	}

	/**
	 * Convert Phase command list to Approve Phase list
	 * 
	 * @param List<AppApprovalPhaseCmd> : Phase list
	 * @param companyId: 会社ID
	 * @param appID: 申請ID
	 * @return
	 */
	private List<AppApprovalPhase> getAppApprovalPhaseList(List<AppApprovalPhaseCmd> approvalPhaseCommand,
			String companyId, String appID) {
		List<AppApprovalPhase> listAppApprovalPhase = approvalPhaseCommand.stream()
				.map(appApprovalPhaseCmd -> new AppApprovalPhase(companyId, appID, IdentifierUtil.randomUniqueId(),
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class),
						appApprovalPhaseCmd.dispOrder,
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class),
						appApprovalPhaseCmd.getListFrame().stream()
								.map(approvalFrame -> new ApprovalFrame(companyId, IdentifierUtil.randomUniqueId(),
										approvalFrame.dispOrder, approvalFrame.listApproveAccepted.stream()
												.map(approveAccepted -> ApproveAccepted.createFromJavaType(companyId,
														IdentifierUtil.randomUniqueId(), approveAccepted.approverSID,
														ApprovalAtr.UNAPPROVED.value, approveAccepted.confirmATR, null,
														approveAccepted.reason, approveAccepted.representerSID))
												.collect(Collectors.toList())))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
		//
		listAppApprovalPhase.forEach(appApprovalPhase -> {
			appApprovalPhase.setAppID(appID);
			String phaseID = appApprovalPhase.getPhaseID();
			appApprovalPhase.setPhaseID(phaseID);
			appApprovalPhase.getListFrame().forEach(approvalFrame -> {
				String frameID = approvalFrame.getFrameID();
				approvalFrame.setFrameID(frameID);
				approvalFrame.getListApproveAccepted().forEach(appAccepted -> {
					String appAcceptedID = appAccepted.getAppAcceptedID();
					appAccepted.setAppAcceptedID(appAcceptedID);
				});
			});
		});

		return listAppApprovalPhase;
	}
}
