package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ChangeAbsDateCommandHandler extends CommandHandler<SaveHolidayShipmentCommand> {
	@Inject
	private SaveHolidayShipmentCommandHandler saveHanler;
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private CancelHolidayShipmentCommandHandler cancelHanler;

	String companyID, appReason, employeeID;
	ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
	RecruitmentAppCommand recCmd;
	AbsenceLeaveAppCommand absCmd;
	SaveHolidayShipmentCommand command;

	@Override
	protected void handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {
		command = context.getCommand();
		recCmd = command.getRecCmd();
		absCmd = command.getAbsCmd();
		// アルゴリズム「登録前エラーチェック（振休日変更）」を実行する
		errorCheckBeforeReg();
		// アルゴリズム「振休振出申請の取消」を実行する
		// cancelHanler.cancelAppForPaidLeave(companyID, command);
	}

	private void errorCheckBeforeReg() {
		appReason = saveHanler.preconditionCheck(command, companyID, appType);
		employeeID = AppContexts.user().employeeId();

		Optional<AbsenceLeaveApp> absAppOpt = absRepo.findByID(absCmd.getAppID());
		if (absAppOpt.isPresent()) {
			// アルゴリズム「申請日矛盾チェック」を実行する
			appDateConflictCheck(null, absCmd, absAppOpt.get());
			// アルゴリズム「同日申請存在チェック」を実行する
			sameDateCheck();
		}

	}

	private void sameDateCheck() {
		// アルゴリズム「休暇・振替系申請存在チェック」を実行する
		saveHanler.vacationTransferCheck(employeeID, absCmd.getAppDate(), command.getAppCmd().getPrePostAtr());

	}

	private void appDateConflictCheck(RecruitmentAppCommand recCmd, AbsenceLeaveAppCommand absCmd,
			AbsenceLeaveApp absDomain) {

	}

}
