package nts.uk.ctx.at.request.app.find.application.holidayshipment;

/*import java.util.Collections;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.arc.time.calendar.period.DatePeriod;*/
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.AbsenceLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp.RecruitmentAppDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.ApplicationMetaOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.holidayshipment.HolidayShipmentService;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class HolidayShipmentScreenBFinder {
//	@Inject
//	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	@Inject
	private DetailAppCommonSetService detailService;
//	@Inject
//	private BeforePreBootMode bootMode;
//	@Inject
//	private ApplicationSettingRepository appSetRepo;
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private RecruitmentAppRepository recRepo;
	@Inject
	private CompltLeaveSimMngRepository CompLeaveRepo;
//	@Inject
//	private ApplicationRepository appRepo;
//	@Inject
//	private HolidayShipmentScreenAFinder aFinder;
	@Inject
	private EmployeeRequestAdapter empAdaptor;
//	@Inject
//	private RequestSettingRepository reqSetRepo;
//	@Inject
//	private AbsenceReruitmentMngInPeriodQuery absRertMngInPeriod;
//	@Inject
//	private WithDrawalReqSetRepository withDrawRepo;
	@Inject
	private WorkTimeSettingRepository wkTimeSetRepo;
	@Inject
	private PredetemineTimeSettingRepository preTimeSetRepo;
	
//	@Inject
//	private HolidayShipmentService holidayShipmentService;

	private static final ApplicationType APP_TYPE = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;

	/**
	 * find by Id
	 * 
	 * @param applicationID
	 */
	public HolidayShipmentDto findByID(String applicationID) {
		// error EA refactor 4
		/*HolidayShipmentDto screenInfo = new HolidayShipmentDto();
		String companyID = AppContexts.user().companyId();
		String enteredEmployeeID = AppContexts.user().employeeId();
		boolean isRecAppID = isRecAppID(applicationID, screenInfo);
		// 1-1.新規画面起動前申請共通設定を取得する
		int rootAtr = 1;

		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet
				.prelaunchAppCommonSetService(companyID, enteredEmployeeID, rootAtr, APP_TYPE, GeneralDate.today());

		screenInfo.setApplicationSetting(ApplicationSettingDto.convertToDto(appCommonSettingOutput.applicationSetting));

		// 入力者
		// 14-1.詳細画面起動前申請共通設定を取得する
		Optional<Application_New> appOutputOpt = appRepo.findByID(companyID, applicationID);
		// 14-2.詳細画面起動前モードの判断
		if (appOutputOpt.isPresent()) {
			Application_New appOutput = appOutputOpt.get();

			setEmployeeDisplayText(appOutput, screenInfo);
			
			String employeeID = appOutput.getEmployeeID();
			screenInfo.setEmployeeID(employeeID);
			

			screenInfo.setApplication(ApplicationDto_New.fromDomain(appOutput));

			DetailedScreenPreBootModeOutput bootOutput = bootMode.judgmentDetailScreenMode(companyID, employeeID,
					applicationID, appOutput.getAppDate());

			if (bootOutput != null) {
				// 14-3.詳細画面の初期モード
				Optional<ApplicationSetting> appSetOpt = appSetRepo.getApplicationSettingByComID(companyID);
				if (appSetOpt.isPresent()) {
					ApplicationSetting appSet = appSetOpt.get();
					screenInfo.setApplicationSetting(ApplicationSettingDto.convertToDto(appSet));
					// load app type set
					Optional<RequestSetting> reqSetOpt = reqSetRepo.findByCompany(companyID);
					if (reqSetOpt.isPresent()) {
						RequestSetting reqSet = reqSetOpt.get();
						Optional<AppTypeSetDto> appTypeSetDtoOpt = AppTypeSetDto.convertToDto(reqSet).stream()
								.filter(x -> x.getAppType().equals(APP_TYPE.value)).findFirst();

						if (appTypeSetDtoOpt.isPresent()) {
							screenInfo.setAppTypeSet(appTypeSetDtoOpt.get());
						}

					}
					screenInfo.setApplicationSetting(ApplicationSettingDto.convertToDto(appSetOpt.get()));

				}
				if (isRecAppID) {
					// 申請＝振出申請
					// アルゴリズム「振出申請に対応する振休情報の取得」を実行する
					getRecApp(applicationID, screenInfo);

				} else {
					// 申請＝振休申請
					// アルゴリズム「振休申請に対応する振出情報の取得」を実行する
					getAbsApp(applicationID, screenInfo);
				}
				// アルゴリズム「基準申請日の決定」を実行する
				RecruitmentAppDto recApp = screenInfo.getRecApp();
				AbsenceLeaveAppDto absApp = screenInfo.getAbsApp();
				GeneralDate recAppDate = recApp != null ? recApp.getAppDate() : null;
				GeneralDate absAppDate = absApp != null ? absApp.getAppDate() : null;
				String recWorkTypeCD = recApp != null ? recApp.getWorkTypeCD() : null;
				String absWorkTypeCD = absApp != null ? absApp.getWorkTypeCD() : null;
				String recWorkTimeCD = recApp != null ? recApp.getWorkTimeCD() : null;
				String absWorkTimeCD = absApp != null ? absApp.getWorkTimeCD() : null;
				GeneralDate refDate = holidayShipmentService.detRefDate(recAppDate, absAppDate);
				// アルゴリズム「振休振出申請起動時の共通処理」を実行する
				aFinder.commonProcessAtStartup(companyID, employeeID, refDate, recAppDate, recWorkTypeCD, recWorkTimeCD,
						absAppDate, absWorkTypeCD, absWorkTimeCD, screenInfo, appCommonSettingOutput);
				//[No.506]振休残数を取得する
				double absRecMng = absRertMngInPeriod.getAbsRecMngRemain(employeeID, GeneralDate.today()).getRemainDays();
				screenInfo.setAbsRecMng(absRecMng);
			}
		}

		return screenInfo;*/
		return null;
	}

	private void setEmployeeDisplayText(Application appOutput, HolidayShipmentDto screenInfo) {
		String resultName = "", enterEmployeeID = appOutput.getEnteredPersonID(), targetEmployeeID = appOutput.getEmployeeID();

		boolean isSameLogin = targetEmployeeID.equals(enterEmployeeID);
		if (isSameLogin) {
			resultName = empAdaptor.getEmployeeName(enterEmployeeID);
		} else {

			String targetEmployeeName = empAdaptor.getEmployeeName(targetEmployeeID);
			String enterEmployeeName = " (入力者 : " + empAdaptor.getEmployeeName(enterEmployeeID) + ")";
			resultName = targetEmployeeName + enterEmployeeName;

		}
		screenInfo.setEmployeeName(resultName);

	}

	private void getAbsApp(String applicationID, HolidayShipmentDto screenInfo) {
		// アルゴリズム「振休申請に同期された振出申請の取得」を実行する
		SyncState syncState = getCompltLeaveSimMngFromAbsID(applicationID, screenInfo);
		boolean isNotSync = syncState.equals(SyncState.ASYNCHRONOUS);
		if (isNotSync) {
			// 振休申請をクリアする
			clearRecApp(screenInfo);
			// アルゴリズム「振休申請と関連付けた振出情報の取得」を実行する
			AbsenceLeaveAppDto absApp = screenInfo.getAbsApp();
			absApp.getSubDigestions().forEach(x -> {
				if (x.getPayoutMngDataID() != null) {
					// TODO Imported(就業.申請承認.休暇残数.振出振休)「振出情報」を取得する chưa có ai
					// làm domain 振出データID指定
				} else {
					// TODO Imported(就業.申請承認.休暇残数.振出振休)「振出情報」を取得する chưa có ai
					// làm domain 暫定振出管理データ

				}
			});

		}
	}

	private SyncState getCompltLeaveSimMngFromAbsID(String applicationID, HolidayShipmentDto screenInfo) {
		// ドメインモデル「振休振出同時申請管理」を1件取得する

		SyncState result = SyncState.ASYNCHRONOUS;

		Optional<CompltLeaveSimMng> CompltLeaveSimMngOpt = CompLeaveRepo.findByAbsID(applicationID);
		if (CompltLeaveSimMngOpt.isPresent()) {

			CompltLeaveSimMng compltLeaveSimMng = CompltLeaveSimMngOpt.get();

			result = compltLeaveSimMng.getSyncing();

			Optional<RecruitmentApp> recAppOpt = recRepo.findByID(compltLeaveSimMng.getRecAppID());

			if (recAppOpt.isPresent()) {

				setRecApp(recAppOpt.get(), screenInfo);

			}

		}
		return result;
	}

	private void getRecApp(String applicationID, HolidayShipmentDto screenInfo) {
		// アルゴリズム「振出申請に同期された振休申請の取得」を実行する
		SyncState syncState = getCompltLeaveSimMngFromRecID(applicationID, screenInfo);
		if (syncState.equals(SyncState.ASYNCHRONOUS)) {
			// 振休申請をクリアする
			clearAbsApp(screenInfo);
			// アルゴリズム「振出日に関連付いた振休情報の取得」を実行する
			// TODO chưa có ai làm domain 暫定振出管理データ
		}

	}

	private SyncState getCompltLeaveSimMngFromRecID(String applicationID, HolidayShipmentDto screenInfo) {
		// ドメインモデル「振休振出同時申請管理」を1件取得する
		SyncState result = SyncState.ASYNCHRONOUS;
		Optional<CompltLeaveSimMng> CompltLeaveSimMngOpt = CompLeaveRepo.findByRecID(applicationID);
		if (CompltLeaveSimMngOpt.isPresent()) {
			CompltLeaveSimMng compltLeaveSimMng = CompltLeaveSimMngOpt.get();
			result = compltLeaveSimMng.getSyncing();
			Optional<AbsenceLeaveApp> absAppOpt = absRepo.findByID(compltLeaveSimMng.getAbsenceLeaveAppID());
			if (absAppOpt.isPresent()) {
				setAbsApp(absAppOpt.get(), screenInfo);
			}

		}
		return result;

	}

	private boolean isRecAppID(String applicationID, HolidayShipmentDto screenInfo) {
		boolean result = false;
		Optional<RecruitmentApp> recAppOpt = recRepo.findByID(applicationID);
		if (recAppOpt.isPresent()) {
			setRecApp(recAppOpt.get(), screenInfo);

			result = true;

		} else {
			Optional<AbsenceLeaveApp> absAppOpt = absRepo.findByID(applicationID);
			if (absAppOpt.isPresent()) {
				setAbsApp(absAppOpt.get(), screenInfo);
			}

		}
		return result;

	}

	private void clearRecApp(HolidayShipmentDto screenInfo) {
		screenInfo.setRecApp(null);
	}

	private void clearAbsApp(HolidayShipmentDto screenInfo) {
		screenInfo.setAbsApp(null);
	}

	private void setRecApp(RecruitmentApp recruitmentApp, HolidayShipmentDto screenInfo) {

		String companyID = AppContexts.user().companyId();
		ApplicationMetaOutput recAppOutput = detailService.getDetailAppCommonSet(companyID, recruitmentApp.getAppID());
		screenInfo.setRecApp(RecruitmentAppDto.fromDomain(recruitmentApp, recAppOutput.getAppDate()));

	}

	private void setAbsApp(AbsenceLeaveApp absenceLeaveApp, HolidayShipmentDto screenInfo) {

		String companyID = AppContexts.user().companyId();
		ApplicationMetaOutput absAppOutput = detailService.getDetailAppCommonSet(companyID, absenceLeaveApp.getAppID());
		screenInfo.setAbsApp(AbsenceLeaveAppDto.fromDomain(absenceLeaveApp, absAppOutput.getAppDate()));

	}
	
	// 1.振休振出申請（詳細）起動処理
	public DisplayInforWhenStarting startPageBRefactor(String applicationID) {
		// refactor 4 error
		/*DisplayInforWhenStarting result = new DisplayInforWhenStarting();
		String companyID = AppContexts.user().companyId();
		
		//詳細画面起動前申請共通設定を取得する(Lấy setting chung của đơn xin trước khi khởi động màn hình chi tiết)
		AppDispInfoStartupOutput_Old appDispInfoStartupOutput = detailService.getCommonSetBeforeDetail(companyID, applicationID);
		result.setAppDispInfoStartup(AppDispInfoStartupDto_Old.fromDomain(appDispInfoStartupOutput));

		//ドメインモデル「振休振出申請」を取得する(Lấy domain[đơn xin nghi bu lam bu])
		Optional<RecruitmentApp> recAppOpt = recRepo.findByID(applicationID);
		// check tôn tại đơn xin làm bù
		if (recAppOpt.isPresent()) {
			ApplicationMetaOutput recAppOutput = detailService.getDetailAppCommonSet(companyID, recAppOpt.get().getAppID());
			result.setRecApp(RecruitmentAppDto.fromDomain(recAppOpt.get(), recAppOutput.getAppDate()));
			this.setWkTimeZoneDisplayInfo(companyID, result.recApp.getWorkTimeCD(), result.recApp);
			
			Optional<AbsenceLeaveAppDto> absAppRefactor = this.getAbsAppRefactor(applicationID, companyID);
			if(absAppRefactor.isPresent()) {
				result.setAbsApp(absAppRefactor.get());
				this.setWkTimeZoneDisplayInfo(companyID, result.absApp.getWorkTimeCD(), result.absApp);
			}
		} else {
			Optional<AbsenceLeaveApp> absAppOpt = absRepo.findByID(applicationID);
			if (absAppOpt.isPresent()) {
				ApplicationMetaOutput absAppOutput = detailService.getDetailAppCommonSet(companyID, absAppOpt.get().getAppID());
				result.setAbsApp(AbsenceLeaveAppDto.fromDomain(absAppOpt.get(), absAppOutput.getAppDate()));
				Optional<RecruitmentAppDto> recAppRefactor = this.getRecAppRefactor(applicationID, companyID);
				this.setWkTimeZoneDisplayInfo(companyID, result.absApp.getWorkTimeCD(), result.absApp);
				if(recAppRefactor.isPresent()) {
					result.setRecApp(recAppRefactor.get());
					this.setWkTimeZoneDisplayInfo(companyID, result.recApp.getWorkTimeCD(), result.recApp);
				}
			}
		}
		
		//振休振出申請設定の取得
		Optional<WithDrawalReqSet> withDrawalReqSetOpt = withDrawRepo.getWithDrawalReqSet();
		if (withDrawalReqSetOpt.isPresent()) {
			result.setDrawalReqSet(WithDrawalReqSetDto.fromDomain(withDrawalReqSetOpt.get()));
		}
		
		if(result.getAbsApp() != null) {
			//振出用勤務種類の取得(Lấy worktype làm bù)
			List<WorkType> workTypeForHoliday = aFinder.getWorkTypeForHoliday(companyID, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.getAbsApp().getWorkTypeCD());
			DisplayInformationApplication applicationForWorkingDay = new DisplayInformationApplication();
			applicationForWorkingDay.setWorkTypeList(workTypeForHoliday.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			//振出申請起動時の表示情報．勤務種類リスト=取得した振出用勤務種類(List)/ (DisplayInfo khi khởi động đơn xin làm bù. WorktypeList = worktype làm bù(list đã lấy))
			result.setApplicationForWorkingDay(applicationForWorkingDay);
		}
		if(result.getRecApp() != null) {
			//振休用勤務種類の取得(Lấy worktype nghỉ bù)
			List<WorkType> workTypeForHoliday = aFinder.getWorkTypeForWorkingDay(companyID, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.getRecApp().getWorkTypeCD());
			DisplayInformationApplication applicationForHoliday = new DisplayInformationApplication();
			applicationForHoliday.setWorkTypeList(workTypeForHoliday.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			//振休申請起動時の表示情報．勤務種類リスト=取得した振休用勤務種類(List) /(DisplayInfo khi khởi động đơn xin nghỉ bù. WorktypeList= worktype nghỉ bù(List) đã lấy)
			result.setApplicationForHoliday(applicationForHoliday);
		}
		
		Optional<Application_New> application = appRepo.findByID(companyID, applicationID);
		if(application.isPresent()) {
			result.setApplication(ApplicationDto_New.fromDomain(application.get()));
		}
		
		if(application.isPresent()) {
			//[No.506]振休残数を取得する(Lấy số ngày nghỉ bù còn lại)
			AbsRecRemainMngOfInPeriod absRecMngRemain = absRertMngInPeriod.getAbsRecMngRemain(application.get().getEmployeeID(), GeneralDate.today());
			
			//一番近い期限日を取得する
			result.setRemainingHolidayInfor(new RemainingHolidayInfor(absRecMngRemain));
			result.setEmployeeName(getEmployeeDisplayTextRefactor(application.get().getEnteredPersonID(), application.get().getEmployeeID()));
			
			Optional<AppTypeSetDto> appTypeSetDtoOpt = AppTypeSetDto.convertToDto(appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getRequestSetting()).stream()
					.filter(x -> x.getAppType().equals(APP_TYPE.value)).findFirst();

			if (appTypeSetDtoOpt.isPresent()) {
				result.setAppTypeSet(appTypeSetDtoOpt.get());
			}
		}
		return result;*/
		return null;
	}

	private Optional<AbsenceLeaveAppDto> getAbsAppRefactor(String applicationID, String companyID) {
		Optional<CompltLeaveSimMng> compltLeaveSimMngOpt = CompLeaveRepo.findByRecID(applicationID);
		if (compltLeaveSimMngOpt.isPresent() && compltLeaveSimMngOpt.get().getSyncing().equals(SyncState.SYNCHRONIZING)) {
			Optional<AbsenceLeaveApp> absAppOpt = absRepo.findByID(compltLeaveSimMngOpt.get().getAbsenceLeaveAppID());
			if (absAppOpt.isPresent()) {
				ApplicationMetaOutput absAppOutput = detailService.getDetailAppCommonSet(companyID, absAppOpt.get().getAppID());
				return Optional.of(AbsenceLeaveAppDto.fromDomain(absAppOpt.get(), absAppOutput.getAppDate()));
			}
		}
		return Optional.empty();
	}
	
	private Optional<RecruitmentAppDto> getRecAppRefactor(String applicationID, String companyID) {
		Optional<CompltLeaveSimMng> compltLeaveSimMngOpt = CompLeaveRepo.findByAbsID(applicationID);
		if (compltLeaveSimMngOpt.isPresent() && compltLeaveSimMngOpt.get().getSyncing().equals(SyncState.SYNCHRONIZING)) {
			Optional<RecruitmentApp> recAppOpt = recRepo.findByID(compltLeaveSimMngOpt.get().getRecAppID());
			if(recAppOpt.isPresent()) {
				ApplicationMetaOutput recAppOutput = detailService.getDetailAppCommonSet(companyID, recAppOpt.get().getAppID());
				return Optional.of(RecruitmentAppDto.fromDomain(recAppOpt.get(), recAppOutput.getAppDate()));
			}
		}
		return Optional.empty();
	}
	
	private String getEmployeeDisplayTextRefactor(String enterEmployeeID, String targetEmployeeID) {
		if (targetEmployeeID.equals(enterEmployeeID)) {
			return empAdaptor.getEmployeeName(enterEmployeeID);
		} else {

			String targetEmployeeName = empAdaptor.getEmployeeName(targetEmployeeID);
			String enterEmployeeName = " (入力者 : " + empAdaptor.getEmployeeName(enterEmployeeID) + ")";
			return targetEmployeeName + enterEmployeeName;
		}
	}
	
	private void setWkTimeZoneDisplayInfo(String companyID, String wkTimeCode, HolidayShipmentAppDto recAppOutPut) {
		Optional<WorkTimeSetting> wkTimeOpt = wkTimeSetRepo.findByCode(companyID, wkTimeCode);

		if (wkTimeOpt.isPresent()) {

			recAppOutPut.updateFromWkTimeSet(wkTimeOpt.get());

			wkTimeCode = wkTimeOpt.get().getWorktimeCode().v();

			Optional<PredetemineTimeSetting> preTimeSetOpt = preTimeSetRepo.findByWorkTimeCode(companyID,
					wkTimeCode);
			if (preTimeSetOpt.isPresent()) {
				recAppOutPut.updateFromPreTimeSet(preTimeSetOpt.get());
				recAppOutPut.setWorkTimeName(wkTimeOpt.get().getWorkTimeDisplayName().getWorkTimeName().v());
			}
		}
	}
}
