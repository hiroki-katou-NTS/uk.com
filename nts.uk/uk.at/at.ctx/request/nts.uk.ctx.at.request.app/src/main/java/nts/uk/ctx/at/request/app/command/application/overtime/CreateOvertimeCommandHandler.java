package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.service.IFactoryOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreateOvertimeCommandHandler extends CommandHandlerWithResult<CreateOvertimeCommand, ProcessResult> {

	@Inject
	private IFactoryOvertime factoryOvertime;

	@Inject
	private OvertimeService overTimeService;

	@Inject
	private NewAfterRegister_New newAfterRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService_New registerService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<CreateOvertimeCommand> context) {

		CreateOvertimeCommand command = context.getCommand();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
				command.getPrePostAtr(), command.getApplicationReason(),
				command.getApplicationReason().replaceFirst(":", System.lineSeparator()),command.getApplicantSID());

		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();
		Integer workClockFrom2 = command.getWorkClockFrom2() == null ? null : command.getWorkClockFrom2().intValue();
		Integer workClockTo2 = command.getWorkClockTo2() == null ? null : command.getWorkClockTo2().intValue();

		Optional<AppOvertimeDetail> appOvertimeDetailOtp = command.getAppOvertimeDetail() == null ? Optional.empty()
				: Optional.ofNullable(command.getAppOvertimeDetail().toDomain(companyId, appID));
		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, workClockFrom2,
				workClockTo2, command.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator()),
				command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID), 
				appOvertimeDetailOtp);

		// ドメインモデル「残業申請」の登録処理を実行する(INSERT)
		overTimeService.CreateOvertime(overTimeDomain, appRoot);

		// 2-2.新規画面登録時承認反映情報の整理
		registerService.newScreenRegisterAtApproveInfoReflect(appRoot.getEmployeeID(), appRoot);

		// 2-3.新規画面登録後の処理を実行
		return newAfterRegister.processAfterRegister(appRoot);

	}
}
