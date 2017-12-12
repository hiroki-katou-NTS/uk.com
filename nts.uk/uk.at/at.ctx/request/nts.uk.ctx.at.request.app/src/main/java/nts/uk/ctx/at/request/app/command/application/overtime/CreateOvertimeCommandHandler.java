package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IFactoryOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreateOvertimeCommandHandler extends CommandHandlerWithResult<CreateOvertimeCommand, String> {

	@Inject
	private IFactoryOvertime factoryOvertime;

	@Inject
	private OvertimeService overTimeService;

	@Inject
	private NewAfterRegister newAfterRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService registerService;

	@Override
	protected String handle(CommandHandlerContext<CreateOvertimeCommand> context) {

		//
		CreateOvertimeCommand command = context.getCommand();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Phase list
		List<AppApprovalPhase> pharseList = CheckBeforeRegisterOvertime.getAppApprovalPhaseList(command, companyId, appID);
		// Create Application
		Application appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
				command.getPrePostAtr(), command.getApplicationReason(),
				command.getApplicationReason().replaceFirst(":", System.lineSeparator()), pharseList);

		
		int workClockFrom1 = command.getWorkClockFrom1() == null ? -1 : command.getWorkClockFrom1().intValue();
		int workClockTo1 = command.getWorkClockTo1() == null ? -1 : command.getWorkClockTo1().intValue();
		int workClockFrom2 = command.getWorkClockFrom2() == null ? -1 : command.getWorkClockFrom2().intValue();
		int workClockTo2 = command.getWorkClockTo2() == null ? -1 : command.getWorkClockTo2().intValue();
		
		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1,
				workClockTo1, workClockFrom2, workClockTo2,
				command.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator()),
				command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID));

		// 2-2.新規画面登録時承認反映情報の整理
		registerService.newScreenRegisterAtApproveInfoReflect(appRoot.getApplicantSID(), appRoot);
		
		// ドメインモデル「残業申請」の登録処理を実行する(INSERT)
		overTimeService.CreateOvertime(overTimeDomain, appRoot);

		// 2-3.新規画面登録後の処理を実行
		return newAfterRegister.processAfterRegister(appRoot);

	}
}
