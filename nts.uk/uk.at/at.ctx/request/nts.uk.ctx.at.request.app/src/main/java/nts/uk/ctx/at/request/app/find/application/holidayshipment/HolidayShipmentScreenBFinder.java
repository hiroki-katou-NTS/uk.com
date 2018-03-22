package nts.uk.ctx.at.request.app.find.application.holidayshipment;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.AbsenceLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp.RecruitmentAppDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApprovalFunctionSettingDto;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.ApplicationMetaOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.BreakOutType;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class HolidayShipmentScreenBFinder {

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
	private WithDrawalReqSetRepository withDrawRepo;
	@Inject
	private ApplicationReasonRepository appResonRepo;
	@Inject
	private WorkTypeRepository wkTypeRepo;
	@Inject
	private CollectAchievement collectAchievement;
	@Inject
	private EmployeeRequestAdapter empAdaptor;
	@Inject
	private WorkplaceAdapter wpAdapter;
	@Inject
	private RequestOfEachWorkplaceRepository requestWpRepo;
	@Inject
	private RequestOfEachCompanyRepository requestComRepo;
	@Inject
	private ApplicationRepository_New appRepo;

	/**
	 * find by Id
	 * 
	 * @param applicationID
	 */
	RecruitmentApp recApp;
	AbsenceLeaveApp absApp;
	ApplicationMetaOutput recAppOutput;
	ApplicationMetaOutput absAppOutput;
	String companyID;
	AppCommonSettingOutput appCommonSettingOutput;
	ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
	HolidayShipmentDto output;

	public HolidayShipmentDto findByID(String applicationID) {
		output = new HolidayShipmentDto();
		companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		boolean isRecApp = isRecApp(applicationID);
		// 14-1.詳細画面起動前申請共通設定を取得する
		Optional<Application_New> appOutputOpt = appRepo.findByID(companyID, applicationID);
		// 14-2.詳細画面起動前モードの判断
		if (appOutputOpt.isPresent()) {
			Application_New appOutput = appOutputOpt.get();

			output.setApplication(ApplicationDto_New.fromDomain(appOutput));
			DetailedScreenPreBootModeOutput bootOutput = bootMode.judgmentDetailScreenMode(companyID, employeeID,
					applicationID, appOutput.getAppDate());

			if (bootOutput != null) {
				// 14-3.詳細画面の初期モード
				Optional<ApplicationSetting> appSetOpt = appSetRepo.getApplicationSettingByComID(companyID);
				if (appSetOpt.isPresent()) {
					ApplicationSetting appSet = appSetOpt.get();
					output.setApplicationSetting(ApplicationSettingDto.convertToDto(appSet));
				}
				if (isRecApp) {
					// 申請＝振出申請
					// アルゴリズム「振出申請に対応する振休情報の取得」を実行する
					getRecApp(applicationID);

				} else {
					// 申請＝振休申請
					// アルゴリズム「振休申請に対応する振出情報の取得」を実行する
					getAbsApp(applicationID);
				}
				// アルゴリズム「基準申請日の決定」を実行する
				GeneralDate refDate = HolidayShipmentScreenAFinder.DetRefDate(recAppOutput.getAppDate(),
						absAppOutput.getAppDate());
				// アルゴリズム「振休振出申請起動時の共通処理」を実行する
				commonProcessAtStartup(employeeID, employeeID, refDate, recAppOutput.getAppDate(),
						recApp.getWorkTypeCD(), recApp.getWorkTimeCD().v(), absAppOutput.getAppDate(),
						absApp.getWorkTypeCD(), absApp.getWorkTimeCD().v(), output);

			}
		}

		return output;

	}

	private void commonProcessAtStartup(String companyID, String employeeID, GeneralDate refDate, GeneralDate appDate,
			String takingOutWkTypeCD, String takingOutWkTimeCD, GeneralDate deadDate, String holidayWkTypeCD,
			String holidayWkTimeCD, HolidayShipmentDto output) {
		// アルゴリズム「振休振出申請設定の取得」を実行する
		Optional<WithDrawalReqSet> withDrawalReqSetOpt = withDrawRepo.getWithDrawalReqSet();
		if (withDrawalReqSetOpt.isPresent()) {
			output.setDrawalReqSet(WithDrawalReqSetDto.fromDomain(withDrawalReqSetOpt.get()));
		}
		// アルゴリズム「振休振出申請定型理由の取得」を実行する

		output.setAppReasons(appResonRepo.getReasonByCompanyId(companyID).stream()
				.map(x -> ApplicationReasonDto.convertToDto(x)).collect(Collectors.toList()));
		// アルゴリズム「基準日別設定の取得」を実行する
		getDateSpecificSetting(companyID, employeeID, refDate, false, takingOutWkTypeCD, takingOutWkTimeCD,
				holidayWkTypeCD, holidayWkTimeCD, appCommonSettingOutput, output);
		// アルゴリズム「実績の取得」を実行する
		getAchievement(companyID, employeeID, appDate);
		// アルゴリズム「実績の取得」を実行する
		getAchievement(companyID, employeeID, deadDate);

	}

	private AchievementOutput getAchievement(String companyID, String employeeID, GeneralDate appDate) {
		// アルゴリズム「実績取得」を実行する
		if (appDate != null) {
			return collectAchievement.getAchievement(companyID, employeeID, appDate);
		}
		return null;

	}

	private void getDateSpecificSetting(String companyID, String employeeID, GeneralDate refDate, boolean getSetting,
			String takingOutWkTypeCD, String takingOutWkTimeCode, String holidayWkTypeCD, String holidayWkTimeCD,
			AppCommonSettingOutput appCommonSet, HolidayShipmentDto output) {
		// Imported(就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wpAdapter.getEmpHistBySid(companyID, employeeID, refDate);
		// アルゴリズム「所属職場を含む上位職場を取得」を実行する
		List<String> wpkIds = empAdaptor.findWpkIdsBySid(companyID, employeeID, refDate);
		if (empImpOpt.isPresent() && CollectionUtil.isEmpty(wpkIds)) {
			String employmentCD = empImpOpt.get().getEmploymentCode();

			if (getSetting) {
				// INPUT.設定取得＝true
				// アルゴリズム「雇用別申請承認設定の取得」を実行するz
				Optional<AppEmploymentSetting> appEmploymentSettingOpt = appCommonSet.appEmploymentWorkType.stream()
						.filter(x -> x.getEmploymentCode().equals(employmentCD)).findFirst();
				if (appEmploymentSettingOpt.isPresent()) {
					output.getAppEmploymentSettings()
							.add(AppEmploymentSettingDto.fromDomain(appEmploymentSettingOpt.get()));
				}
				// アルゴリズム「申請承認機能設定の取得」を実行する
				output.setApprovalFunctionSetting(
						ApprovalFunctionSettingDto.convertToDto(AcApprovalFuncSet(companyID, wpkIds)));

			}

			// アルゴリズム「振出用勤務種類の取得」を実行する
			// TakingOut
			output.setTakingOutWkTypes(getWorkTypeFor(companyID, employmentCD, takingOutWkTypeCD).stream()
					.map(x -> WorkTypeDto.fromDomainWorkTypeLanguage(x)).collect(Collectors.toList()));

			// INPUT.振出就業時間帯コード＝設定なし
			// アルゴリズム「振休用勤務種類の取得」を実行する
			// Holiday
			output.setHolidayWkTypes(getWorkTypeFor(companyID, employmentCD, holidayWkTypeCD).stream()
					.map(x -> WorkTypeDto.fromDomainWorkTypeLanguage(x)).collect(Collectors.toList()));
			// INPUT.振休就業時間帯コード＝設定なし

		}

	}

	private ApprovalFunctionSetting AcApprovalFuncSet(String companyID, List<String> wpkIds) {
		ApprovalFunctionSetting result = null;
		for (String wpID : wpkIds) {
			Optional<ApprovalFunctionSetting> wpOpt = requestWpRepo.getFunctionSetting(companyID, wpID, appType.value);
			if (wpOpt.isPresent()) {
				result = wpOpt.get();
			}
		}
		// 職場別設定なし
		Optional<ApprovalFunctionSetting> comOpt = requestComRepo.getFunctionSetting(companyID, appType.value);
		if (comOpt.isPresent()) {
			result = comOpt.get();
		}
		return result;

	}

	private List<WorkType> getWorkTypeFor(String companyID, String employmentCode, String wkTypeCD) {

		// ドメインモデル「勤務種類」を取得する yêu cầu team anh Chình trả về wktypes

		List<WorkType> wkTypes = wkTypeRepo.findWorkTypeForPause(companyID);
		// アルゴリズム「対象勤務種類の抽出」を実行する
		List<WorkType> outputWkTypes = extractTargetWkTypes(companyID, employmentCode, BreakOutType.PAUSE.value,
				wkTypes);
		if (!StringUtils.isEmpty(wkTypeCD)) {
			// アルゴリズム「申請済み勤務種類の存在判定と取得」を実行する
			appliedWorkType(companyID, outputWkTypes, wkTypeCD);

		}
		// sort by CD
		outputWkTypes.sort(Comparator.comparing(WorkType::getWorkTypeCode));
		return outputWkTypes;

	}

	private void getAbsApp(String applicationID) {
		// アルゴリズム「振休申請に同期された振出申請の取得」を実行する
		SyncState syncState = getCompltLeaveSimMngFromAbsID(applicationID);
		if (syncState.equals(SyncState.SYNCHRONIZING)) {
			// アルゴリズム「振休申請と関連付けた振出情報の取得」を実行する
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

	private boolean appliedWorkType(String companyID, List<WorkType> wkTypes, String wkTypeCD) {
		boolean masterUnregistered = true;

		Optional<WorkType> WkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		if (WkTypeOpt.isPresent()) {
			wkTypes.add(WkTypeOpt.get());
			masterUnregistered = false;

		}
		return masterUnregistered;

	}

	private List<WorkType> extractTargetWkTypes(String companyID, String employmentCode, int breakOutType,
			List<WorkType> wkTypes) {
		Optional<AppEmploymentSetting> empSetOpt = appCommonSettingOutput.appEmploymentWorkType.stream()
				.filter(x -> x.getHolidayOrPauseType() == breakOutType).findFirst();
		if (empSetOpt.isPresent()) {
			AppEmploymentSetting empSet = empSetOpt.get();
			if (empSet.isDisplayFlag()) {
				return wkTypes.stream()
						.filter(x -> empSet.getLstWorkType().stream()
								.filter(y -> y.getWorkTypeCode().equals(x.getWorkTypeCode().v())).findFirst()
								.isPresent())
						.collect(Collectors.toList());
			} else {
				return wkTypes;
			}
		} else {
			return wkTypes;
		}

	}

	private SyncState getCompltLeaveSimMngFromAbsID(String applicationID) {
		// ドメインモデル「振休振出同時申請管理」を1件取得する
		SyncState result = SyncState.ASYNCHRONOUS;
		Optional<CompltLeaveSimMng> CompltLeaveSimMngOpt = CompLeaveRepo.findByRecID(applicationID);
		if (CompltLeaveSimMngOpt.isPresent()) {
			CompltLeaveSimMng compltLeaveSimMng = CompltLeaveSimMngOpt.get();
			result = compltLeaveSimMng.getSyncing();
			Optional<RecruitmentApp> recAppOpt = recRepo.findByID(compltLeaveSimMng.getRecAppID());
			if (recAppOpt.isPresent()) {
				setRecApp(recAppOpt.get());
			} else {

				throw new BusinessException("");
			}

		}
		return result;
	}

	private void getRecApp(String applicationID) {
		// アルゴリズム「振出申請に同期された振休申請の取得」を実行する
		SyncState syncState = getCompltLeaveSimMngFromRecID(applicationID);
		if (syncState.equals(SyncState.SYNCHRONIZING)) {
			// アルゴリズム「振出日に関連付いた振休情報の取得」を実行する
			// TODO chưa có ai làm domain 暫定振出管理データ
		}

	}

	private SyncState getCompltLeaveSimMngFromRecID(String applicationID) {
		// ドメインモデル「振休振出同時申請管理」を1件取得する
		SyncState result = SyncState.ASYNCHRONOUS;
		Optional<CompltLeaveSimMng> CompltLeaveSimMngOpt = CompLeaveRepo.findByRecID(applicationID);
		if (CompltLeaveSimMngOpt.isPresent()) {
			CompltLeaveSimMng compltLeaveSimMng = CompltLeaveSimMngOpt.get();
			result = compltLeaveSimMng.getSyncing();
			Optional<AbsenceLeaveApp> absAppOpt = absRepo.findByID(compltLeaveSimMng.getAbsenceLeaveAppID());
			if (absAppOpt.isPresent()) {
				setAbsApp(absAppOpt.get());

			} else {

				throw new BusinessException("");
			}

		}
		return result;

	}

	private boolean isRecApp(String applicationID) {
		boolean result = false;
		Optional<RecruitmentApp> recAppOpt = recRepo.findByID(applicationID);
		if (recAppOpt.isPresent()) {
			setRecApp(recAppOpt.get());

			result = true;

		} else {
			Optional<AbsenceLeaveApp> absAppOpt = absRepo.findByID(applicationID);
			setAbsApp(absAppOpt.get());

		}
		return result;

	}

	private void setRecApp(RecruitmentApp recruitmentApp) {

		recApp = recruitmentApp;
		recAppOutput = detailService.getDetailAppCommonSet(companyID, recruitmentApp.getAppID());
		output.setRecApp(RecruitmentAppDto.fromDomain(recruitmentApp, absAppOutput.getAppDate()));

	}

	private void setAbsApp(AbsenceLeaveApp absenceLeaveApp) {
		absApp = absenceLeaveApp;
		absAppOutput = detailService.getDetailAppCommonSet(companyID, absenceLeaveApp.getAppID());
		output.setAbsApp(AbsenceLeaveAppDto.fromDomain(absenceLeaveApp, absAppOutput.getAppDate()));

	}
}
