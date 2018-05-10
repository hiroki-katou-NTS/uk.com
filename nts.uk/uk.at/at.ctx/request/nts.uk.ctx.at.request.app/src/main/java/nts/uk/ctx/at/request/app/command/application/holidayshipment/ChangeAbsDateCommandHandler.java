package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
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
	@Inject
	private ApplicationRepository_New appRepo;

	String companyID, appReason, employeeID, reason;
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
		HolidayShipmentCommand shipmentCmd = new HolidayShipmentCommand(absCmd.getAppID(), null,
				command.getAppCmd().getAppVersion(), "");
		// アルゴリズム「振休振出申請の取消」を実行する
		cancelHanler.cancelAppForPaidLeave(companyID, shipmentCmd);
		Optional<Application_New> appOutputOpt = appRepo.findByID(companyID, absCmd.getAppID());
		if (appOutputOpt.isPresent()) {
			Application_New absApp = appOutputOpt.get();
			updateAplication(absApp);
			// アルゴリズム「登録前共通処理（新規）」を実行する
			saveHanler.CmProcessBeforeReg(command, absApp);
			// ドメイン「振休申請」を1件登録する

		}

	}

	private void updateAplication(Application_New app) {
		app.setAppReason(new AppReason(reason));

	}

	private void errorCheckBeforeReg() {
		//appReason = saveHanler.preconditionCheck(command, companyID, appType);
		employeeID = AppContexts.user().employeeId();
		// アルゴリズム「事前条件チェック」を実行する
		//reason = saveHanler.preconditionCheck(command, companyID, appType);
		// アルゴリズム「同日申請存在チェック」を実行する
		saveHanler.dateCheck(command);

	}

}
