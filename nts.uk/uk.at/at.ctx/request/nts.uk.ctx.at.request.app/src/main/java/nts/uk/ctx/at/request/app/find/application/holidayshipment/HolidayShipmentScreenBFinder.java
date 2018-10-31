package nts.uk.ctx.at.request.app.find.application.holidayshipment;

import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeSetDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.AbsenceLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp.RecruitmentAppDto;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.ApplicationMetaOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class HolidayShipmentScreenBFinder {
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	@Inject
	private DetailAppCommonSetService detailService;
	@Inject
	private BeforePreBootMode bootMode;
	@Inject
	private ApplicationSettingRepository appSetRepo;
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private RecruitmentAppRepository recRepo;
	@Inject
	private CompltLeaveSimMngRepository CompLeaveRepo;
	@Inject
	private ApplicationRepository_New appRepo;
	@Inject
	private HolidayShipmentScreenAFinder aFinder;
	@Inject
	private EmployeeRequestAdapter empAdaptor;
	@Inject
	private RequestSettingRepository reqSetRepo;
	@Inject
	private AbsenceReruitmentMngInPeriodQuery absRertMngInPeriod;

	private static final ApplicationType APP_TYPE = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;

	/**
	 * find by Id
	 * 
	 * @param applicationID
	 */
	public HolidayShipmentDto findByID(String applicationID) {
		HolidayShipmentDto screenInfo = new HolidayShipmentDto();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		screenInfo.setEmployeeID(employeeID);
		boolean isRecAppID = isRecAppID(applicationID, screenInfo);
		// 1-1.新規画面起動前申請共通設定を取得する
		int rootAtr = 1;

		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet
				.prelaunchAppCommonSetService(companyID, employeeID, rootAtr, APP_TYPE, GeneralDate.today());

		screenInfo.setApplicationSetting(ApplicationSettingDto.convertToDto(appCommonSettingOutput.applicationSetting));

		// 入力者
		// 14-1.詳細画面起動前申請共通設定を取得する
		Optional<Application_New> appOutputOpt = appRepo.findByID(companyID, applicationID);
		// 14-2.詳細画面起動前モードの判断
		if (appOutputOpt.isPresent()) {
			Application_New appOutput = appOutputOpt.get();

			setEmployeeDisplayText(appOutput, screenInfo);

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
				GeneralDate refDate = HolidayShipmentScreenAFinder.DetRefDate(recAppDate, absAppDate);
				// アルゴリズム「振休振出申請起動時の共通処理」を実行する
				aFinder.commonProcessAtStartup(companyID, employeeID, refDate, recAppDate, recWorkTypeCD, recWorkTimeCD,
						absAppDate, absWorkTypeCD, absWorkTimeCD, screenInfo, appCommonSettingOutput);
				//[No.506]振休残数を取得する
				double absRecMng = absRertMngInPeriod.getAbsRecMngRemain(employeeID, GeneralDate.today());
				screenInfo.setAbsRecMng(absRecMng);
			}
		}

		return screenInfo;

	}

	private void setEmployeeDisplayText(Application_New appOutput, HolidayShipmentDto screenInfo) {
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

}
