package nts.uk.ctx.at.request.app.command.application.holidayshipment;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.workrecord.remainmanagement.InterimRemainDataMngCheckRegisterRequest;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.holidayset.HolidaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveHolidayShipmentCommandHandler
		extends CommandHandlerWithResult<SaveHolidayShipmentCommand, ProcessResult> {
	
//	@Inject
//	private WithDrawalReqSetRepository withDrawRepo;
//	@Inject
//	private ApplicationRepository appRepo;
	@Inject
	private WorkTypeRepository wkTypeRepo;
	@Inject
	private WorkplaceAdapter wpAdapter;
	@Inject
	private EmpSubstVacationRepository empSubrepo;
	@Inject
	private ComSubstVacationRepository comSubrepo;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	@Inject
	private HolidaySettingRepository holidayRepo;
//	@Inject
//	private NewBeforeRegister processBeforeRegister;
//	@Inject
//	private RegisterAtApproveReflectionInfoService registerAppReplection;
//	@Inject
//	private AbsenceLeaveAppRepository absRepo;
//	@Inject
//	private RecruitmentAppRepository recRepo;
	@Inject
	private AppHdsubRecRepository CompLeaveRepo;
//	@Inject
//	private ApplicationApprovalService appImp;
//	@Inject
//	private IFactoryApplication IfacApp;
//	@Inject
//	private NewAfterRegister newAfterReg;
	@Inject
	private HolidayShipmentScreenAFinder afinder;
//	@Inject
//	private UseDateDeadlineFromDatePeriod dateDeadline;
	@Inject
	private InterimRemainDataMngRegisterDateChange registerDateChange;
	@Inject
	private OtherCommonAlgorithm ortherAl;
	@Inject
	private InterimRemainDataMngCheckRegisterRequest checkRegister;
	@Inject
	private HolidayApplicationSettingRepository repoHdAppSet;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
//	@Inject
//	private RequestSettingRepository requestSettingRepository;

	@Override
	protected ProcessResult handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {
		String companyID = AppContexts.user().companyId();

		SaveHolidayShipmentCommand command = context.getCommand();
		String sID = command.getAppCmd().getEmployeeID() != null ? command.getAppCmd().getEmployeeID()
				: AppContexts.user().employeeId();// Sua ho
		int comType = command.getComType();
		// アルゴリズム「振休振出申請の新規登録」を実行する
		return null; /*createNewForHolidayBreakge(command, companyID, sID, comType);*/

	}

	/*	private ProcessResult createNewForHolidayBreakge(SaveHolidayShipmentCommand command, String companyID, String sID, int comType) {
		// アルゴリズム「事前条件チェック」を実行する
		String appReason = preconditionCheck(command, companyID, ApplicationType.COMPLEMENT_LEAVE_APPLICATION, comType);
//		// アルゴリズム「登録前エラーチェック（新規）」を実行する
//		errorCheckBeforeRegister(command, companyID, sID, recDate, absDate, comType, appReason);
//		//振休残数不足チェック
//		checkForlackOfRest(companyID, sID, command);
		if (isSaveBothApp(comType)) {
			// アルゴリズム「振休申請・振出申請の同時登録」を実行する
			return registerBothApp(command, companyID, sID, absDate, recDate, appReason);
		} else {
			// アルゴリズム「振出申請の登録」を実行する
			if (isSaveRec(comType)) {
				return RegRecApp(command, companyID, sID, recDate, appReason);
			}
			if (isSaveAbs(comType)) {
				return RegAbsApp(command, companyID, sID, absDate, appReason);
			}

		}
		return null;
	}
	
	public List<ConfirmMsgDto> processBeforeRegister_New(SaveHolidayShipmentCommand command){
		
		String companyID = AppContexts.user().companyId();

		String sID = command.getAppCmd().getEmployeeID() != null ? command.getAppCmd().getEmployeeID()
				: AppContexts.user().employeeId();// Sua ho
		GeneralDate absDate = command.getAbsCmd().getAppDate();
		GeneralDate recDate = command.getRecCmd().getAppDate();
		int comType = command.getComType();
		List<ConfirmMsgDto> result = new ArrayList<>();
		
		// アルゴリズム「事前条件チェック」を実行する
		String appReason = preconditionCheck(command, companyID, ApplicationType.COMPLEMENT_LEAVE_APPLICATION, comType);
		// アルゴリズム「登録前エラーチェック（新規）」を実行する
		result.addAll(errorCheckBeforeRegister(command, companyID, sID, recDate, absDate, comType, appReason));
		//振休残数不足チェック
		checkForlackOfRest(companyID, sID, command);
		
		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
		
		if (isSaveRec(comType)) {
//			Application_New commonApp = IfacApp.buildApplication(command.getRecCmd().getAppID(), recDate,
//					command.getAppCmd().getPrePostAtr(), null, appReason, appType, recDate, recDate, sID);
			// error EA refactor 4
			List<ConfirmMsgDto> listConfirmMsg =  processBeforeRegister.processBeforeRegister_New(
					companyID, 
					EmploymentRootAtr.APPLICATION, 
					false, 
					commonApp, 
					null, 
					command.getDisplayInforWhenStarting().getAppDispInfoStartup().toDomain().getAppDispInfoWithDateOutput().getErrorFlag(), 
					new ArrayList<>()).stream().map(c-> new ConfirmMsgDto(c.getMsgID(), c.getParamLst())).collect(Collectors.toList());
			result.addAll(listConfirmMsg);
		}
		if (isSaveAbs(comType)) {
//			Application_New commonApp = IfacApp.buildApplication(command.getAbsCmd().getAppID(), absDate,
//					command.getAppCmd().getPrePostAtr(), null, appReason, appType, absDate, absDate, sID);
			// error EA refactor 4
			List<ConfirmMsgDto> listConfirmMsg =  processBeforeRegister.processBeforeRegister_New(
					companyID, 
					EmploymentRootAtr.APPLICATION, 
					false, 
					commonApp, 
					null, 
					command.getDisplayInforWhenStarting().getAppDispInfoStartup().toDomain().getAppDispInfoWithDateOutput().getErrorFlag(), 
					new ArrayList<>()).stream().map(c-> new ConfirmMsgDto(c.getMsgID(), c.getParamLst())).collect(Collectors.toList());
			result.addAll(listConfirmMsg);
		}
		
		return result;
	}
	

	private void checkForlackOfRest(String companyID, String sID, SaveHolidayShipmentCommand command) {
		//4.社員の当月の期間を算出する
		PeriodCurrentMonth cls =  this.ortherAl.employeePeriodCurrentMonthCalculate(companyID, sID, GeneralDate.today());
		//INPUT.振休申請をチェックする
		if(isSaveAbs(command.getComType())){
			//INPUT.振休申請＝設定あり
			//ドメインモデル「休暇申請設定」を取得する
			Optional<HolidayApplicationSetting> hdAppSetOpt =  repoHdAppSet.findSettingByCompanyId(companyID);
			
			boolean chkSubHoliday = false;
			boolean chkPause = false;
			boolean chkAnnual = false;
			boolean chkFundingAnnual = false;
			boolean chkSpecial = true;
			boolean chkPublicHoliday = false;
			boolean chkSuperBreak = true;
			String appName = "";
			if (hdAppSetOpt.isPresent()) {
				HolidayApplicationSetting hdSet = hdAppSetOpt.get();
//				chkPause = hdSet.getRegisInsuff().value == 1 ? true : false;// 休暇申請設定．振休残数不足登録できる
//				appName = hdSet.getHolidayApplicationTypeDisplayName()
//						.stream()
//						.filter(i -> i.getHolidayApplicationType() == HolidayAppType.REST_TIME)
//						.findFirst()
//						.map(i -> i.getDisplayName().v())
//						.orElse("");
			}
			
			InterimRemainCheckInputParam inputParam = new InterimRemainCheckInputParam(companyID, sID,
					new DatePeriod(cls.getStartDate(), cls.getStartDate().addYears(1).addDays(-1)), false,
					command.getAbsCmd().getAppDate(),
					new DatePeriod(command.getAbsCmd().getAppDate(), command.getAbsCmd().getAppDate()), true,
					Collections.emptyList(), Collections.emptyList(),getAppData(command,sID) , chkSubHoliday, chkPause, chkAnnual, chkFundingAnnual, chkSpecial,
					chkPublicHoliday, chkSuperBreak);
			//登録時の残数チェック
			EarchInterimRemainCheck check =  checkRegister.checkRegister(inputParam);
			
			if(check.isChkSubHoliday() ==true || check.isChkPause()==true || check.isChkAnnual() ==true || check.isChkFundingAnnual() ==true || check.isChkSpecial()==true){
				throw new BusinessException("Msg_1409", appName);
			}
		}
		
		
	}

	private List<AppRemainCreateInfor> getAppData(SaveHolidayShipmentCommand command, String sID) {
		List<AppRemainCreateInfor> apps =  new ArrayList<AppRemainCreateInfor>();
		
		if (isSaveAbs(command.getComType())) {
			AbsenceLeaveAppCommand absCmd = command.getAbsCmd();
			apps.add(new AppRemainCreateInfor(sID, absCmd.getAppID(), GeneralDateTime.now(), absCmd.getAppDate(),
					EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(), PrePostAtr.class), nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType.COMPLEMENT_LEAVE_APPLICATION,
					Optional.ofNullable(absCmd.getWkTypeCD()), Optional.ofNullable(absCmd.getWkTimeCD()), Optional.empty(), Optional.empty(), Optional.empty(),
					Optional.ofNullable(absCmd.getAppDate()), Optional.ofNullable(absCmd.getAppDate()), Collections.emptyList()));
		}

		if (isSaveRec(command.getComType())) {
			RecruitmentAppCommand recCmd = command.getRecCmd();
			apps.add(new AppRemainCreateInfor(sID, recCmd.getAppID(), GeneralDateTime.now(), recCmd.getAppDate(),
					EnumAdaptor.valueOf(command.getAppCmd().getPrePostAtr(), PrePostAtr.class), nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType.COMPLEMENT_LEAVE_APPLICATION,
					Optional.ofNullable(recCmd.getWkTypeCD()), Optional.ofNullable(recCmd.getWkTimeCD()), Optional.empty(), Optional.empty(), Optional.empty(),
					Optional.ofNullable(recCmd.getAppDate()), Optional.ofNullable(recCmd.getAppDate()), Collections.emptyList()));
		}
		return apps;
	}

	private ProcessResult RegAbsApp(SaveHolidayShipmentCommand command, String companyID, String sID,
			GeneralDate absDate, String appReason) {
		String wkTypeCD = command.getAbsCmd().getWkTypeCD();
		// アルゴリズム「振休消化管理データ更新と消化対象の決定」を実行する
		updateDigestionTarget(command);
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する
		updateOfSubstitution(command, wkTypeCD);
		// ドメイン「振休申請」を1件登録する
//		Application_New absCommonApp = createNewAbsApp(command, companyID, sID, absDate, appReason);
		//暫定データの登録
		this.registerDateChange.registerDateChange(companyID, sID, Arrays.asList(absDate));
		// アルゴリズム「新規画面登録後の処理」を実行する
		return this.newAfterReg.processAfterRegister(absCommonApp);
		return null;
	}

	private void updateDigestionTarget(SaveHolidayShipmentCommand command) {
		// アルゴリズム「勤務種類別振休消化数の取得」を実行する
		//BigDecimal day = getByWorkType(command.getAbsCmd().getWkTypeCD(), WorkTypeClassification.Pause);
		// TODO アルゴリズム「暫定振休管理データの登録と自動相殺」を実行する chưa làm được do không có thông
		// tin của 暫定振休管理データ
		// 暫定振出振休紐付け管理の件数分ループ

	}

	private ProcessResult RegRecApp(SaveHolidayShipmentCommand command, String companyID, String sID,
			GeneralDate recDate, String appReason) {

		String wkTypeCD = command.getRecCmd().getWkTypeCD();
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する
		updateOfSubstitution(command, wkTypeCD);
		// アルゴリズム「振休発生管理データ更新」を実行する
		updateOccurrenceData(companyID, sID, wkTypeCD, recDate);
		// 消化対象代休管理を振出申請に追加する
//		Application_New recCommonApp = createNewRecApp(command, companyID, sID, recDate, appReason);
		//暫定データの登録
		this.registerDateChange.registerDateChange(companyID, sID, Arrays.asList(recDate));
		// アルゴリズム「新規画面登録後の処理」を実行する
		return this.newAfterReg.processAfterRegister(recCommonApp);
		return null;
	}

	private void updateOccurrenceData(String companyID, String sID, String wkTypeCD, GeneralDate recDate) {
		// アルゴリズム「勤務種類別振休発生数の取得」を実行する
		//BigDecimal holidayBrkDownDay = getByWorkType(wkTypeCD, WorkTypeClassification.Shooting);
		// アルゴリズム「勤務種類別法定内外区分の取得」を実行する
		//HolidayAtr holidayType = getHolidayTypeByWkType(wkTypeCD, companyID);
		// アルゴリズム「振休有効期限の決定」を実行する
		//GeneralDate expDate = DemOfexpDate(recDate, companyID, sID);
		// アルゴリズム「暫定振出管理データの登録と自動相殺」を実行する
		registerMngData(recDate);

	}

	private void registerMngData(GeneralDate recDate) {
		// アルゴリズム「暫定振出管理データの登録」を実行する
		registerAbsData();
		// 振出有効期限内の未相殺振休を取得する
		getExpDate(recDate);

	}

	private void getExpDate(GeneralDate recDate) {
		// アルゴリズム「暫定未相殺残数を作成する」を実行する
		createProvisionalBalance(recDate);

	}

	private void createProvisionalBalance(GeneralDate recDate2) {
		// chưa có thông tin của domain 暫定振休管理データ

	}

	private ProcessResult registerBothApp(SaveHolidayShipmentCommand command, String companyID, String sID,
			GeneralDate absDate, GeneralDate recDate, String appReason) {
		AbsenceLeaveAppCommand absAppCmd = command.getAbsCmd();
		RecruitmentAppCommand recAppCmd = command.getRecCmd();
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する takingout
		updateOfSubstitution(command, absAppCmd.getWkTypeCD());
		// アルゴリズム「代休消化管理データ更新と消化対象の決定」を実行する holiday
		updateOfSubstitution(command, recAppCmd.getWkTypeCD());
		// 振休発生消化管理データを登録
		RegisterDigestionData(command, recDate, companyID, sID);

//		Application_New recCommonApp = createNewRecApp(command, companyID, sID, recDate, appReason);
		
		// error EA refactor 4
		// アルゴリズム「新規画面登録後の処理」を実行する
		newAfterReg.processAfterRegister(recCommonApp);

//		Application_New absCommonApp = createNewAbsApp(command, companyID, sID, absDate, appReason);
		
		// error EA refactor 4
		// アルゴリズム「新規画面登録後の処理」を実行する
		ProcessResult result = newAfterReg.processAfterRegister(absCommonApp);
		// ドメイン「振休振出同時申請管理」を1件登録する
		createNewComLeaveSilMng(recAppCmd.getAppID(), absAppCmd.getAppID());

		return result;
		return null;
	}

	private void createNewComLeaveSilMng(String recAppID, String absAppID) {
		CompltLeaveSimMng comMng = new CompltLeaveSimMng(recAppID, absAppID, SyncState.SYNCHRONIZING);
		CompLeaveRepo.insert(comMng);

	}

	private Application createNewAbsApp(SaveHolidayShipmentCommand command, String companyID, String sID,
			GeneralDate absDate, String appReason) {
//		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
//		Application_New commonApp = IfacApp.buildApplication(command.getAbsCmd().getAppID(), absDate,
//				command.getAppCmd().getPrePostAtr(), null, appReason, appType, absDate, absDate, sID);
//
//		// アルゴリズム「登録前共通処理（新規）」を実行する
//		CmProcessBeforeReg(command, commonApp);
//		// ドメイン「振出申請」を1件登録する
//
//		AbsenceLeaveApp absApp = createNewAbsDomainFromCmd(command.getAbsCmd());
//		// error EA refactor 4
//		/*appImp.insert(commonApp);*/
//		absRepo.insert(absApp);
//		
//		// 暫定データの登録
//		this.registerDateChange.registerDateChange(companyID, sID, Arrays.asList(absDate));
//		// アルゴリズム「新規画面登録時承認反映情報の整理」を実行する
//		// error EA refactor 4
//		/*registerAppReplection.newScreenRegisterAtApproveInfoReflect(sID, commonApp);*/
//
//		return commonApp;
	/*		return null;

	}

	public AbsenceLeaveApp createNewAbsDomainFromCmd( AbsenceLeaveAppCommand absCmd) {
		return absCmd.toDomain();
	}

	private Application createNewRecApp(SaveHolidayShipmentCommand command, String companyID, String sID,
			GeneralDate recDate, String appReason) {
//		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
//		
//		Application_New commonApp = IfacApp.buildApplication(command.getRecCmd().getAppID(), recDate,
//				command.getAppCmd().getPrePostAtr(), null, appReason, appType, recDate, recDate, sID);
//
//		
//		// アルゴリズム「登録前共通処理（新規）」を実行する
//		CmProcessBeforeReg(command, commonApp);
//		// ドメイン「振出申請」を1件登録する
//		RecruitmentApp recApp = createNewRecDomainFromCmd(command.getRecCmd());
//		// error EA refactor 4
//		/*appImp.insert(commonApp);*/
//		recRepo.insert(recApp);
//		
//		// 暫定データの登録
//		this.registerDateChange.registerDateChange(companyID, sID, Arrays.asList(recDate));
//		// アルゴリズム「新規画面登録時承認反映情報の整理」を実行する
//		// error EA refactor 4
//		/*registerAppReplection.newScreenRegisterAtApproveInfoReflect(sID, commonApp);*/
//
//		return commonApp;
	/*		return null;
	}

	private RecruitmentApp createNewRecDomainFromCmd(RecruitmentAppCommand appCmd) {
		WkTimeCommand wkTime1Cmd = appCmd.getWkTime1();
		//WkTimeCommand wkTime2Cmd = appCmd.getWkTime2();
		RecruitmentWorkingHour recHour1 = new RecruitmentWorkingHour(new WorkTime(wkTime1Cmd.getStartTime()),
				EnumAdaptor.valueOf(wkTime1Cmd.getStartType(), NotUseAtr.class), new WorkTime(wkTime1Cmd.getEndTime()),
				EnumAdaptor.valueOf(wkTime1Cmd.getEndType(), NotUseAtr.class));
		
		RecruitmentWorkingHour recHour2 = null; new RecruitmentWorkingHour(new WorkTime(wkTime2Cmd.getStartTime()),
				EnumAdaptor.valueOf(wkTime2Cmd.getStartType(), NotUseAtr.class),
				new WorkTime(wkTime2Cmd.getEndTime()),
				EnumAdaptor.valueOf(wkTime2Cmd.getEndType(), NotUseAtr.class));
//		RecruitmentApp recApp = new RecruitmentApp(appCmd.getAppID(), new WorkTypeCode(appCmd.getWkTypeCD()),
//				new WorkTimeCode(appCmd.getWkTimeCD()),
//				recHour1,
//				recHour2,
//				Collections.emptyList());

		return recApp;
	}

	public void CmProcessBeforeReg(SaveHolidayShipmentCommand command, Application application) {
		// アルゴリズム「新規画面登録前の処理」を実行する
		// processBeforeRegister.processBeforeRegister(application, OverTimeAtr.ALL, command.isCheckOver1Year(), Collections.emptyList());

	}

	private void RegisterDigestionData(SaveHolidayShipmentCommand command, GeneralDate recDate, String companyID,
			String sID) {
		// アルゴリズム「勤務種類別振休発生数の取得」を実行する rec
		BigDecimal absBrkDownDay = getByWorkType(command.getAbsCmd().getWkTypeCD(), WorkTypeClassification.Pause);
		// アルゴリズム「勤務種類別振休発生数の取得」を実行する holiday
		BigDecimal recBrkDownDay = getByWorkType(command.getRecCmd().getWkTypeCD(), WorkTypeClassification.Shooting);
		boolean isBothDaySame = absBrkDownDay.compareTo(recBrkDownDay) == 0;
		if (isBothDaySame) {
			// アルゴリズム「振休有効期限の決定」を実行する
			GeneralDate expDate = DemOfexpDate(recDate, companyID, sID);
			// アルゴリズム「勤務種類別法定内外区分の取得」を実行する
			HolidayAtr holidayType = getHolidayTypeByWkType(command.getAbsCmd().getWkTypeCD(), companyID);
			// アルゴリズム「暫定振出・暫定振休管理データの同時登録」を実行する
			registerData(command, expDate, holidayType);

		}

	}

	private void registerData(SaveHolidayShipmentCommand command, GeneralDate expDate, HolidayAtr holidaySet) {
		// アルゴリズム「暫定振出管理データの登録」を実行する
		registerAbsData();

	}

	private void registerAbsData() {
		// TODO 更新用未使用日数を決定する
		// chưa có thông tin của domain 暫定振出管理データ

	}

	private HolidayAtr getHolidayTypeByWkType(String wkTypeCD, String companyID) {
		// ドメインモデル「勤務種類」を取得する
		HolidayAtr result = null;
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		if (wkTypeOpt.isPresent()) {
			WorkType wkType = wkTypeOpt.get();
			if (wkType.getDailyWork().isHolidayType()) {
				result = holidayRepo.findBy(companyID).get().getHolidayAtr();
			}
		}
		return result;
	}

	private GeneralDate DemOfexpDate(GeneralDate refDate, String companyID, String sID) {
		// Imported(就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wpAdapter.getEmpHistBySid(companyID, sID, refDate);
		GeneralDate expDate = null;
		if (empImpOpt.isPresent()) {
			// アルゴリズム「振休管理設定の取得」を実行する
			EmploymentHistoryImported empImp = empImpOpt.get();
			String emptCD = empImp.getEmploymentCode();
			Optional<EmpSubstVacation> empSubOpt = empSubrepo.findById(companyID, emptCD);
			if (empSubOpt.isPresent()) {
				EmpSubstVacation empSub = empSubOpt.get();
				//expDate = getDateByExpirationTime(empSub.getSetting().getExpirationDate(), sID);

			} else {
				Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(companyID);
				if (!comSubOpt.isPresent()) {
					throw new BusinessException("振休管理設定の取得 == null");
				}
				ComSubstVacation comSub = comSubOpt.get();
				expDate = getDateByExpirationTime(comSub.getSetting().getExpirationDate(), sID);

			}
		}
		return expDate;

	}

	private GeneralDate getDateByExpirationTime(ExpirationTime expirationDate, String sID) {
		String companyID = AppContexts.user().companyId();
		GeneralDate expDate = null;
		if (expirationDate.equals(ExpirationTime.UNLIMITED)) {
			expDate = GeneralDate.max();
		}
		if (expirationDate.equals(ExpirationTime.THIS_MONTH)) {
			expDate = otherCommonAlgorithmService
					.employeePeriodCurrentMonthCalculate(companyID, sID, GeneralDate.today()).getEndDate();
		}

		return expDate;
	}

	private void updateOfSubstitution(SaveHolidayShipmentCommand command, String wkTypeCD) {
		// アルゴリズム「勤務種類別代休消化数の取得」を実行する
		//BigDecimal substitutionDay = getByWorkType(wkTypeCD, WorkTypeClassification.SubstituteHoliday);

		// TODO liên quan đến domain 暫定休出管理データ

	}

	private BigDecimal getByWorkType(String wkTypeCD, WorkTypeClassification wkTypeClass) {
		String companyID = AppContexts.user().companyId();
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		BigDecimal result = new BigDecimal(0);
		if (wkTypeOpt.isPresent()) {
			WorkType wkType = wkTypeOpt.get();
			DailyWork dailyWk = wkType.getDailyWork();
			boolean isTypeUnitIsOneDay = dailyWk.getWorkTypeUnit().equals(WorkTypeUnit.OneDay);
			if (isTypeUnitIsOneDay) {

				if (dailyWk.getOneDay().equals(wkTypeClass)) {
					result = BigDecimal.valueOf(1);
				}

			}
			boolean isTypeUnitIsMorningAndAfterNoon = wkType.getDailyWork().getWorkTypeUnit()
					.equals(WorkTypeUnit.MonringAndAfternoon);
			if (isTypeUnitIsMorningAndAfterNoon) {
				if (dailyWk.getMorning().equals(wkTypeClass)) {
					result = result.add(BigDecimal.valueOf(0.5));
				} else {

				}
				if (dailyWk.getAfternoon().equals(wkTypeClass)) {
					result = result.add(BigDecimal.valueOf(0.5));
				} else {

				}

			}

		}
		return result;

	}

	private List<ConfirmMsgDto> errorCheckBeforeRegister(SaveHolidayShipmentCommand command, String companyID, String sID,
			GeneralDate recDate, GeneralDate absDate, int comType, String appReason) {

		List<ConfirmMsgDto> result = new ArrayList<>();
		// アルゴリズム「振休振出申請設定の取得」を実行する
		Optional<WithDrawalReqSet> withDrawReqSet = withDrawRepo.getWithDrawalReqSet();
		// アルゴリズム「申請前勤務種類の取得」を実行する takingout
		if (isSaveAbs(comType)) {
			//getWorkTypeOfApp(sID, absDate);
		}
		// アルゴリズム「申請前勤務種類の取得」を実行する holiday
		if (isSaveRec(comType)) {
			//getWorkTypeOfApp(sID, recDate);
		}
		// アルゴリズム「申請日関連チェック」を実行する
		ApplicationDateRelatedCheck(command, withDrawReqSet.get(), sID, recDate, absDate, comType);
		// アルゴリズム「勤務種類矛盾チェック」を実行する
		result.addAll(checkWorkTypeConflict(sID,companyID,command, withDrawReqSet.get()));
		// アルゴリズム「終日半日矛盾チェック」を実行する
		checkDayConflict(command, comType);
		// アルゴリズム「法内法外矛盾チェック」を実行する
		checkSetting(companyID, withDrawReqSet.get(), command, sID);
		return result;
	}

	private void checkSetting(String companyID, WithDrawalReqSet seqSet, SaveHolidayShipmentCommand command,
			String sID) {

		boolean isCheck = !seqSet.getCheckUpLimitHalfDayHD().equals(CheckUper.DONT_CHECK);
		if (isSaveBothApp(command.getComType()) && isCheck) {
			String wkTypeCD = command.getAbsCmd().getWkTypeCD();
			// アルゴリズム「勤務種類別法定内外区分の取得」を実行する
			HolidayAtr absHolidayType = getHolidayTypeByWkType(wkTypeCD, companyID);
			GeneralDate appDate = command.getRecCmd().getAppDate();
			// アルゴリズム「実績の取得」を実行する
			AchievementOutput achievement = afinder.getAchievement(companyID, sID, appDate);
			// アルゴリズム「勤務種類別法定内外区分の取得」を実行する
			HolidayAtr achievementHolidayType = getHolidayTypeByWkType(achievement.getWorkType().getWorkTypeCode(),
					companyID);
			boolean isError = compareHoliday(absHolidayType, achievementHolidayType);
			
			if (isError) {
				String nameId = achievementHolidayType != null ? achievementHolidayType.nameId : "設定なし";
				throw new BusinessException("Msg_702", "", appDate.toString("yyyy/MM/dd"), nameId);
			}
		}

	}

	private boolean compareHoliday(HolidayAtr absHolidayType, HolidayAtr achievementHolidayType) {
		boolean isError = false;
		if (absHolidayType == null && achievementHolidayType == null)
			isError = true;
		if (absHolidayType == null && achievementHolidayType != null)
			isError = true;
		if (!absHolidayType.equals(achievementHolidayType)) {
			isError = true;
		}
		return isError;
	}

	private void checkDayConflict(SaveHolidayShipmentCommand command, int comType) {
		if (isSaveBothApp(comType)) {
			// アルゴリズム「勤務種類別振休発生数の取得」を実行する takingout
			BigDecimal absDay = getByWorkType(command.getAbsCmd().getWkTypeCD(), WorkTypeClassification.Pause);

			// アルゴリズム「勤務種類別振休発生数の取得」を実行する holiday
			BigDecimal recDay = getByWorkType(command.getRecCmd().getWkTypeCD(), WorkTypeClassification.Shooting);

			boolean isBothDayNotZero = !(BigDecimal.valueOf(0).compareTo(absDay) == 0)
					&& !(BigDecimal.valueOf(0).compareTo(recDay) == 0);

			boolean isTwoDateNotSame = !(absDay.compareTo(recDay) == 0);

			if (isBothDayNotZero && isTwoDateNotSame) {

				throw new BusinessException("Msg_698");

			}
		}

	}

	private List<ConfirmMsgDto> checkWorkTypeConflict(String Sid, String companyID, SaveHolidayShipmentCommand command,
			WithDrawalReqSet withDrawalReqSet) {

		List<ConfirmMsgDto> result = new ArrayList<>();
		
		ContractCheck checkMode = withDrawalReqSet.getAppliDateContrac();
		boolean isCheck = !checkMode.equals(ContractCheck.DONT_CHECK);
		if (isCheck) {
			boolean isNotSelectYes = command.getIsNotSelectYes() == null ? true : command.getIsNotSelectYes();
			// アルゴリズム「振出勤務種類矛盾チェック」を実行する
			if (isSaveRec(command.getComType())) {
				result.addAll(workTypeContradictionCheck(companyID, Sid, command.getRecCmd().getAppDate(), checkMode, isNotSelectYes,true));
			}
			// アルゴリズム「振休勤務種類矛盾チェック」を実行する
			if (isSaveAbs(command.getComType())) {
				result.addAll(workTypeContradictionCheck(companyID, Sid, command.getAbsCmd().getAppDate(), checkMode, isNotSelectYes,false));
			}
		}
		return result;
	}

	*//**
	 * 振出勤務種類矛盾チェック &&振休勤務種類矛盾チェック
	 * 
	 * @param companyID
	 * @param sid
	 * @param appDate
	 * @param checkMode
	 * @param isNotSelectYes
	 * @param ischeckRec
	 *//*
	private List<ConfirmMsgDto> workTypeContradictionCheck(String companyID, String sid, GeneralDate appDate, ContractCheck checkMode,
			boolean isNotSelectYes, boolean ischeckRec) {
		List<ConfirmMsgDto> result = new ArrayList<>();
		// アルゴリズム「11.指定日の勤務実績（予定）の勤務種類を取得」を実行する
		WorkType workType = this.otherCommonAlgorithm.getWorkTypeScheduleSpec(companyID, sid, appDate);
		String appDateText = appDate.toString("yyyy/MM/dd");
		if (workType == null) {
			if (checkMode.equals(ContractCheck.CHECK_IMPOSSIBLE)) {
				throw new BusinessException("Msg_1519", appDateText);
			}
			if (checkMode.equals(ContractCheck.CHECK_AVAILABE) && isNotSelectYes) {
				result.add(new ConfirmMsgDto("Msg_1520", Arrays.asList(appDateText)));
				//throw new BusinessException("Msg_1520", appDateText);
			}

		} else {
			
				WorkTypeClassification wkTypeClass = workType.getDailyWork().getOneDay();
				boolean isError ;
				if (ischeckRec) {
					isError = !(wkTypeClass.equals(WorkTypeClassification.Holiday)
							|| wkTypeClass.equals(WorkTypeClassification.Shooting)
							|| wkTypeClass.equals(WorkTypeClassification.HolidayWork));
				} else {
					isError = wkTypeClass.equals(WorkTypeClassification.Holiday)
							|| wkTypeClass.equals(WorkTypeClassification.HolidayWork);
				}
			
				if (isError) {
					String wkTypeName = workType.getName().v();
					if (checkMode.equals(ContractCheck.CHECK_IMPOSSIBLE)) {
						throw new BusinessException("Msg_1521", appDateText, wkTypeName);
					}
					if (checkMode.equals(ContractCheck.CHECK_AVAILABE) && isNotSelectYes) {
						result.add(new ConfirmMsgDto("Msg_1522", Arrays.asList(appDateText, wkTypeName)));
						//throw new BusinessException("Msg_1522", appDateText, wkTypeName);
					}

				}

			
		}
		return result;
	}

	public boolean isSaveRec(int comType) {
		if (comType == ApplicationCombination.RecAndAbs.value || comType == ApplicationCombination.Rec.value) {
			return true;
		}
		return false;
	}

	public boolean isSaveAbs(int comType) {
		if (comType == ApplicationCombination.RecAndAbs.value || comType == ApplicationCombination.Abs.value) {
			return true;
		}
		return false;
	}

	public boolean isSaveBothApp(int comType) {
		if (comType == ApplicationCombination.RecAndAbs.value) {
			return true;
		}
		return false;
	}

	private void ApplicationDateRelatedCheck(SaveHolidayShipmentCommand command, WithDrawalReqSet reqSet, String sID,
			GeneralDate recDate, GeneralDate absDate, int comType) {
		// アルゴリズム「同日申請存在チェック」を実行する
		dateCheck(sID, recDate, absDate, command, comType);
		// 申請の組み合わせをチェックする
		if (isSaveBothApp(comType)) {
			// アルゴリズム「振休先取可否チェック」を実行する
			checkFirstShipment(reqSet.getLettleSuperLeave(), recDate, absDate);
			// アルゴリズム「同時申請時有効期限チェック」を実行する
			checkTimeApplication(sID, command);
		}
	}

	private void checkTimeApplication(String sID, SaveHolidayShipmentCommand command) {
		String companyID = AppContexts.user().companyId();
		GeneralDate recDate = command.getRecCmd().getAppDate();
		GeneralDate absDate = command.getAbsCmd().getAppDate();
		// Imported（就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wpAdapter.getEmpHistBySid(companyID, sID, recDate);
		empImpOpt.ifPresent(x -> {
			String employmentCd = x.getEmploymentCode();
			// アルゴリズム「振休使用期限日の算出」を実行する
			GeneralDate expAbsDate = calExpAbsDate(companyID, employmentCd, recDate);
			// 使用期限日と振休日を比較する
			if (absDate.after(expAbsDate)) {
				throw new BusinessException("Msg_1361", expAbsDate.toString("yyyy/MM/dd"));
			}
		});
	}

	private GeneralDate calExpAbsDate(String companyID, String employmentCd, GeneralDate recDate) {
		// アルゴリズム「振休管理設定の取得」を実行する
		SubstVacationSetting setting = null;
		Optional<EmpSubstVacation> empSubOpt = empSubrepo.findById(companyID, employmentCd);
		if (empSubOpt.isPresent()) {
			//setting = empSubOpt.get().getSetting();
			
		} else {
			Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(companyID);
			if (comSubOpt.isPresent()) {
				setting = comSubOpt.get().getSetting();
			}
		}
		if (setting == null) {
			throw new BusinessException("振休管理設定 == null");
		}
		// アルゴリズム「休暇使用期限から使用期限日を算出する」を実行する
		return calExpDateFromAbsDate(recDate, setting.getExpirationDate(), employmentCd);
	}

	private GeneralDate calExpDateFromAbsDate(GeneralDate recDate, ExpirationTime expTime, String employmentCd) {
		// 休暇使用期限をチェックする
		GeneralDate resultDate;
		switch (expTime) {

		case END_OF_YEAR:
			resultDate = GeneralDate.ymd(recDate.year(), 12, 31);
			break;

		case UNLIMITED:
			resultDate = GeneralDate.max();
			break;

		default:
			// 期限指定のある使用期限日を作成する
			// resultDate = this.dateDeadline.useDateDeadline(employmentCd, expTime, recDate);
			resultDate = null;
			break;

		}
		return resultDate;
	}

	private void checkFirstShipment(AllowAtr allowAtr, GeneralDate recDate, GeneralDate absDate) {
		boolean isTwoDateSame = recDate.equals(absDate);
		if (isTwoDateSame) {
			throw new BusinessException("Msg_696");
		}
		boolean isRecDateAfterAbs = recDate.after(absDate) && allowAtr.equals(AllowAtr.NOTALLOW);
		if (isRecDateAfterAbs) {
			throw new BusinessException("Msg_697");

		}

	}
//EA修正履歴 No.3103
	//同日申請存在チェック
	public void dateCheck(String employeeID, GeneralDate recDate, GeneralDate absDate,
			SaveHolidayShipmentCommand command, int comType) {
//		// アルゴリズム「休暇・振替系申請存在チェック」を実行する
//		if (isSaveAbs(comType)) {
//			vacationTransferCheck(employeeID, absDate, command.getAppCmd().getPrePostAtr());
//		}
//		// アルゴリズム「休暇・振替系申請存在チェック」を実行する
//		if (isSaveRec(comType)) {
//			vacationTransferCheck(employeeID, recDate, command.getAppCmd().getPrePostAtr());
//		}

	}

	public void vacationTransferCheck(String sID, GeneralDate appDate, int prePostAtr) {
		// ドメインモデル「申請」を取得する
//		List<Application_New> sameDateApps = appRepo
//				.getApp(sID, appDate, prePostAtr, ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value).stream()
//				.filter(x -> !x.getReflectionInformation().getStateReflectionReal().equals(ReflectedState_New.CANCELED)
//						&& !x.getReflectionInformation().getStateReflectionReal().equals(ReflectedState_New.WAITCANCEL)
//						&& !x.getReflectionInformation().getStateReflectionReal().equals(ReflectedState_New.DENIAL))
//				.collect(Collectors.toList());
//
//		boolean isAppSameDateExists = !CollectionUtil.isEmpty(sameDateApps);
//
//		if (isAppSameDateExists) {
//
//			throw new BusinessException("Msg_700", " ", appDate.toString());
//
//		}

	}

//	private void getWorkTypeOfApp(String sID, GeneralDate appDate) {
//		// アルゴリズム待ち
//		// アルゴリズム「勤務実績を取得する」を実行する
//		String workTypeCode = "";
//		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(sID, appDate);
//		if (recordWorkInfoImport.getWorkTypeCode().equals("")) {
//			// アルゴリズム「予定管理利用チェック」を実行する
//			UseAtr useAtr = ScheMngUseCheck(sID, appDate);
//			if (useAtr != null && useAtr.equals(UseAtr.USE)) {
//				// アルゴリズム「勤務予定を取得する」を実行する
//
//			} else {
//
//			}
//		} else {
//			workTypeCode = recordWorkInfoImport.getWorkTypeCode();
//
//		}
//
//	}

//	private UseAtr ScheMngUseCheck(String sID, GeneralDate appDate) {
//		// ドメインモデル「個人労働条件」を取得する
//		Optional<PersonalLaborCondition> perCond = personalLaborConditionRepository.findById(sID, appDate);
//		if (perCond.isPresent()) {
//
//			return perCond.get().getScheduleManagementAtr();
//		}
//		return null;
//
//	}

	public String preconditionCheck(SaveHolidayShipmentCommand command, String companyID, ApplicationType appType,
			int comType) {
		if (isSaveRec(comType)) {
			// 勤務種類、就業時間帯チェックのメッセージを表示
			this.detailBeforeUpdate.displayWorkingHourCheck(companyID, command.getRecCmd().getWkTypeCD(),
					command.getRecCmd().getWkTimeCD());
		}
		if (isSaveAbs(comType)) {
			// 勤務種類、就業時間帯チェックのメッセージを表示
			this.detailBeforeUpdate.displayWorkingHourCheck(companyID, command.getAbsCmd().getWkTypeCD(),
					command.getAbsCmd().getWkTimeCD());
		}
		// アルゴリズム「申請理由の生成と検査」を実行する
		String reason = GenAndInspectionOfAppReason(command, companyID, appType);
		// INPUT.振出申請に申請理由を設定する
		
		if (isSaveRec(comType)) {
			validateRec(command.getRecCmd());
		}
		if (isSaveAbs(comType)) {
			validateAbs(command.getAbsCmd());
		}

		return reason;

	}

	private void validateAbs(AbsenceLeaveAppCommand cmd) {

		WkTimeCommand wkTime1 = cmd.getWkTime1();
		//WkTimeCommand wkTime2 = cmd.getWkTime2();

		// 就業時間帯変更＝するしない区分.しないのとき、以下の項目が設定されていないこと
		// ・就業時間帯
		// ・勤務時間1
		// ・勤務時間2
		boolean isNotUseWkType = cmd.getChangeWorkHoursType() == NotUseAtr.NOT_USE.value;
		if (isNotUseWkType) {
			wkTime1.setStartTime(null);
			wkTime1.setEndTime(null);
			cmd.setWkTimeCD(null);
			//wkTime2.setStartTime(null);
			//wkTime2.setEndTime(null);
			cmd.setWkTimeCD(null);
		} else {
			// 開始時刻＜終了時刻 (#Msg_966#)
			checkTime(wkTime1.getStartTime(), wkTime1.getEndTime());
			//checkTime(wkTime2.getStartTime(), wkTime2.getEndTime());
			// reason check trước đó ở hàm GenAndInspectionOfAppReason rồi
		}

	}

	private void validateRec(RecruitmentAppCommand cmd) {
		WkTimeCommand wkTime1 = cmd.getWkTime1();
		//WkTimeCommand wkTime2 = cmd.getWkTime2();
		// 開始時刻＜終了時刻 (#Msg_966#)
		checkTime(wkTime1.getStartTime(), wkTime1.getEndTime());
		//checkTime(wkTime2.getStartTime(), wkTime2.getEndTime());
		// reason check trước đó ở hàm GenAndInspectionOfAppReason rồi

	}

	private void checkTime(Integer startTime, Integer endTime) {
		boolean isStartAfterEndTime = (startTime != null && endTime != null) && (startTime > endTime);

		if (isStartAfterEndTime) {

			throw new BusinessException("Msg_966");

		}
	}

	private String GenAndInspectionOfAppReason(SaveHolidayShipmentCommand command, String companyID,
			ApplicationType appType) {
		RequestSetting requestSetting = requestSettingRepository.findByCompany(companyID).get();
		
		AppTypeSetting appTypeSetting = requestSetting.getApplicationSetting().getListAppTypeSetting()
				.stream().filter(x -> x.getAppType() == ApplicationType.COMPLEMENT_LEAVE_APPLICATION)
				.findFirst().get();

		String typicalReason = getTypicalReason(command, appTypeSetting);

		String displayReason = getDisplayReason(typicalReason, command, appTypeSetting, command.isScreenB());

		String appReason = typicalReason + displayReason;

		validateReasonText(appReason, requestSetting, appTypeSetting);

		return appReason;
	}

	private void validateReasonText(String appReason, RequestSetting requestSetting, AppTypeSetting appTypeSetting) {

		boolean isAnyReasonControlDisplay = isComboBoxReasonDisplay(appTypeSetting) || isReasonTextFieldDisplay(appTypeSetting);

		boolean isReasonBlankWhenRequired = requestSetting.getApplicationSetting().getAppLimitSetting().getRequiredAppReason()
				&& Strings.isBlank(appReason);

		if (isAnyReasonControlDisplay && isReasonBlankWhenRequired) {

			throw new BusinessException("Msg_115");

		}

	}

	private String getDisplayReason(String typicalReason, SaveHolidayShipmentCommand command,
			AppTypeSetting appTypeSetting, boolean isScreenB) {
		String disPlayReason = Strings.EMPTY;
		if (isReasonTextFieldDisplay(appTypeSetting)) {

			if (Strings.isNotBlank(typicalReason)) {

				disPlayReason += System.lineSeparator();

			}

			disPlayReason += command.getAppCmd().getApplicationReason();

		}else{
			if (isScreenB && Strings.isBlank(typicalReason)) {
				disPlayReason = command.getAppCmd().getApplicationReason();
			}
		}
		return disPlayReason;
	}

	private boolean isReasonTextFieldDisplay(AppTypeSetting appTypeSetting) {

		return appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY;

	}

	private String getTypicalReason(SaveHolidayShipmentCommand command, AppTypeSetting appTypeSetting) {

		if (isComboBoxReasonDisplay(appTypeSetting)) {

			return command.getAppCmd().getAppReasonText();

		}
		return "";
	}

	private boolean isComboBoxReasonDisplay(AppTypeSetting appTypeSetting) {
		return appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY;

	}
*/
}
