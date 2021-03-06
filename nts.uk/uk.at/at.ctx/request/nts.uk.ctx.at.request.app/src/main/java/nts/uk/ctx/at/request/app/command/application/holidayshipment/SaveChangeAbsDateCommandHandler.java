package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.workrecord.remainmanagement.InterimRemainDataMngCheckRegisterRequest;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveChangeAbsDateCommandHandler
		extends CommandHandlerWithResult<SaveHolidayShipmentCommand, ProcessResult> {
	@Inject
	private SaveHolidayShipmentCommandHandler saveHanler;
	@Inject
	private CancelHolidayShipmentCommandHandler cancelHanler;
	@Inject
	private ApplicationApprovalService appImp;
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private ApplicationRepository appRepo;
	@Inject
	private NewAfterRegister newAfterReg;
	@Inject
	private OtherCommonAlgorithm ortherAl;
	@Inject
	private HolidayApplicationSettingRepository repoHdAppSet;
	@Inject
	private InterimRemainDataMngCheckRegisterRequest checkRegister;
	@Inject
	private HolidayShipmentScreenAFinder afinder;
	@Inject
	private InterimRemainDataMngRegisterDateChange registerDateChange;

	@Override
	protected ProcessResult handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {
		SaveHolidayShipmentCommand command = context.getCommand();
//		AbsenceLeaveAppCmd absCmd = command.getAbsCmd();
		String companyID = AppContexts.user().companyId();
		String sID =   command.getAppCmd().getEmployeeID();
//		String oldAppID = absCmd.getAppID();
//		// ??????????????????????????????????????????????????????????????????????????????????????????
//		String appReason = errorCheckBeforeReg(command, absCmd);
//		
//		//4.???????????????????????????????????????
//		PeriodCurrentMonth cls =  this.ortherAl.employeePeriodCurrentMonthCalculate(companyID, sID, GeneralDate.today());
//		
//		// ?????????????????????????????????????????????????????????????????????????????????
//		Application oldApp =  getDetailApp(absCmd.getAppID());
//		//???????????????
//		// AchievementOutput achievement = afinder.getAchievement(companyID, sID, oldApp.getAppDate());
//		
//		//????????????????????????????????????????????????????????????
//		Optional<HdAppSet> hdAppSetOpt =  repoHdAppSet.getAll();
//		
//		boolean chkSubHoliday = false;
//		boolean chkPause = false;
//		boolean chkAnnual = false;
//		boolean chkFundingAnnual = false;
//		boolean chkSpecial = true;
//		boolean chkPublicHoliday = false;
//		boolean chkSuperBreak = true;
//		String appName = "";
//		if (hdAppSetOpt.isPresent()) {
//			HdAppSet hdSet = hdAppSetOpt.get();
//			chkPause = hdSet.getRegisInsuff().value == 1 ? true : false;// ??????????????????????????????????????????????????????
//			if (hdSet.getFurikyuName() != null) {
//				appName = hdSet.getFurikyuName().v();
//			}
//		String oldAppID = absCmd.getAppID();
//		// ??????????????????????????????????????????????????????????????????????????????????????????
//		String appReason = errorCheckBeforeReg(command, absCmd);
//		
//		//4.???????????????????????????????????????
//		PeriodCurrentMonth cls =  this.ortherAl.employeePeriodCurrentMonthCalculate(companyID, sID, GeneralDate.today());
//		
//		// ?????????????????????????????????????????????????????????????????????????????????
//		Application oldApp =  getDetailApp(absCmd.getAppID());
//		//???????????????
//		// AchievementOutput achievement = afinder.getAchievement(companyID, sID, oldApp.getAppDate());
//		
//		//????????????????????????????????????????????????????????????
//		Optional<HolidayApplicationSetting> hdAppSetOpt =  repoHdAppSet.findSettingByCompanyId(companyID);
//		
//		boolean chkSubHoliday = false;
//		boolean chkPause = false;
//		boolean chkAnnual = false;
//		boolean chkFundingAnnual = false;
//		boolean chkSpecial = true;
//		boolean chkPublicHoliday = false;
//		boolean chkSuperBreak = true;
//		String appName = "";
//		if (hdAppSetOpt.isPresent()) {
//			HolidayApplicationSetting hdSet = hdAppSetOpt.get();
//			chkPause = hdSet.getRegisInsuff().value == 1 ? true : false;// ??????????????????????????????????????????????????????
//			appName = hdSet.getHolidayApplicationTypeDisplayName()
//					.stream()
//					.filter(i -> i.getHolidayApplicationType() == HolidayAppType.REST_TIME)
//					.findFirst()
//					.map(i -> i.getDisplayName().v())
//					.orElse("");
//		}
		
//		InterimRemainCheckInputParam inputParam = new InterimRemainCheckInputParam(companyID, sID,
//				new DatePeriod(cls.getStartDate(), cls.getStartDate().addYears(1).addDays(-1)), false,
//				command.getAbsCmd().getAppDate(),
//				new DatePeriod(command.getAbsCmd().getAppDate(), command.getAbsCmd().getAppDate()), true,
//				Collections.emptyList(), Collections.emptyList(),getAppData(command,sID,achievement,oldApp) , chkSubHoliday, chkPause, chkAnnual, chkFundingAnnual, chkSpecial,
//				chkPublicHoliday, chkSuperBreak);
		
		//??????????????????????????????
//		EarchInterimRemainCheck check =  checkRegister.checkRegister(inputParam);
		
//		if(check.isChkSubHoliday() ==true || check.isChkPause()==true || check.isChkAnnual() ==true || check.isChkFundingAnnual() ==true || check.isChkSpecial()==true){
//			throw new BusinessException("Msg_1409", appName);
//		}
//		
////		InterimRemainCheckInputParam inputParam = new InterimRemainCheckInputParam(companyID, sID,
////				new DatePeriod(cls.getStartDate(), cls.getStartDate().addYears(1).addDays(-1)), false,
////				command.getAbsCmd().getAppDate(),
////				new DatePeriod(command.getAbsCmd().getAppDate(), command.getAbsCmd().getAppDate()), true,
////				Collections.emptyList(), Collections.emptyList(),getAppData(command,sID,achievement,oldApp) , chkSubHoliday, chkPause, chkAnnual, chkFundingAnnual, chkSpecial,
////				chkPublicHoliday, chkSuperBreak);
//		
//		//??????????????????????????????
////		EarchInterimRemainCheck check =  checkRegister.checkRegister(inputParam);
//		
////		if(check.isChkSubHoliday() ==true || check.isChkPause()==true || check.isChkAnnual() ==true || check.isChkFundingAnnual() ==true || check.isChkSpecial()==true){
////			throw new BusinessException("Msg_1409", appName);
////		}
//		
//		
//
//		// ??????????????????????????????????????????????????????????????????
//		cancelOldAbsApp(command, absCmd, oldAppID);
//		// ????????????????????????????????????????????????????????????????????????
//		Application commonApp = createNewCommonApp(command, absCmd, appReason);
//		command.getAbsCmd().setAppID(commonApp.getAppID());
//		// saveHanler.CmProcessBeforeReg(command, commonApp);
//		// ?????????????????????????????????1???????????????
//		createNewAbsApp(commonApp, command);
		//????????????????????????
//		this.registerDateChange.registerDateChange(companyID, sID,
//				Arrays.asList(oldApp.getAppDate(), command.getAbsCmd().getAppDate()));
		// ?????????????????????????????????????????????????????????????????????
		/*return newAfterReg.processAfterRegister(commonApp);*/
		return null;
	}
	
//	private List<AppRemainCreateInfor> getAppData(SaveHolidayShipmentCommand command, String sID,
//			AchievementOutput achievement, Application oldApp) {
//		List<AppRemainCreateInfor> apps = new ArrayList<AppRemainCreateInfor>();
//		// add oldapp
////		apps.add(new AppRemainCreateInfor(sID, oldApp.getAppID(), GeneralDateTime.now(), oldApp.getAppDate(),
////				EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(),
////						nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.PrePostAtr.class),
////				nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType.COMPLEMENT_LEAVE_APPLICATION,
////				Optional.ofNullable(achievement.getWorkType().getWorkTypeCode()),
////				Optional.ofNullable(command.getAbsCmd().getWkTimeCD()), Optional.empty(), Optional.empty(),
////				Optional.empty(), Optional.ofNullable(oldApp.getAppDate()), Optional.ofNullable(oldApp.getAppDate()),
////				Collections.emptyList()));
//
//		AbsenceLeaveAppCommand absCmd = command.getAbsCmd();
//		String newAppID = IdentifierUtil.randomUniqueId();
//
//		absCmd.setAppID(newAppID);
//		// add newApp
//		apps.add(new AppRemainCreateInfor(sID, newAppID, GeneralDateTime.now(), absCmd.getAppDate(),
//				EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(),
//						nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.PrePostAtr.class),
//				nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType.COMPLEMENT_LEAVE_APPLICATION,
//				Optional.ofNullable(absCmd.getWkTypeCD()), Optional.ofNullable(absCmd.getWkTimeCD()), Optional.empty(),
//				Optional.empty(), Optional.empty(), Optional.ofNullable(absCmd.getAppDate()),
//				Optional.ofNullable(absCmd.getAppDate()), Collections.emptyList()));
//
//		return apps;
//	}
//
//	private Application getDetailApp(String appID) {
////		String companyID = AppContexts.user().companyId();
////		Optional<Application_New> app = appRepo.findByID(companyID, appID);
////		if (!app.isPresent()) {
////			throw new BusinessException("Msg_198");
////		}
////		return app.get();
//		return null;
//	}
//
//	private Application createNewCommonApp(SaveHolidayShipmentCommand command, AbsenceLeaveAppCommand absCmd,
//			String appReason) {
////		String companyID = AppContexts.user().companyId();
////		String employeeID = command.getAppCmd().getEmployeeID();
////		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
////		Application_New commonApp = Application_New.firstCreate(companyID,
////				EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(), PrePostAtr.class), absCmd.getAppDate(),
////				appType, employeeID, new AppReason(appReason));
////		if (!AppContexts.user().employeeId().equals(employeeID)) {
////			commonApp.setEnteredPersonID(AppContexts.user().employeeId());
////		}
////		// error EA refactor 4
////		/*appImp.insert(commonApp);*/
////		return commonApp;
//		return null;
//	}
//
//	private void createNewAbsApp(Application commonApp, SaveHolidayShipmentCommand command) {
//
//		AbsenceLeaveApp absApp = saveHanler.createNewAbsDomainFromCmd(command.getAbsCmd());
//
//		absRepo.insert(absApp);
//	}
//
//	private void cancelOldAbsApp(SaveHolidayShipmentCommand command, AbsenceLeaveAppCommand absCmd, String oldAppID) {
//		String companyID = AppContexts.user().companyId();
//		HolidayShipmentCommand shipmentCmd = new HolidayShipmentCommand(oldAppID, null,
//				command.getAppCmd().getAppVersion(), "", "", "",0,0);
//		cancelHanler.cancelAppForPaidLeave(companyID, shipmentCmd);
//
//	}
//
//	private String errorCheckBeforeReg(SaveHolidayShipmentCommand command, AbsenceLeaveAppCommand absCmd) {
//		String companyID = AppContexts.user().companyId();
//		String employeeID = AppContexts.user().employeeId();
//		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
//		// ???????????????????????????????????????????????????????????????
//		String appReason = saveHanler.preconditionCheck(command, companyID, appType, ApplicationCombination.Abs.value);
//		// ?????????????????????????????????????????????????????????????????????
//		saveHanler.dateCheck(employeeID, null, absCmd.getAppDate(), command, command.getComType());
//		return appReason;
//	}

}
