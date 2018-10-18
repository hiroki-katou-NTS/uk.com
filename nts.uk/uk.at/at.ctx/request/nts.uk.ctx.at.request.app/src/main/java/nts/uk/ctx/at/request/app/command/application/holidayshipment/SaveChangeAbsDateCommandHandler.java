package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayshipment.ApplicationCombination;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveChangeAbsDateCommandHandler
		extends CommandHandlerWithResult<SaveHolidayShipmentCommand, ProcessResult> {
	@Inject
	private SaveHolidayShipmentCommandHandler saveHanler;
	@Inject
	private CancelHolidayShipmentCommandHandler cancelHanler;
	@Inject
	private ApplicationApprovalService_New appImp;
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private ApplicationRepository_New appRepo;
	@Inject
	private NewAfterRegister_New newAfterReg;

	@Override
	protected ProcessResult handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {
		SaveHolidayShipmentCommand command = context.getCommand();
		AbsenceLeaveAppCommand absCmd = command.getAbsCmd();

		// アルゴリズム「登録前エラーチェック（振休日変更）」を実行する
		String appReason = errorCheckBeforeReg(command, absCmd);

		// アルゴリズム「詳細画面申請データを取得する」を実行する
		getDetailApp(absCmd.getAppID());

		// アルゴリズム「振休振出申請の取消」を実行する
		cancelOldAbsApp(command, absCmd);
		// アルゴリズム「登録前共通処理（新規）」を実行する
		Application_New commonApp = createNewCommonApp(command, absCmd, appReason);
		command.getAbsCmd().setAppID(commonApp.getAppID());
		saveHanler.CmProcessBeforeReg(command, commonApp);
		// ドメイン「振休申請」を1件登録する
		createNewAbsApp(commonApp, command);
		// アルゴリズム「新規画面登録後の処理」を実行する
		return newAfterReg.processAfterRegister(commonApp);

	}

	private void getDetailApp(String appID) {
		String companyID = AppContexts.user().companyId();
		Optional<Application_New> app = appRepo.findByID(companyID, appID);
		if (!app.isPresent()) {
			throw new BusinessException("Msg_198");
		}
	}

	private Application_New createNewCommonApp(SaveHolidayShipmentCommand command, AbsenceLeaveAppCommand absCmd,
			String appReason) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
		Application_New commonApp = Application_New.firstCreate(companyID,
				EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(), PrePostAtr.class), absCmd.getAppDate(),
				appType, employeeID, new AppReason(appReason));
		appImp.insert(commonApp);
		return commonApp;
	}

	private void createNewAbsApp(Application_New commonApp, SaveHolidayShipmentCommand command) {

		AbsenceLeaveApp absApp = saveHanler.createNewAbsDomainFromCmd(command.getAbsCmd());

		absRepo.insert(absApp);
	}

	private void cancelOldAbsApp(SaveHolidayShipmentCommand command, AbsenceLeaveAppCommand absCmd) {
		String companyID = AppContexts.user().companyId();
		HolidayShipmentCommand shipmentCmd = new HolidayShipmentCommand(absCmd.getAppID(), null,
				command.getAppCmd().getAppVersion(), "");
		cancelHanler.cancelAppForPaidLeave(companyID, shipmentCmd);

	}

	private String errorCheckBeforeReg(SaveHolidayShipmentCommand command, AbsenceLeaveAppCommand absCmd) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
		// アルゴリズム「事前条件チェック」を実行する
		String appReason = saveHanler.preconditionCheck(command, companyID, appType, ApplicationCombination.Abs.value);
		// アルゴリズム「同日申請存在チェック」を実行する
		saveHanler.dateCheck(employeeID, null, absCmd.getAppDate(), command, command.getComType());
		return appReason;
	}

}
